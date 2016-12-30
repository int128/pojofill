package org.hidetake.pojofill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.isAbstract;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingInt;
import static java.util.Optional.empty;
import static java.util.stream.IntStream.range;

/**
 * A class to instantiate an object.
 *
 * @author Hidetake Iwata
 */
@Slf4j
@RequiredArgsConstructor
public class ObjectInstantiator {
    private final Instantiator instantiator;

    private static final Predicate<Constructor> CONSTRUCTOR_PREDICATE = (Constructor constructor) ->
        Modifier.isPublic(constructor.getModifiers());

    private static final Predicate<Method> SETTER_PREDICATE = (Method method) ->
        method.getName().startsWith("set") && method.getParameterCount() == 1 && Modifier.isPublic(method.getModifiers());

    /**
     * Create an new object.
     *
     * @param clazz class to instantiate
     * @param <T> type of the class
     * @return an instance or {@link Optional#empty()} if error occurred
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> newInstance(Class<T> clazz) {
        if (isAbstract(clazz.getModifiers())) {
            log.debug("Could not instantiate abstract class: {}", clazz);
            return empty();
        }

        return Stream.of(clazz.getConstructors())
            .filter(CONSTRUCTOR_PREDICATE)
            .sorted(comparingInt(Constructor::getParameterCount))
            .sorted(reverseOrder())
            .flatMap(constructor -> {
                log.trace("Trying constructor: {}", constructor);
                val arguments = range(0, constructor.getParameterCount())
                    .mapToObj(index -> {
                        val parameterClass = constructor.getParameterTypes()[index];
                        val parameterType = constructor.getGenericParameterTypes()[index];
                        return instantiator.newInstance(parameterClass, parameterType);
                    })
                    .map(argument -> argument.orElse(null))
                    .toArray();

                try {
                    log.trace("Invoking constructor: {} with {}", constructor, arguments);
                    return Stream.of((T) constructor.newInstance(arguments));
                } catch (IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    log.debug("Could not invoke constructor: {} with {}", constructor, arguments, e);
                    return Stream.empty();
                }
            })
            .peek(instance ->
                Stream.of(clazz.getMethods())
                    .filter(SETTER_PREDICATE)
                    .forEach(setter -> {
                        val parameterClass = setter.getParameterTypes()[0];
                        val parameterType = setter.getGenericParameterTypes()[0];
                        instantiator.newInstance(parameterClass, parameterType).map((parameter) -> {
                            try {
                                log.trace("Invoking setter: {} with {}", setter, parameterType);
                                setter.invoke(instance, parameter);
                            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                log.debug("Could not invoke setter: {} with {}", setter, parameterType, e);
                            }
                            return null;
                        });
                    })
            )
            .findFirst();
    }
}
