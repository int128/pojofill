package org.hidetake.pojofill

import org.hidetake.pojofill.fixture.*
import spock.lang.Specification

class PojofillSpec extends Specification {

    static final defaultValueProvider = new DefaultValueProvider()

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
        instance.aBoolean == defaultValueProvider.BOOLEAN
        instance.aByte == defaultValueProvider.BYTE
        instance.aChar == defaultValueProvider.CHAR
        instance.aShort == defaultValueProvider.SHORT
        instance.anInt == defaultValueProvider.INT
        instance.aLong == defaultValueProvider.LONG
        instance.aFloat == defaultValueProvider.FLOAT
        instance.aDouble == defaultValueProvider.DOUBLE
        instance.string == defaultValueProvider.STRING
        instance.anEnum == AnEnum.FOO
    }

    def 'newInstance(class with primitive constructors) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(FinalPrimitives)

        then:
        instance instanceof FinalPrimitives
        instance.aBoolean == defaultValueProvider.BOOLEAN
        instance.aByte == defaultValueProvider.BYTE
        instance.aChar == defaultValueProvider.CHAR
        instance.aShort == defaultValueProvider.SHORT
        instance.anInt == defaultValueProvider.INT
        instance.aLong == defaultValueProvider.LONG
        instance.aFloat == defaultValueProvider.FLOAT
        instance.aDouble == defaultValueProvider.DOUBLE
        instance.string == defaultValueProvider.STRING
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

        instance.booleans[0] == defaultValueProvider.BOOLEAN
        instance.bytes[0] == defaultValueProvider.BYTE
        instance.chars[0] == defaultValueProvider.CHAR
        instance.shorts[0] == defaultValueProvider.SHORT
        instance.ints[0] == defaultValueProvider.INT
        instance.longs[0] == defaultValueProvider.LONG
        instance.floats[0] == defaultValueProvider.FLOAT
        instance.doubles[0] == defaultValueProvider.DOUBLE
        instance.strings[0] == defaultValueProvider.STRING
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

        instance.booleans[0] == defaultValueProvider.BOOLEAN
        instance.bytes[0] == defaultValueProvider.BYTE
        instance.chars[0] == defaultValueProvider.CHAR
        instance.shorts[0] == defaultValueProvider.SHORT
        instance.ints[0] == defaultValueProvider.INT
        instance.longs[0] == defaultValueProvider.LONG
        instance.floats[0] == defaultValueProvider.FLOAT
        instance.doubles[0] == defaultValueProvider.DOUBLE
        instance.strings[0] == defaultValueProvider.STRING
        instance.anEnums[0] == AnEnum.FOO
    }

    def 'newInstance(composite of primitives) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(CompositeOfPrimitives)

        then:
        instance instanceof CompositeOfPrimitives

        instance.primitives.aBoolean == defaultValueProvider.BOOLEAN
        instance.primitives.aByte == defaultValueProvider.BYTE
        instance.primitives.aChar == defaultValueProvider.CHAR
        instance.primitives.aShort == defaultValueProvider.SHORT
        instance.primitives.anInt == defaultValueProvider.INT
        instance.primitives.aLong == defaultValueProvider.LONG
        instance.primitives.aFloat == defaultValueProvider.FLOAT
        instance.primitives.aDouble == defaultValueProvider.DOUBLE
        instance.primitives.string == defaultValueProvider.STRING
        instance.primitives.anEnum == AnEnum.FOO

        instance.finalPrimitives.aBoolean == defaultValueProvider.BOOLEAN
        instance.finalPrimitives.aByte == defaultValueProvider.BYTE
        instance.finalPrimitives.aChar == defaultValueProvider.CHAR
        instance.finalPrimitives.aShort == defaultValueProvider.SHORT
        instance.finalPrimitives.anInt == defaultValueProvider.INT
        instance.finalPrimitives.aLong == defaultValueProvider.LONG
        instance.finalPrimitives.aFloat == defaultValueProvider.FLOAT
        instance.finalPrimitives.aDouble == defaultValueProvider.DOUBLE
        instance.finalPrimitives.string == defaultValueProvider.STRING
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
        instance.primitiveArrays.booleans[0] == defaultValueProvider.BOOLEAN
        instance.primitiveArrays.bytes[0] == defaultValueProvider.BYTE
        instance.primitiveArrays.chars[0] == defaultValueProvider.CHAR
        instance.primitiveArrays.shorts[0] == defaultValueProvider.SHORT
        instance.primitiveArrays.ints[0] == defaultValueProvider.INT
        instance.primitiveArrays.longs[0] == defaultValueProvider.LONG
        instance.primitiveArrays.floats[0] == defaultValueProvider.FLOAT
        instance.primitiveArrays.doubles[0] == defaultValueProvider.DOUBLE
        instance.primitiveArrays.strings[0] == defaultValueProvider.STRING
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
        instance.finalPrimitiveArrays.booleans[0] == defaultValueProvider.BOOLEAN
        instance.finalPrimitiveArrays.bytes[0] == defaultValueProvider.BYTE
        instance.finalPrimitiveArrays.chars[0] == defaultValueProvider.CHAR
        instance.finalPrimitiveArrays.shorts[0] == defaultValueProvider.SHORT
        instance.finalPrimitiveArrays.ints[0] == defaultValueProvider.INT
        instance.finalPrimitiveArrays.longs[0] == defaultValueProvider.LONG
        instance.finalPrimitiveArrays.floats[0] == defaultValueProvider.FLOAT
        instance.finalPrimitiveArrays.doubles[0] == defaultValueProvider.DOUBLE
        instance.finalPrimitiveArrays.strings[0] == defaultValueProvider.STRING
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

        instance.primitives.aBoolean == defaultValueProvider.BOOLEAN
        instance.primitives.aByte == defaultValueProvider.BYTE
        instance.primitives.aChar == defaultValueProvider.CHAR
        instance.primitives.aShort == defaultValueProvider.SHORT
        instance.primitives.anInt == defaultValueProvider.INT
        instance.primitives.aLong == defaultValueProvider.LONG
        instance.primitives.aFloat == defaultValueProvider.FLOAT
        instance.primitives.aDouble == defaultValueProvider.DOUBLE
        instance.primitives.string == defaultValueProvider.STRING
        instance.primitives.anEnum == AnEnum.FOO

        instance.finalPrimitives.aBoolean == defaultValueProvider.BOOLEAN
        instance.finalPrimitives.aByte == defaultValueProvider.BYTE
        instance.finalPrimitives.aChar == defaultValueProvider.CHAR
        instance.finalPrimitives.aShort == defaultValueProvider.SHORT
        instance.finalPrimitives.anInt == defaultValueProvider.INT
        instance.finalPrimitives.aLong == defaultValueProvider.LONG
        instance.finalPrimitives.aFloat == defaultValueProvider.FLOAT
        instance.finalPrimitives.aDouble == defaultValueProvider.DOUBLE
        instance.finalPrimitives.string == defaultValueProvider.STRING
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
        instance.primitiveArrays.booleans[0] == defaultValueProvider.BOOLEAN
        instance.primitiveArrays.bytes[0] == defaultValueProvider.BYTE
        instance.primitiveArrays.chars[0] == defaultValueProvider.CHAR
        instance.primitiveArrays.shorts[0] == defaultValueProvider.SHORT
        instance.primitiveArrays.ints[0] == defaultValueProvider.INT
        instance.primitiveArrays.longs[0] == defaultValueProvider.LONG
        instance.primitiveArrays.floats[0] == defaultValueProvider.FLOAT
        instance.primitiveArrays.doubles[0] == defaultValueProvider.DOUBLE
        instance.primitiveArrays.strings[0] == defaultValueProvider.STRING
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
        instance.finalPrimitiveArrays.booleans[0] == defaultValueProvider.BOOLEAN
        instance.finalPrimitiveArrays.bytes[0] == defaultValueProvider.BYTE
        instance.finalPrimitiveArrays.chars[0] == defaultValueProvider.CHAR
        instance.finalPrimitiveArrays.shorts[0] == defaultValueProvider.SHORT
        instance.finalPrimitiveArrays.ints[0] == defaultValueProvider.INT
        instance.finalPrimitiveArrays.longs[0] == defaultValueProvider.LONG
        instance.finalPrimitiveArrays.floats[0] == defaultValueProvider.FLOAT
        instance.finalPrimitiveArrays.doubles[0] == defaultValueProvider.DOUBLE
        instance.finalPrimitiveArrays.strings[0] == defaultValueProvider.STRING
        instance.finalPrimitiveArrays.anEnums[0] == AnEnum.FOO

        instance.anInterface == null
        instance.anAbstractClass == null
        instance.anInterfaces.length == 0
        instance.anAbstractClasses.length == 0
    }

}
