package org.hidetake.pojofill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * A class to instantiate a primitive, collection, array or object.
 *
 * @author Hidetake Iwata
 */
@Slf4j
@RequiredArgsConstructor
@Getter
public class Instantiator {
    private final ValueProvider valueProvider;

    private final CollectionInstantiator collectionInstantiator = new CollectionInstantiator(this);
    private final ArrayInstantiator arrayInstantiator = new ArrayInstantiator(this);
    private final ObjectInstantiator objectInstantiator = new ObjectInstantiator(this);

    /**
     * Create an new instance.
     *
     * @param clazz class to instantiate
     * @param <T> type of the class
     * @return an instance or {@link Optional#empty()} if error occurred
     */
    public <T> Optional<T> newInstance(Class<T> clazz) {
        return newInstance(clazz, null);
    }

    /**
     * Create an new instance.
     *
     * @param clazz class to instantiate
     * @param genericParameterType type parameter of the collection class (or null)
     * @param <T> type of the class
     * @return an instance or {@link Optional#empty()} if error occurred
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> newInstance(Class<T> clazz, Type genericParameterType) {
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
            return of((T) collectionInstantiator.newInstance(clazz, genericParameterType));
        } else if (clazz.isArray()) {
            return of(arrayInstantiator.newInstance(clazz));
        } else if (clazz.isEnum()) {
            return Stream.of(clazz.getEnumConstants()).findFirst();
        } else {
            return objectInstantiator.newInstance(clazz);
        }
    }
}
