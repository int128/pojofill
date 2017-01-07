package org.hidetake.pojofill.fixture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeAggregateWithSetters {
    private boolean aBoolean;
    private byte aByte;
    private char aChar;
    private short aShort;
    private int anInt;
    private long aLong;
    private float aFloat;
    private double aDouble;
    private String string;
    private AnEnum anEnum;
    private AnInterface anInterface;
    private AnAbstractClass anAbstractClass;

    private boolean[] booleans;
    private byte[] bytes;
    private char[] chars;
    private short[] shorts;
    private int[] ints;
    private long[] longs;
    private float[] floats;
    private double[] doubles;
    private String[] strings;
    private AnEnum[] anEnums;
    private AnInterface[] anInterfaces;
    private AnAbstractClass[] anAbstractClasses;
}
