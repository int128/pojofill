package org.hidetake.pojofill;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompositeOfPrimitives {
    private Primitives primitives;
    private FinalPrimitives finalPrimitives;
    private PrimitiveArrays primitiveArrays;
    private FinalPrimitiveArrays finalPrimitiveArrays;

    private AnInterface anInterface;
    private AnAbstractClass anAbstractClass;
    private AnInterface[] anInterfaces;
    private AnAbstractClass[] anAbstractClasses;
}
