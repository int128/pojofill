package org.hidetake.pojofill;

/**
 * A class providing value for instantiation.
 *
 * @author Hidetake Iwata
 */
public class ValueProvider {
    public Boolean getBoolean() {
        return true;
    }

    public Byte getByte() {
        return 123;
    }

    public Character getCharacter() {
        return 'a';
    }

    public Short getShort() {
        return 12345;
    }

    public Integer getInteger() {
        return 123456;
    }

    public Long getLong() {
        return 1234567890L;
    }

    public Float getFloat() {
        return 1.23456f;
    }

    public Double getDouble() {
        return 1.23456;
    }

    public CharSequence getCharSequence() {
        return "abcde";
    }
}
