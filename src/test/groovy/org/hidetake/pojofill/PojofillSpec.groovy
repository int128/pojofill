package org.hidetake.pojofill

import org.hidetake.pojofill.fixture.*
import spock.lang.Specification

class PojofillSpec extends Specification {

    static final defaultValueProvider = new ValueProvider()

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
        instance.aBoolean == defaultValueProvider.boolean
        instance.aByte == defaultValueProvider.byte
        instance.aChar == defaultValueProvider.character
        instance.aShort == defaultValueProvider.short
        instance.anInt == defaultValueProvider.integer
        instance.aLong == defaultValueProvider.long
        instance.aFloat == defaultValueProvider.float
        instance.aDouble == defaultValueProvider.double
        instance.string == defaultValueProvider.charSequence
        instance.anEnum == AnEnum.FOO
    }

    def 'newInstance(class with primitive constructors) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(FinalPrimitives)

        then:
        instance instanceof FinalPrimitives
        instance.aBoolean == defaultValueProvider.boolean
        instance.aByte == defaultValueProvider.byte
        instance.aChar == defaultValueProvider.character
        instance.aShort == defaultValueProvider.short
        instance.anInt == defaultValueProvider.integer
        instance.aLong == defaultValueProvider.long
        instance.aFloat == defaultValueProvider.float
        instance.aDouble == defaultValueProvider.double
        instance.string == defaultValueProvider.charSequence
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

        instance.booleans[0] == defaultValueProvider.boolean
        instance.bytes[0] == defaultValueProvider.byte
        instance.chars[0] == defaultValueProvider.character
        instance.shorts[0] == defaultValueProvider.short
        instance.ints[0] == defaultValueProvider.integer
        instance.longs[0] == defaultValueProvider.long
        instance.floats[0] == defaultValueProvider.float
        instance.doubles[0] == defaultValueProvider.double
        instance.strings[0] == defaultValueProvider.charSequence
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

        instance.booleans[0] == defaultValueProvider.boolean
        instance.bytes[0] == defaultValueProvider.byte
        instance.chars[0] == defaultValueProvider.character
        instance.shorts[0] == defaultValueProvider.short
        instance.ints[0] == defaultValueProvider.integer
        instance.longs[0] == defaultValueProvider.long
        instance.floats[0] == defaultValueProvider.float
        instance.doubles[0] == defaultValueProvider.double
        instance.strings[0] == defaultValueProvider.charSequence
        instance.anEnums[0] == AnEnum.FOO
    }

    def 'newInstance(composite of primitives) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(CompositeOfPrimitives)

        then:
        instance instanceof CompositeOfPrimitives

        instance.primitives.aBoolean == defaultValueProvider.boolean
        instance.primitives.aByte == defaultValueProvider.byte
        instance.primitives.aChar == defaultValueProvider.character
        instance.primitives.aShort == defaultValueProvider.short
        instance.primitives.anInt == defaultValueProvider.integer
        instance.primitives.aLong == defaultValueProvider.long
        instance.primitives.aFloat == defaultValueProvider.float
        instance.primitives.aDouble == defaultValueProvider.double
        instance.primitives.string == defaultValueProvider.charSequence
        instance.primitives.anEnum == AnEnum.FOO

        instance.finalPrimitives.aBoolean == defaultValueProvider.boolean
        instance.finalPrimitives.aByte == defaultValueProvider.byte
        instance.finalPrimitives.aChar == defaultValueProvider.character
        instance.finalPrimitives.aShort == defaultValueProvider.short
        instance.finalPrimitives.anInt == defaultValueProvider.integer
        instance.finalPrimitives.aLong == defaultValueProvider.long
        instance.finalPrimitives.aFloat == defaultValueProvider.float
        instance.finalPrimitives.aDouble == defaultValueProvider.double
        instance.finalPrimitives.string == defaultValueProvider.charSequence
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
        instance.primitiveArrays.booleans[0] == defaultValueProvider.boolean
        instance.primitiveArrays.bytes[0] == defaultValueProvider.byte
        instance.primitiveArrays.chars[0] == defaultValueProvider.character
        instance.primitiveArrays.shorts[0] == defaultValueProvider.short
        instance.primitiveArrays.ints[0] == defaultValueProvider.integer
        instance.primitiveArrays.longs[0] == defaultValueProvider.long
        instance.primitiveArrays.floats[0] == defaultValueProvider.float
        instance.primitiveArrays.doubles[0] == defaultValueProvider.double
        instance.primitiveArrays.strings[0] == defaultValueProvider.charSequence
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
        instance.finalPrimitiveArrays.booleans[0] == defaultValueProvider.boolean
        instance.finalPrimitiveArrays.bytes[0] == defaultValueProvider.byte
        instance.finalPrimitiveArrays.chars[0] == defaultValueProvider.character
        instance.finalPrimitiveArrays.shorts[0] == defaultValueProvider.short
        instance.finalPrimitiveArrays.ints[0] == defaultValueProvider.integer
        instance.finalPrimitiveArrays.longs[0] == defaultValueProvider.long
        instance.finalPrimitiveArrays.floats[0] == defaultValueProvider.float
        instance.finalPrimitiveArrays.doubles[0] == defaultValueProvider.double
        instance.finalPrimitiveArrays.strings[0] == defaultValueProvider.charSequence
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

        instance.primitives.aBoolean == defaultValueProvider.boolean
        instance.primitives.aByte == defaultValueProvider.byte
        instance.primitives.aChar == defaultValueProvider.character
        instance.primitives.aShort == defaultValueProvider.short
        instance.primitives.anInt == defaultValueProvider.integer
        instance.primitives.aLong == defaultValueProvider.long
        instance.primitives.aFloat == defaultValueProvider.float
        instance.primitives.aDouble == defaultValueProvider.double
        instance.primitives.string == defaultValueProvider.charSequence
        instance.primitives.anEnum == AnEnum.FOO

        instance.finalPrimitives.aBoolean == defaultValueProvider.boolean
        instance.finalPrimitives.aByte == defaultValueProvider.byte
        instance.finalPrimitives.aChar == defaultValueProvider.character
        instance.finalPrimitives.aShort == defaultValueProvider.short
        instance.finalPrimitives.anInt == defaultValueProvider.integer
        instance.finalPrimitives.aLong == defaultValueProvider.long
        instance.finalPrimitives.aFloat == defaultValueProvider.float
        instance.finalPrimitives.aDouble == defaultValueProvider.double
        instance.finalPrimitives.string == defaultValueProvider.charSequence
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
        instance.primitiveArrays.booleans[0] == defaultValueProvider.boolean
        instance.primitiveArrays.bytes[0] == defaultValueProvider.byte
        instance.primitiveArrays.chars[0] == defaultValueProvider.character
        instance.primitiveArrays.shorts[0] == defaultValueProvider.short
        instance.primitiveArrays.ints[0] == defaultValueProvider.integer
        instance.primitiveArrays.longs[0] == defaultValueProvider.long
        instance.primitiveArrays.floats[0] == defaultValueProvider.float
        instance.primitiveArrays.doubles[0] == defaultValueProvider.double
        instance.primitiveArrays.strings[0] == defaultValueProvider.charSequence
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
        instance.finalPrimitiveArrays.booleans[0] == defaultValueProvider.boolean
        instance.finalPrimitiveArrays.bytes[0] == defaultValueProvider.byte
        instance.finalPrimitiveArrays.chars[0] == defaultValueProvider.character
        instance.finalPrimitiveArrays.shorts[0] == defaultValueProvider.short
        instance.finalPrimitiveArrays.ints[0] == defaultValueProvider.integer
        instance.finalPrimitiveArrays.longs[0] == defaultValueProvider.long
        instance.finalPrimitiveArrays.floats[0] == defaultValueProvider.float
        instance.finalPrimitiveArrays.doubles[0] == defaultValueProvider.double
        instance.finalPrimitiveArrays.strings[0] == defaultValueProvider.charSequence
        instance.finalPrimitiveArrays.anEnums[0] == AnEnum.FOO

        instance.anInterface == null
        instance.anAbstractClass == null
        instance.anInterfaces.length == 0
        instance.anAbstractClasses.length == 0
    }

}
