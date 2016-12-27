package org.hidetake.pojofill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isInterface;
import static java.util.Collections.emptyList;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingInt;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.IntStream.range;

@Slf4j
@RequiredArgsConstructor
@Getter
public class Pojofill {
    private final ValueProvider valueProvider = new ValueProvider();

    private static final Predicate<Constructor> CONSTRUCTOR_PREDICATE = (Constructor constructor) ->
        Modifier.isPublic(constructor.getModifiers());

    private static final Predicate<Method> SETTER_PREDICATE = (Method method) ->
        method.getName().startsWith("set") && method.getParameterCount() == 1 && Modifier.isPublic(method.getModifiers());

    public <T> Optional<T> newInstance(Class<T> clazz) {
        return newInstanceGenericAware(clazz, null);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> newInstanceGenericAware(Class<T> clazz, Type genericType) {
        if (clazz == null) {
            throw new NullPointerException();
        } else if (clazz == void.class || Void.class.isAssignableFrom(clazz)) {
            log.debug("Could not instantiate class: {}", clazz);
            return empty();
        } else if (clazz == boolean.class || Boolean.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getBoolean());
        } else if (clazz == byte.class || Byte.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getByte());
        } else if (clazz == char.class || Character.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getCharacter());
        } else if (clazz == short.class || Short.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getShort());
        } else if (clazz == int.class || Integer.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getInteger());
        } else if (clazz == long.class || Long.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getLong());
        } else if (clazz == float.class || Float.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getFloat());
        } else if (clazz == double.class || Double.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getDouble());
        } else if (CharSequence.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getCharSequence());
        } else if (Collection.class.isAssignableFrom(clazz)) {
            return of((T) newCollectionInstance(clazz, genericType));
        } else if (clazz.isArray()) {
            return of(newArrayInstance(clazz).orElseGet(() -> newEmptyArrayInstance(clazz)));
        } else if (clazz.isEnum()) {
            return Stream.of(clazz.getEnumConstants()).findFirst();
        } else {
            return newObject(clazz);
        }
    }

    protected Collection<?> newCollectionInstance(Class<?> collectionClass, Type genericType) {
        if (collectionClass == Object.class) {
            return emptyList();
        } else if (genericType instanceof ParameterizedType) {
            val parameterizedType = (ParameterizedType) genericType;
            val actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length == 1) {
                val elementType = actualTypeArguments[0];
                try {
                    val elementClass = Class.forName(elementType.getTypeName());
                    val element = newInstance(elementClass);
                    return element
                        .map(Collections::singletonList)
                        .orElseGet(Collections::emptyList);
                } catch (ClassNotFoundException e) {
                    log.debug("Could not instantiate element {} of {}", elementType, genericType);
                    return emptyList();
                }
            } else {
                log.debug("Could not infer element type: {}", genericType);
                return emptyList();
            }
        } else {
            log.trace("Finding generic type in super class of {}", collectionClass);
            return newCollectionInstance(collectionClass.getSuperclass(), collectionClass.getGenericSuperclass());
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> Optional<T> newArrayInstance(Class<T> arrayClass) {
        val componentType = arrayClass.getComponentType();
        try {
            log.trace("Instantiating array of {}", componentType);
            return newInstance(componentType).map(instance -> {
                val array = Array.newInstance(componentType, 1);
                Array.set(array, 0, instance);
                return (T) array;
            });
        } catch (IllegalArgumentException e) {
            log.debug("Could not instantiate array component: ", componentType, e);
            return empty();
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T newEmptyArrayInstance(Class<T> arrayClass) {
        val componentType = arrayClass.getComponentType();
        log.trace("Instantiating empty array of {}", componentType);
        return (T) Array.newInstance(componentType, 0);
    }

    @SuppressWarnings("unchecked")
    protected <T> Optional<T> newObject(Class<T> clazz) {
        if (isAbstract(clazz.getModifiers())) {
            log.debug("Could not instantiate abstract class: {}", clazz);
            return empty();
        }
        if (isInterface(clazz.getModifiers())) {
            log.debug("Could not instantiate interface: {}", clazz);
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
                        return newInstanceGenericAware(parameterClass, parameterType);
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
            .findFirst()
            .map(instance -> {
                Stream.of(clazz.getMethods())
                    .filter(SETTER_PREDICATE)
                    .forEach(setter -> {
                        val parameterClass = setter.getParameterTypes()[0];
                        val parameterType = setter.getGenericParameterTypes()[0];
                        newInstanceGenericAware(parameterClass, parameterType).map((parameter) -> {
                            try {
                                log.trace("Invoking setter: {} with {}", setter, parameterType);
                                setter.invoke(instance, parameter);
                            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                log.debug("Could not invoke setter: {} with {}", setter, parameterType, e);
                            }
                            return null;
                        });
                    });
                return instance;
            });
    }
}
