package org.hidetake.pojofill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hidetake.pojofill.context.InstantiationContext;

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
     * @param genericParameterType type parameter of the collection class (or null)
     * @param context instantiation context
     * @param <T> type of the class
     * @return an instance or {@link Optional#empty()} if error occurred
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> newInstance(Class<T> clazz, Type genericParameterType, InstantiationContext context) {
        if (clazz == null) {
            throw new NullPointerException();
        } else if (clazz == void.class || Void.class.isAssignableFrom(clazz)) {
            log.debug("Could not instantiate class: {}", clazz);
            return empty();
        } else if (clazz == boolean.class || Boolean.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getBoolean(context));
        } else if (clazz == byte.class || Byte.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getByte(context));
        } else if (clazz == char.class || Character.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getCharacter(context));
        } else if (clazz == short.class || Short.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getShort(context));
        } else if (clazz == int.class || Integer.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getInteger(context));
        } else if (clazz == long.class || Long.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getLong(context));
        } else if (clazz == float.class || Float.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getFloat(context));
        } else if (clazz == double.class || Double.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getDouble(context));
        } else if (CharSequence.class.isAssignableFrom(clazz)) {
            return of((T) valueProvider.getCharSequence(context));
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
