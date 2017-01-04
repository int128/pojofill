package org.hidetake.pojofill.instantiator;

import org.hidetake.pojofill.RootInstantiator;
import org.hidetake.pojofill.context.InstantiationContext;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * An interface for instantiation.
 *
 * @author Hidetake Iwata
 */
public interface Instantiator {
    /**
     * Create an new instance.
     * This should return {@link Optional#empty()} if it could not instantiate the class.
     *
     * @param <T> type of the class
     * @param rootInstantiator root instantiator to instantiate recursively
     * @param clazz class to instantiate
     * @param genericParameterType type parameter of the collection class (or null)
     * @param context instantiation context
     * @return an instance or {@link Optional#empty()}
     */
    <T> Optional<T> newInstance(RootInstantiator rootInstantiator, Class<T> clazz, Type genericParameterType, InstantiationContext context);
}
