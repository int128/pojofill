package org.hidetake.pojofill;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hidetake.pojofill.context.InstantiationContext;
import org.hidetake.pojofill.instantiator.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A class to instantiate a primitive, collection, array or object.
 *
 * @author Hidetake Iwata
 */
@Slf4j
public class RootInstantiator {
    private final LinkedList<Instantiator> instantiators = new LinkedList<>(
        Arrays.asList(
            new PrimitiveInstantiator(new DefaultValueProvider()),
            new EnumInstantiator(),
            new ArrayInstantiator(),
            new CollectionInstantiator(),
            new ObjectInstantiator()
        )
    );

    /**
     * Add an {@link Instantiator} at first.
     *
     * @param instantiator an instantiator tried at first
     */
    public void addInstantiator(Instantiator instantiator) {
        if (instantiator == null) {
            throw new NullPointerException("Instantiator parameter must not be null");
        }
        instantiators.addFirst(instantiator);
    }

    /**
     * Create an new instance.
     *
     * @param clazz class to instantiate
     * @param genericParameterType type parameter of the collection class (or null)
     * @param context instantiation context
     * @param <T> type of the class
     * @return an instance or {@link Optional#empty()} if error occurred
     */
    public <T> Optional<T> newInstance(Class<T> clazz, Type genericParameterType, InstantiationContext context) {
        if (clazz == null) {
            throw new NullPointerException("Class parameter must not be null");
        }
        if (context == null) {
            throw new NullPointerException("InstantiationContext parameter must not be null");
        }

        log.trace("Instantiating {} for {}", clazz, context);
        val instance = instantiators.stream()
            .flatMap(instantiator -> instantiator.newInstance(this, clazz, genericParameterType, context)
                .map(Stream::of)
                .orElse(Stream.empty()))
            .findFirst();

        if (!instance.isPresent()) {
            log.debug("Could not instantiate {} for {}", clazz, context);
        }
        return instance;
    }
}
