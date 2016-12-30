package org.hidetake.pojofill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.emptyList;

/**
 * A class to instantiate a collection class.
 *
 * @author Hidetake Iwata
 */
@Slf4j
@RequiredArgsConstructor
public class CollectionInstantiator {
    private final Instantiator instantiator;

    /**
     * Create an new instance.
     *
     * @param clazz class to instantiate
     * @return a list with 1 element or empty list if error occurred
     */
    public Collection<?> newInstance(Class<?> clazz, Type genericParameterType) {
        if (clazz == Object.class) {
            return emptyList();
        } else if (genericParameterType instanceof ParameterizedType) {
            val parameterizedType = (ParameterizedType) genericParameterType;
            val actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length == 1) {
                val elementType = actualTypeArguments[0];
                try {
                    val elementClass = Class.forName(elementType.getTypeName());
                    val element = instantiator.newInstance(elementClass);
                    return element
                        .map(Collections::singletonList)
                        .orElseGet(Collections::emptyList);
                } catch (ClassNotFoundException e) {
                    log.debug("Could not instantiate element {} of {}", elementType, genericParameterType);
                    return emptyList();
                }
            } else {
                log.debug("Could not infer element type: {}", genericParameterType);
                return emptyList();
            }
        } else {
            log.trace("Finding generic type in super class of {}", clazz);
            return newInstance(clazz.getSuperclass(), clazz.getGenericSuperclass());
        }
    }
}
