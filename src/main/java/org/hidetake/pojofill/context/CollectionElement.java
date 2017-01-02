package org.hidetake.pojofill.context;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.ParameterizedType;

/**
 * A class to represent the context to instantiate an element of a collection class.
 *
 * @author Hidetake Iwata
 */
@Data
@RequiredArgsConstructor
public class CollectionElement implements InstantiationContext {
    private final Class collectionClass;
    private final ParameterizedType parameterizedType;
}
