package org.hidetake.pojofill.instantiator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hidetake.pojofill.RootInstantiator;
import org.hidetake.pojofill.context.ArrayElement;
import org.hidetake.pojofill.context.InstantiationContext;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * A class to instantiate an array.
 *
 * @author Hidetake Iwata
 */
@Slf4j
@RequiredArgsConstructor
public class ArrayInstantiator implements Instantiator {
    @Override
    public <T> Optional<T> newInstance(RootInstantiator rootInstantiator, Class<T> clazz, Type genericParameterType, InstantiationContext context) {
        if (clazz.isArray()) {
            return of(newArray(rootInstantiator, clazz));
        } else {
            return empty();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T newArray(RootInstantiator rootInstantiator, Class<T> clazz) {
        val context = new ArrayElement(clazz);
        val componentType = clazz.getComponentType();
        return rootInstantiator.newInstance(componentType, null, context)
            .flatMap(instance -> {
                try {
                    log.trace("Calling constructor of {}", clazz);
                    val array = (T) Array.newInstance(componentType, 1);
                    log.trace("Calling setter of {} with {}", clazz, instance);
                    Array.set(array, 0, instance);
                    return of(array);
                } catch (IllegalArgumentException e) {
                    log.debug("Could not call setter for {}", context, e);
                    return empty();
                }
            }).orElseGet(() -> {
                log.debug("Fallback to empty array for {}", context);
                return (T) Array.newInstance(componentType, 0);
            });
    }
}
