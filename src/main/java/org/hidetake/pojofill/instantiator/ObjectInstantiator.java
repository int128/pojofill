package org.hidetake.pojofill.instantiator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hidetake.pojofill.RootInstantiator;
import org.hidetake.pojofill.context.ConstructorArgument;
import org.hidetake.pojofill.context.InstantiationContext;
import org.hidetake.pojofill.context.SetterArgument;

import java.lang.reflect.*;
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
public class ObjectInstantiator implements Instantiator {
    private static final Predicate<Constructor> CONSTRUCTOR_PREDICATE = (Constructor constructor) ->
        Modifier.isPublic(constructor.getModifiers());

    private static final Predicate<Method> SETTER_PREDICATE = (Method method) ->
        method.getName().startsWith("set") && method.getParameterCount() == 1 && Modifier.isPublic(method.getModifiers());

    @Override
    public <T> Optional<T> newInstance(RootInstantiator rootInstantiator, Class<T> clazz, Type genericParameterType, InstantiationContext context) {
        return newObject(rootInstantiator, clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> newObject(RootInstantiator rootInstantiator, Class<T> clazz) {
        if (isAbstract(clazz.getModifiers())) {
            log.debug("Could not instantiate abstract class: {}", clazz);
            return empty();
        }

        return Stream.of(clazz.getConstructors())
            .filter(CONSTRUCTOR_PREDICATE)
            .sorted(comparingInt(Constructor::getParameterCount))
            .sorted(reverseOrder())
            .flatMap(constructor -> {
                val arguments = range(0, constructor.getParameterCount())
                    .mapToObj(index -> {
                        val parameter = constructor.getParameters()[index];
                        val context = new ConstructorArgument(constructor, parameter);
                        return rootInstantiator.newInstance(parameter.getType(), parameter.getParameterizedType(), context);
                    })
                    .map(argument -> argument.orElse(null))
                    .toArray();

                try {
                    log.trace("Calling constructor {} with {}", constructor, arguments);
                    return Stream.of((T) constructor.newInstance(arguments));
                } catch (IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    log.debug("Could not call constructor {} with {}", constructor, arguments, e);
                    return Stream.empty();
                }
            })
            .peek(instance ->
                Stream.of(clazz.getMethods())
                    .filter(SETTER_PREDICATE)
                    .forEach(setter -> {
                        val parameter = setter.getParameters()[0];
                        val context = new SetterArgument(setter, parameter);
                        rootInstantiator.newInstance(parameter.getType(), parameter.getParameterizedType(), context).map((argument) -> {
                            try {
                                log.trace("Calling setter {} with {}", setter, argument);
                                setter.invoke(instance, argument);
                            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                log.debug("Could not call setter {} with {}", setter, argument, e);
                            }
                            return null;
                        });
                    })
            )
            .findFirst();
    }
}
