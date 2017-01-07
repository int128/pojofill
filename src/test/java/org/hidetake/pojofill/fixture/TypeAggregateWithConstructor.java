package org.hidetake.pojofill.fixture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TypeAggregateWithConstructor {
    private final boolean aBoolean;
    private final byte aByte;
    private final char aChar;
    private final short aShort;
    private final int anInt;
    private final long aLong;
    private final float aFloat;
    private final double aDouble;
    private final String string;
    private final AnEnum anEnum;
    private final AnInterface anInterface;
    private final AnAbstractClass anAbstractClass;

    private final boolean[] booleans;
    private final byte[] bytes;
    private final char[] chars;
    private final short[] shorts;
    private final int[] ints;
    private final long[] longs;
    private final float[] floats;
    private final double[] doubles;
    private final String[] strings;
    private final AnEnum[] anEnums;
    private final AnInterface[] anInterfaces;
    private final AnAbstractClass[] anAbstractClasses;
}
