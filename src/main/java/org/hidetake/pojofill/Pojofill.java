package org.hidetake.pojofill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hidetake.pojofill.context.TopLevel;
import org.hidetake.pojofill.instantiator.Instantiator;

import java.util.Optional;

/**
 * A root class of Pojofill.
 *
 * @author Hidetake Iwata
 */
@RequiredArgsConstructor
@Getter
public class Pojofill {
    private final RootInstantiator rootInstantiator = new RootInstantiator();

    private static final TopLevel TOP_LEVEL = new TopLevel();

    /**
     * Create an new instance.
     *
     * @param clazz class to instantiate
     * @param <T> type of the class
     * @return an instance or {@link Optional#empty()} if error occurred
     */
    public <T> Optional<T> newInstance(Class<T> clazz) {
        return rootInstantiator.newInstance(clazz, null, TOP_LEVEL);
    }

    /**
     * Create an new instance.
     *
     * @param clazz class to instantiate
     * @param <T> type of the class
     * @return an instance or null if error occurred
     */
    public <T> T newInstanceOrNull(Class<T> clazz) {
        return newInstance(clazz).orElse(null);
    }

    /**
     * Add an {@link Instantiator} at first.
     *
     * @param instantiator an instantiator tried at first
     */
    public void addInstantiator(Instantiator instantiator) {
        rootInstantiator.addInstantiator(instantiator);
    }
}
