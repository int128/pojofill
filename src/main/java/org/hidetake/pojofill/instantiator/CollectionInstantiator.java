package org.hidetake.pojofill.instantiator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hidetake.pojofill.RootInstantiator;
import org.hidetake.pojofill.context.CollectionElement;
import org.hidetake.pojofill.context.InstantiationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * A class to instantiate a collection.
 *
 * @author Hidetake Iwata
 */
@Slf4j
@RequiredArgsConstructor
public class CollectionInstantiator implements Instantiator {
    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> newInstance(RootInstantiator rootInstantiator, Class<T> clazz, Type genericParameterType, InstantiationContext context) {
        if (Collection.class.isAssignableFrom(clazz)) {
            return of((T) newCollection(rootInstantiator, clazz, genericParameterType));
        } else {
            return empty();
        }
    }

    private Collection<?> newCollection(RootInstantiator rootInstantiator, Class<?> clazz, Type genericParameterType) {
        if (clazz == Object.class) {
            return emptyList();
        } else if (genericParameterType instanceof ParameterizedType) {
            val parameterizedType = (ParameterizedType) genericParameterType;
            val actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length == 1) {
                val elementType = actualTypeArguments[0];
                val context = new CollectionElement(clazz, parameterizedType);
                try {
                    val elementClass = Class.forName(elementType.getTypeName());
                    val element = rootInstantiator.newInstance(elementClass, null, context);
                    return element
                        .map(Collections::singletonList)
                        .orElseGet(Collections::emptyList);
                } catch (ClassNotFoundException e) {
                    log.debug("Fallback to empty collection for {}", context, e);
                    return emptyList();
                }
            } else {
                log.debug("Could not infer element type from type arguments {}", Arrays.toString(actualTypeArguments));
                return emptyList();
            }
        } else {
            log.trace("Finding generic type in super class of {}", clazz);
            return newCollection(rootInstantiator, clazz.getSuperclass(), clazz.getGenericSuperclass());
        }
    }
}
