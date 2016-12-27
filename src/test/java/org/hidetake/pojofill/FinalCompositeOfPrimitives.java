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

    private final AnInterface anInterface;
    private final AnAbstractClass anAbstractClass;
    private final AnInterface[] anInterfaces;
    private final AnAbstractClass[] anAbstractClasses;
}
