package org.hidetake.pojofill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hidetake.pojofill.context.TopLevel;

import java.util.Optional;

/**
 * A root class of Pojofill.
 *
 * @author Hidetake Iwata
 */
@RequiredArgsConstructor
@Getter
public class Pojofill {
    private final ValueProvider valueProvider = new DefaultValueProvider();
    private final Instantiator instantiator = new Instantiator(valueProvider);

    private static final TopLevel TOP_LEVEL = new TopLevel();

    /**
     * Create an new instance.
     *
     * @param clazz class to instantiate
     * @param <T> type of the class
     * @return an instance or {@link Optional#empty()} if error occurred
     */
    public <T> Optional<T> newInstance(Class<T> clazz) {
        return instantiator.newInstance(clazz, null, TOP_LEVEL);
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
}
