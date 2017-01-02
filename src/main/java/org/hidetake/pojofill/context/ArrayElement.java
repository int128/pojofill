package org.hidetake.pojofill.context;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * A class to represent the context to instantiate an element of an array.
 *
 * @author Hidetake Iwata
 */
@Data
@RequiredArgsConstructor
public class ArrayElement implements InstantiationContext {
    private final Class arrayClass;
}
