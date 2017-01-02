package org.hidetake.pojofill.context;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * A class to represent the context to instantiate an argument for a setter method.
 *
 * @author Hidetake Iwata
 */
@Data
@RequiredArgsConstructor
public class SetterArgument implements InstantiationContext {
    private final Method setter;
    private final Parameter parameter;
}
