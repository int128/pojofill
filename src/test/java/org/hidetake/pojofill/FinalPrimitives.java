package org.hidetake.pojofill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FinalPrimitives {
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
}
