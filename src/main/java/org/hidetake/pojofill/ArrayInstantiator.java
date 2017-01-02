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
        log.trace("Instantiating array of {}", componentType);
        return instantiator.newInstance(componentType, null, context).flatMap(instance -> {
            try {
                val array = (T) Array.newInstance(componentType, 1);
                Array.set(array, 0, instance);
                return of(array);
            } catch (IllegalArgumentException e) {
                log.debug("Could not instantiate array component: ", componentType, e);
                return empty();
            }
        }).orElseGet(() -> newEmptyArray(clazz));
    }

    @SuppressWarnings("unchecked")
    public <T> T newEmptyArray(Class<T> arrayClass) {
        val componentType = arrayClass.getComponentType();
        log.trace("Instantiating empty array of {}", componentType);
        return (T) Array.newInstance(componentType, 0);
    }
}
