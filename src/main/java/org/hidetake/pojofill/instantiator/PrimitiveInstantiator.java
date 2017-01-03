package org.hidetake.pojofill.instantiator;

import lombok.RequiredArgsConstructor;
import org.hidetake.pojofill.RootInstantiator;
import org.hidetake.pojofill.context.InstantiationContext;

import java.lang.reflect.Type;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * A class to instantiate a primitive value.
 *
 * @author Hidetake Iwata
 */
@RequiredArgsConstructor
public class PrimitiveInstantiator implements Instantiator {
    private final ValueProvider valueProvider;

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> newInstance(RootInstantiator rootInstantiator, Class<T> clazz, Type genericParameterType, InstantiationContext context) {
        if (clazz == void.class || Void.class.isAssignableFrom(clazz)) {
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
        } else {
            return empty();
        }
    }
}
