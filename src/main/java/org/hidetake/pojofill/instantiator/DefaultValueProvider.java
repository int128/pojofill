package org.hidetake.pojofill.instantiator;

import org.hidetake.pojofill.context.InstantiationContext;

/**
 * A default class providing value for instantiation.
 *
 * @author Hidetake Iwata
 */
public class DefaultValueProvider implements ValueProvider {
    public static final boolean BOOLEAN = true;
    public static final byte BYTE = 123;
    public static final char CHAR = 'a';
    public static final short SHORT = 12345;
    public static final int INT = 123456;
    public static final long LONG = 1234567890L;
    public static final float FLOAT = 1.23456f;
    public static final double DOUBLE = 1.23456;
    public static final String STRING = "abcde";

    @Override
    public Boolean getBoolean(InstantiationContext context) {
        return BOOLEAN;
    }

    @Override
    public Byte getByte(InstantiationContext context) {
        return BYTE;
    }

    @Override
    public Character getCharacter(InstantiationContext context) {
        return CHAR;
    }

    @Override
    public Short getShort(InstantiationContext context) {
        return SHORT;
    }

    @Override
    public Integer getInteger(InstantiationContext context) {
        return INT;
    }

    @Override
    public Long getLong(InstantiationContext context) {
        return LONG;
    }

    @Override
    public Float getFloat(InstantiationContext context) {
        return FLOAT;
    }

    @Override
    public Double getDouble(InstantiationContext context) {
        return DOUBLE;
    }

    @Override
    public CharSequence getCharSequence(InstantiationContext context) {
        return STRING;
    }
}
