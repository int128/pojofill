package org.hidetake.pojofill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;

@Slf4j
@RequiredArgsConstructor
@Getter
public class Pojofill {
    private final ValueProvider valueProvider = new ValueProvider();

    private static final Predicate<Constructor> CONSTRUCTOR_PREDICATE = (Constructor constructor) ->
        Modifier.isPublic(constructor.getModifiers());

    private static final Predicate<Method> SETTER_PREDICATE = (Method method) ->
        method.getName().startsWith("set") && method.getParameterCount() == 1 && Modifier.isPublic(method.getModifiers());

    @SuppressWarnings("unchecked")
    public <T> T newInstance(Class<T> clazz) {
        if (clazz == null) {
            return null;
        } else if (clazz == void.class || Void.class.isAssignableFrom(clazz)) {
            return null;
        } else if (clazz == boolean.class || Boolean.class.isAssignableFrom(clazz)) {
            return (T) valueProvider.getBoolean();
        } else if (clazz == byte.class || Byte.class.isAssignableFrom(clazz)) {
            return (T) valueProvider.getByte();
        } else if (clazz == char.class || Character.class.isAssignableFrom(clazz)) {
            return (T) valueProvider.getCharacter();
        } else if (clazz == short.class || Short.class.isAssignableFrom(clazz)) {
            return (T) valueProvider.getShort();
        } else if (clazz == int.class || Integer.class.isAssignableFrom(clazz)) {
            return (T) valueProvider.getInteger();
        } else if (clazz == long.class || Long.class.isAssignableFrom(clazz)) {
            return (T) valueProvider.getLong();
        } else if (clazz == float.class || Float.class.isAssignableFrom(clazz)) {
            return (T) valueProvider.getFloat();
        } else if (clazz == double.class || Double.class.isAssignableFrom(clazz)) {
            return (T) valueProvider.getDouble();
        } else if (CharSequence.class.isAssignableFrom(clazz)) {
            return (T) valueProvider.getCharSequence();
        } else if (clazz.isEnum()) {
            val enumConstants = clazz.getEnumConstants();
            if (enumConstants.length > 0) {
                return enumConstants[0];
            } else {
                return null;
            }
        } else if (clazz.isArray()) {
            val componentType = clazz.getComponentType();
            try {
                val array = Array.newInstance(componentType, 1);
                Array.set(array, 0, newInstance(componentType));
                return (T) array;
            } catch (IllegalArgumentException e) {
                log.debug("Could not instantiate array: {}", clazz, e);
                return null;
            }
        } else if (Collection.class.isAssignableFrom(clazz)) {
            return newInstanceGenericAware(clazz, clazz.getGenericSuperclass());
        } else {
            return (T) Stream.of(clazz.getConstructors())
                .filter(CONSTRUCTOR_PREDICATE)
                .max(comparingInt(Constructor::getParameterCount))
                .map(constructor -> {
                    val args = IntStream.range(0, constructor.getParameterCount()).mapToObj(index -> {
                        val parameterClass = constructor.getParameterTypes()[index];
                        val parameterType = constructor.getGenericParameterTypes()[index];
                        return newInstanceGenericAware(parameterClass, parameterType);
                    }).toArray();
                    try {
                        return constructor.newInstance(args);
                    } catch (IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        log.debug("Could not invoke constructor: {} with {}", constructor, args, e);
                        return null;
                    }
                })
                .map(instance -> {
                    Stream.of(clazz.getMethods())
                        .filter(SETTER_PREDICATE)
                        .forEach(setter -> {
                            val parameterClass = setter.getParameterTypes()[0];
                            val parameterType = setter.getGenericParameterTypes()[0];
                            val parameter = newInstanceGenericAware(parameterClass, parameterType);
                            try {
                                setter.invoke(instance, parameter);
                            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                                log.debug("Could not invoke setter: {} with {}", setter, parameterType, e);
                            }
                        });
                    return instance;
                })
                .orElse(null);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T newInstanceGenericAware(Class<T> clazz, Type genericType) {
        if (Collection.class.isAssignableFrom(clazz)) {
            if (genericType instanceof ParameterizedType) {
                val parameterizedType = (ParameterizedType) genericType;
                val actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length == 1) {
                    val elementType = actualTypeArguments[0];
                    try {
                        val elementClass = Class.forName(elementType.getTypeName());
                        val element = newInstance(elementClass);
                        return (T) Collections.singletonList(element);
                    } catch (ClassNotFoundException e) {
                        log.debug("Could not instantiate class: {}", elementType);
                        return (T) Collections.emptyList();
                    }
                } else {
                    log.debug("Could not infer element type: {}", genericType);
                    return (T) Collections.emptyList();
                }
            } else {
                log.debug("Could not infer element type: {}", genericType);
                return (T) Collections.emptyList();
            }
        } else {
            return newInstance(clazz);
        }
    }
}
