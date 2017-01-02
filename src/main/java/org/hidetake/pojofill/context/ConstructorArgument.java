package org.hidetake.pojofill.context;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

/**
 * A class to represent the context to instantiate an argument for a constructor.
 *
 * @author Hidetake Iwata
 */
@Data
@RequiredArgsConstructor
public class ConstructorArgument implements InstantiationContext {
    private final Constructor constructor;
    private final Parameter parameter;
}
