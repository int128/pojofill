package org.hidetake.pojofill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FinalCompositeOfPrimitives {
    private final Primitives primitives;
    private final FinalPrimitives finalPrimitives;
    private final PrimitiveArrays primitiveArrays;
    private final FinalPrimitiveArrays finalPrimitiveArrays;
}
