package org.hidetake.pojofill

import org.hidetake.pojofill.context.InstantiationContext
import org.hidetake.pojofill.context.SetterArgument
import org.hidetake.pojofill.fixture.*
import org.hidetake.pojofill.instantiator.DefaultValueProvider
import org.hidetake.pojofill.instantiator.PrimitiveInstantiator
import spock.lang.Specification

class PojofillSpec extends Specification {

    Pojofill pojofill

    def setup() {
        pojofill = new Pojofill()
    }

    def 'newInstance(null) should throw NPE'() {
        when:
        pojofill.newInstance(null)

        then:
        thrown(NullPointerException)
    }

    def 'newInstance(class with primitive setters) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(Primitives)

        then:
        instance instanceof Primitives
        instance.aBoolean == DefaultValueProvider.BOOLEAN
        instance.aByte == DefaultValueProvider.BYTE
        instance.aChar == DefaultValueProvider.CHAR
        instance.aShort == DefaultValueProvider.SHORT
        instance.anInt == DefaultValueProvider.INT
        instance.aLong == DefaultValueProvider.LONG
        instance.aFloat == DefaultValueProvider.FLOAT
        instance.aDouble == DefaultValueProvider.DOUBLE
        instance.string == DefaultValueProvider.STRING
        instance.anEnum == AnEnum.FOO
    }

    def 'newInstance(class with primitive constructors) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(FinalPrimitives)

        then:
        instance instanceof FinalPrimitives
        instance.aBoolean == DefaultValueProvider.BOOLEAN
        instance.aByte == DefaultValueProvider.BYTE
        instance.aChar == DefaultValueProvider.CHAR
        instance.aShort == DefaultValueProvider.SHORT
        instance.anInt == DefaultValueProvider.INT
        instance.aLong == DefaultValueProvider.LONG
        instance.aFloat == DefaultValueProvider.FLOAT
        instance.aDouble == DefaultValueProvider.DOUBLE
        instance.string == DefaultValueProvider.STRING
        instance.anEnum == AnEnum.FOO
    }

    def 'newInstance(class with primitive array setters) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(PrimitiveArrays)

        then:
        instance instanceof PrimitiveArrays
        instance.booleans.length == 1
        instance.bytes.length == 1
        instance.chars.length == 1
        instance.shorts.length == 1
        instance.ints.length == 1
        instance.longs.length == 1
        instance.floats.length == 1
        instance.doubles.length == 1
        instance.strings.length == 1
        instance.anEnums.length == 1

        instance.booleans[0] == DefaultValueProvider.BOOLEAN
        instance.bytes[0] == DefaultValueProvider.BYTE
        instance.chars[0] == DefaultValueProvider.CHAR
        instance.shorts[0] == DefaultValueProvider.SHORT
        instance.ints[0] == DefaultValueProvider.INT
        instance.longs[0] == DefaultValueProvider.LONG
        instance.floats[0] == DefaultValueProvider.FLOAT
        instance.doubles[0] == DefaultValueProvider.DOUBLE
        instance.strings[0] == DefaultValueProvider.STRING
        instance.anEnums[0] == AnEnum.FOO
    }

    def 'newInstance(class with primitive array constructors) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(FinalPrimitiveArrays)

        then:
        instance.booleans.length == 1
        instance.bytes.length == 1
        instance.chars.length == 1
        instance.shorts.length == 1
        instance.ints.length == 1
        instance.longs.length == 1
        instance.floats.length == 1
        instance.doubles.length == 1
        instance.strings.length == 1
        instance.anEnums.length == 1

        instance.booleans[0] == DefaultValueProvider.BOOLEAN
        instance.bytes[0] == DefaultValueProvider.BYTE
        instance.chars[0] == DefaultValueProvider.CHAR
        instance.shorts[0] == DefaultValueProvider.SHORT
        instance.ints[0] == DefaultValueProvider.INT
        instance.longs[0] == DefaultValueProvider.LONG
        instance.floats[0] == DefaultValueProvider.FLOAT
        instance.doubles[0] == DefaultValueProvider.DOUBLE
        instance.strings[0] == DefaultValueProvider.STRING
        instance.anEnums[0] == AnEnum.FOO
    }

    def 'newInstance(composite of primitives) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(CompositeOfPrimitives)

        then:
        instance instanceof CompositeOfPrimitives

        instance.primitives.aBoolean == DefaultValueProvider.BOOLEAN
        instance.primitives.aByte == DefaultValueProvider.BYTE
        instance.primitives.aChar == DefaultValueProvider.CHAR
        instance.primitives.aShort == DefaultValueProvider.SHORT
        instance.primitives.anInt == DefaultValueProvider.INT
        instance.primitives.aLong == DefaultValueProvider.LONG
        instance.primitives.aFloat == DefaultValueProvider.FLOAT
        instance.primitives.aDouble == DefaultValueProvider.DOUBLE
        instance.primitives.string == DefaultValueProvider.STRING
        instance.primitives.anEnum == AnEnum.FOO

        instance.finalPrimitives.aBoolean == DefaultValueProvider.BOOLEAN
        instance.finalPrimitives.aByte == DefaultValueProvider.BYTE
        instance.finalPrimitives.aChar == DefaultValueProvider.CHAR
        instance.finalPrimitives.aShort == DefaultValueProvider.SHORT
        instance.finalPrimitives.anInt == DefaultValueProvider.INT
        instance.finalPrimitives.aLong == DefaultValueProvider.LONG
        instance.finalPrimitives.aFloat == DefaultValueProvider.FLOAT
        instance.finalPrimitives.aDouble == DefaultValueProvider.DOUBLE
        instance.finalPrimitives.string == DefaultValueProvider.STRING
        instance.finalPrimitives.anEnum == AnEnum.FOO

        instance.primitiveArrays.booleans.length == 1
        instance.primitiveArrays.bytes.length == 1
        instance.primitiveArrays.chars.length == 1
        instance.primitiveArrays.shorts.length == 1
        instance.primitiveArrays.ints.length == 1
        instance.primitiveArrays.longs.length == 1
        instance.primitiveArrays.floats.length == 1
        instance.primitiveArrays.doubles.length == 1
        instance.primitiveArrays.strings.length == 1
        instance.primitiveArrays.anEnums.length == 1
        instance.primitiveArrays.booleans[0] == DefaultValueProvider.BOOLEAN
        instance.primitiveArrays.bytes[0] == DefaultValueProvider.BYTE
        instance.primitiveArrays.chars[0] == DefaultValueProvider.CHAR
        instance.primitiveArrays.shorts[0] == DefaultValueProvider.SHORT
        instance.primitiveArrays.ints[0] == DefaultValueProvider.INT
        instance.primitiveArrays.longs[0] == DefaultValueProvider.LONG
        instance.primitiveArrays.floats[0] == DefaultValueProvider.FLOAT
        instance.primitiveArrays.doubles[0] == DefaultValueProvider.DOUBLE
        instance.primitiveArrays.strings[0] == DefaultValueProvider.STRING
        instance.primitiveArrays.anEnums[0] == AnEnum.FOO

        instance.finalPrimitiveArrays.booleans.length == 1
        instance.finalPrimitiveArrays.bytes.length == 1
        instance.finalPrimitiveArrays.chars.length == 1
        instance.finalPrimitiveArrays.shorts.length == 1
        instance.finalPrimitiveArrays.ints.length == 1
        instance.finalPrimitiveArrays.longs.length == 1
        instance.finalPrimitiveArrays.floats.length == 1
        instance.finalPrimitiveArrays.doubles.length == 1
        instance.finalPrimitiveArrays.strings.length == 1
        instance.finalPrimitiveArrays.anEnums.length == 1
        instance.finalPrimitiveArrays.booleans[0] == DefaultValueProvider.BOOLEAN
        instance.finalPrimitiveArrays.bytes[0] == DefaultValueProvider.BYTE
        instance.finalPrimitiveArrays.chars[0] == DefaultValueProvider.CHAR
        instance.finalPrimitiveArrays.shorts[0] == DefaultValueProvider.SHORT
        instance.finalPrimitiveArrays.ints[0] == DefaultValueProvider.INT
        instance.finalPrimitiveArrays.longs[0] == DefaultValueProvider.LONG
        instance.finalPrimitiveArrays.floats[0] == DefaultValueProvider.FLOAT
        instance.finalPrimitiveArrays.doubles[0] == DefaultValueProvider.DOUBLE
        instance.finalPrimitiveArrays.strings[0] == DefaultValueProvider.STRING
        instance.finalPrimitiveArrays.anEnums[0] == AnEnum.FOO

        instance.anInterface == null
        instance.anAbstractClass == null
        instance.anInterfaces.length == 0
        instance.anAbstractClasses.length == 0
    }

    def 'newInstance(final composite class) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(FinalCompositeOfPrimitives)

        then:
        instance instanceof FinalCompositeOfPrimitives

        instance.primitives.aBoolean == DefaultValueProvider.BOOLEAN
        instance.primitives.aByte == DefaultValueProvider.BYTE
        instance.primitives.aChar == DefaultValueProvider.CHAR
        instance.primitives.aShort == DefaultValueProvider.SHORT
        instance.primitives.anInt == DefaultValueProvider.INT
        instance.primitives.aLong == DefaultValueProvider.LONG
        instance.primitives.aFloat == DefaultValueProvider.FLOAT
        instance.primitives.aDouble == DefaultValueProvider.DOUBLE
        instance.primitives.string == DefaultValueProvider.STRING
        instance.primitives.anEnum == AnEnum.FOO

        instance.finalPrimitives.aBoolean == DefaultValueProvider.BOOLEAN
        instance.finalPrimitives.aByte == DefaultValueProvider.BYTE
        instance.finalPrimitives.aChar == DefaultValueProvider.CHAR
        instance.finalPrimitives.aShort == DefaultValueProvider.SHORT
        instance.finalPrimitives.anInt == DefaultValueProvider.INT
        instance.finalPrimitives.aLong == DefaultValueProvider.LONG
        instance.finalPrimitives.aFloat == DefaultValueProvider.FLOAT
        instance.finalPrimitives.aDouble == DefaultValueProvider.DOUBLE
        instance.finalPrimitives.string == DefaultValueProvider.STRING
        instance.finalPrimitives.anEnum == AnEnum.FOO

        instance.primitiveArrays.booleans.length == 1
        instance.primitiveArrays.bytes.length == 1
        instance.primitiveArrays.chars.length == 1
        instance.primitiveArrays.shorts.length == 1
        instance.primitiveArrays.ints.length == 1
        instance.primitiveArrays.longs.length == 1
        instance.primitiveArrays.floats.length == 1
        instance.primitiveArrays.doubles.length == 1
        instance.primitiveArrays.strings.length == 1
        instance.primitiveArrays.anEnums.length == 1
        instance.primitiveArrays.booleans[0] == DefaultValueProvider.BOOLEAN
        instance.primitiveArrays.bytes[0] == DefaultValueProvider.BYTE
        instance.primitiveArrays.chars[0] == DefaultValueProvider.CHAR
        instance.primitiveArrays.shorts[0] == DefaultValueProvider.SHORT
        instance.primitiveArrays.ints[0] == DefaultValueProvider.INT
        instance.primitiveArrays.longs[0] == DefaultValueProvider.LONG
        instance.primitiveArrays.floats[0] == DefaultValueProvider.FLOAT
        instance.primitiveArrays.doubles[0] == DefaultValueProvider.DOUBLE
        instance.primitiveArrays.strings[0] == DefaultValueProvider.STRING
        instance.primitiveArrays.anEnums[0] == AnEnum.FOO

        instance.finalPrimitiveArrays.booleans.length == 1
        instance.finalPrimitiveArrays.bytes.length == 1
        instance.finalPrimitiveArrays.chars.length == 1
        instance.finalPrimitiveArrays.shorts.length == 1
        instance.finalPrimitiveArrays.ints.length == 1
        instance.finalPrimitiveArrays.longs.length == 1
        instance.finalPrimitiveArrays.floats.length == 1
        instance.finalPrimitiveArrays.doubles.length == 1
        instance.finalPrimitiveArrays.strings.length == 1
        instance.finalPrimitiveArrays.anEnums.length == 1
        instance.finalPrimitiveArrays.booleans[0] == DefaultValueProvider.BOOLEAN
        instance.finalPrimitiveArrays.bytes[0] == DefaultValueProvider.BYTE
        instance.finalPrimitiveArrays.chars[0] == DefaultValueProvider.CHAR
        instance.finalPrimitiveArrays.shorts[0] == DefaultValueProvider.SHORT
        instance.finalPrimitiveArrays.ints[0] == DefaultValueProvider.INT
        instance.finalPrimitiveArrays.longs[0] == DefaultValueProvider.LONG
        instance.finalPrimitiveArrays.floats[0] == DefaultValueProvider.FLOAT
        instance.finalPrimitiveArrays.doubles[0] == DefaultValueProvider.DOUBLE
        instance.finalPrimitiveArrays.strings[0] == DefaultValueProvider.STRING
        instance.finalPrimitiveArrays.anEnums[0] == AnEnum.FOO

        instance.anInterface == null
        instance.anAbstractClass == null
        instance.anInterfaces.length == 0
        instance.anAbstractClasses.length == 0
    }

    def 'addInstantiator should add custom instantiator at first'() {
        given:
        pojofill.addInstantiator(new PrimitiveInstantiator(new DefaultValueProvider() {
            @Override
            Integer getInteger(InstantiationContext context) {
                -100
            }

            @Override
            CharSequence getCharSequence(InstantiationContext context) {
                if (context instanceof SetterArgument) {
                    context.setter.name
                } else {
                    super.getCharSequence(context)
                }
            }
        }))

        when:
        def instance = pojofill.newInstanceOrNull(Primitives)

        then:
        instance instanceof Primitives
        instance.aBoolean == DefaultValueProvider.BOOLEAN
        instance.aByte == DefaultValueProvider.BYTE
        instance.aChar == DefaultValueProvider.CHAR
        instance.aShort == DefaultValueProvider.SHORT
        instance.anInt == -100
        instance.aLong == DefaultValueProvider.LONG
        instance.aFloat == DefaultValueProvider.FLOAT
        instance.aDouble == DefaultValueProvider.DOUBLE
        instance.string == 'setString'
        instance.anEnum == AnEnum.FOO
    }

}
