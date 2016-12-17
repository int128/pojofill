package org.hidetake.pojofill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FinalPrimitiveArrays {
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
}
