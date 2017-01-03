package org.hidetake.pojofill.instantiator;

import org.hidetake.pojofill.RootInstantiator;
import org.hidetake.pojofill.context.InstantiationContext;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.empty;

/**
 * A class to instantiate an enum.
 *
 * @author Hidetake Iwata
 */
public class EnumInstantiator implements Instantiator {
    @Override
    public <T> Optional<T> newInstance(RootInstantiator rootInstantiator, Class<T> clazz, Type genericParameterType, InstantiationContext context) {
        if (clazz.isEnum()) {
            return Stream.of(clazz.getEnumConstants()).findFirst();
        } else {
            return empty();
        }
    }
}
