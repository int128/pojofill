package org.hidetake.pojofill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hidetake.pojofill.context.ArrayElement;

import java.lang.reflect.Array;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * A class to instantiate an array.
 *
 * @author Hidetake Iwata
 */
@Slf4j
@RequiredArgsConstructor
public class ArrayInstantiator {
    private final Instantiator instantiator;

    /**
     * Create an new array.
     *
     * @param clazz class to instantiate
     * @param <T> type of the class
     * @return an array with 1 element or empty array if error occurred
     */
    @SuppressWarnings("unchecked")
    public <T> T newInstance(Class<T> clazz) {
        val context = new ArrayElement(clazz);
        val componentType = clazz.getComponentType();
        return instantiator.newInstance(componentType, null, context).flatMap(instance -> {
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
