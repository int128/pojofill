package org.hidetake.pojofill;

import org.hidetake.pojofill.context.InstantiationContext;

/**
 * An interface for providing value for instantiation.
 *
 * @author Hidetake Iwata
 */
public interface ValueProvider {
    /**
     * Provide the value.
     * @param context instantiation context (or null if it is not member of a class)
     * @return the value
     */
    Boolean getBoolean(InstantiationContext context);

    /**
     * Provide the value.
     * @param context instantiation context (or null if it is not member of a class)
     * @return the value
     */
    Byte getByte(InstantiationContext context);

    /**
     * Provide the value.
     * @param context instantiation context (or null if it is not member of a class)
     * @return the value
     */
    Character getCharacter(InstantiationContext context);

    /**
     * Provide the value.
     * @param context instantiation context (or null if it is not member of a class)
     * @return the value
     */
    Short getShort(InstantiationContext context);

    /**
     * Provide the value.
     * @param context instantiation context (or null if it is not member of a class)
     * @return the value
     */
    Integer getInteger(InstantiationContext context);

    /**
     * Provide the value.
     * @param context instantiation context (or null if it is not member of a class)
     * @return the value
     */
    Long getLong(InstantiationContext context);

    /**
     * Provide the value.
     * @param context instantiation context (or null if it is not member of a class)
     * @return the value
     */
    Float getFloat(InstantiationContext context);

    /**
     * Provide the value.
     * @param context instantiation context (or null if it is not member of a class)
     * @return the value
     */
    Double getDouble(InstantiationContext context);

    /**
     * Provide the value.
     * @param context instantiation context (or null if it is not member of a class)
     * @return the value
     */
    CharSequence getCharSequence(InstantiationContext context);
}
