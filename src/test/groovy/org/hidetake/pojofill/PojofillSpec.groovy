package org.hidetake.pojofill

import spock.lang.Specification

class PojofillSpec extends Specification {

    static final defaultValueProvider = new ValueProvider()

    Pojofill pojofill

    def setup() {
        pojofill = new Pojofill()
    }

    def 'newInstance(null) should return null'() {
        expect:
        pojofill.newInstance(null) == null
    }

    def 'newInstance(class with primitive setters) should return an object'() {
        when:
        def instance = pojofill.newInstance(Primitives)

        then:
        instance instanceof Primitives
        instance.with {
            aBoolean == defaultValueProvider.boolean
            aByte == defaultValueProvider.byte
            aChar == defaultValueProvider.character
            aShort == defaultValueProvider.short
            anInt == defaultValueProvider.integer
            aLong == defaultValueProvider.long
            aFloat == defaultValueProvider.float
            aDouble == defaultValueProvider.double
            string == defaultValueProvider.charSequence
            anEnum == AnEnum.FOO
        }
    }

    def 'newInstance(class with primitive constructors) should return an object'() {
        when:
        def instance = pojofill.newInstance(FinalPrimitives)

        then:
        instance instanceof FinalPrimitives
        instance.with {
            aBoolean == defaultValueProvider.boolean
            aByte == defaultValueProvider.byte
            aChar == defaultValueProvider.character
            aShort == defaultValueProvider.short
            anInt == defaultValueProvider.integer
            aLong == defaultValueProvider.long
            aFloat == defaultValueProvider.float
            aDouble == defaultValueProvider.double
            string == defaultValueProvider.charSequence
            anEnum == AnEnum.FOO
        }
    }

    def 'newInstance(class with primitive array setters) should return an object'() {
        when:
        def instance = pojofill.newInstance(PrimitiveArrays)

        then:
        instance instanceof PrimitiveArrays
        instance.with {
            booleans.length == 1
            bytes.length == 1
            chars.length == 1
            shorts.length == 1
            ints.length == 1
            longs.length == 1
            floats.length == 1
            doubles.length == 1
            strings.length == 1
            anEnums.length == 1
        }
        instance.with {
            booleans[0] == defaultValueProvider.boolean
            bytes[0] == defaultValueProvider.byte
            chars[0] == defaultValueProvider.character
            shorts[0] == defaultValueProvider.short
            ints[0] == defaultValueProvider.integer
            longs[0] == defaultValueProvider.long
            floats[0] == defaultValueProvider.float
            doubles[0] == defaultValueProvider.double
            strings[0] == defaultValueProvider.charSequence
            anEnums[0] == AnEnum.FOO
        }
    }

    def 'newInstance(class with primitive array constructors) should return an object'() {
        when:
        def instance = pojofill.newInstance(FinalPrimitiveArrays)

        then:
        instance instanceof FinalPrimitiveArrays
        instance.with {
            booleans.length == 1
            bytes.length == 1
            chars.length == 1
            shorts.length == 1
            ints.length == 1
            longs.length == 1
            floats.length == 1
            doubles.length == 1
            strings.length == 1
            anEnums.length == 1
        }
        instance.with {
            booleans[0] == defaultValueProvider.boolean
            bytes[0] == defaultValueProvider.byte
            chars[0] == defaultValueProvider.character
            shorts[0] == defaultValueProvider.short
            ints[0] == defaultValueProvider.integer
            longs[0] == defaultValueProvider.long
            floats[0] == defaultValueProvider.float
            doubles[0] == defaultValueProvider.double
            strings[0] == defaultValueProvider.charSequence
            anEnums[0] == AnEnum.FOO
        }
    }

    def 'newInstance(composite of primitives) should return an object'() {
        when:
        def instance = pojofill.newInstance(CompositeOfPrimitives)

        then:
        instance instanceof CompositeOfPrimitives

        instance.primitives.with {
            aBoolean == defaultValueProvider.boolean
            aByte == defaultValueProvider.byte
            aChar == defaultValueProvider.character
            aShort == defaultValueProvider.short
            anInt == defaultValueProvider.integer
            aLong == defaultValueProvider.long
            aFloat == defaultValueProvider.float
            aDouble == defaultValueProvider.double
            string == defaultValueProvider.charSequence
            anEnum == AnEnum.FOO
        }

        instance.finalPrimitives.with {
            aBoolean == defaultValueProvider.boolean
            aByte == defaultValueProvider.byte
            aChar == defaultValueProvider.character
            aShort == defaultValueProvider.short
            anInt == defaultValueProvider.integer
            aLong == defaultValueProvider.long
            aFloat == defaultValueProvider.float
            aDouble == defaultValueProvider.double
            string == defaultValueProvider.charSequence
            anEnum == AnEnum.FOO
        }

        instance.primitiveArrays.with {
            booleans.length == 1
            bytes.length == 1
            chars.length == 1
            shorts.length == 1
            ints.length == 1
            longs.length == 1
            floats.length == 1
            doubles.length == 1
            strings.length == 1
            anEnums.length == 1
        }
        instance.primitiveArrays.with {
            booleans[0] == defaultValueProvider.boolean
            bytes[0] == defaultValueProvider.byte
            chars[0] == defaultValueProvider.character
            shorts[0] == defaultValueProvider.short
            ints[0] == defaultValueProvider.integer
            longs[0] == defaultValueProvider.long
            floats[0] == defaultValueProvider.float
            doubles[0] == defaultValueProvider.double
            strings[0] == defaultValueProvider.charSequence
            anEnums[0] == AnEnum.FOO
        }

        instance.finalPrimitiveArrays.with {
            booleans.length == 1
            bytes.length == 1
            chars.length == 1
            shorts.length == 1
            ints.length == 1
            longs.length == 1
            floats.length == 1
            doubles.length == 1
            strings.length == 1
            anEnums.length == 1
        }
        instance.finalPrimitiveArrays.with {
            booleans[0] == defaultValueProvider.boolean
            bytes[0] == defaultValueProvider.byte
            chars[0] == defaultValueProvider.character
            shorts[0] == defaultValueProvider.short
            ints[0] == defaultValueProvider.integer
            longs[0] == defaultValueProvider.long
            floats[0] == defaultValueProvider.float
            doubles[0] == defaultValueProvider.double
            strings[0] == defaultValueProvider.charSequence
            anEnums[0] == AnEnum.FOO
        }
    }

    def 'newInstance(final composite class) should return an object'() {
        when:
        def instance = pojofill.newInstance(FinalCompositeOfPrimitives)

        then:
        instance instanceof FinalCompositeOfPrimitives

        instance.primitives.with {
            aBoolean == defaultValueProvider.boolean
            aByte == defaultValueProvider.byte
            aChar == defaultValueProvider.character
            aShort == defaultValueProvider.short
            anInt == defaultValueProvider.integer
            aLong == defaultValueProvider.long
            aFloat == defaultValueProvider.float
            aDouble == defaultValueProvider.double
            string == defaultValueProvider.charSequence
            anEnum == AnEnum.FOO
        }

        instance.finalPrimitives.with {
            aBoolean == defaultValueProvider.boolean
            aByte == defaultValueProvider.byte
            aChar == defaultValueProvider.character
            aShort == defaultValueProvider.short
            anInt == defaultValueProvider.integer
            aLong == defaultValueProvider.long
            aFloat == defaultValueProvider.float
            aDouble == defaultValueProvider.double
            string == defaultValueProvider.charSequence
            anEnum == AnEnum.FOO
        }

        instance.primitiveArrays.with {
            booleans.length == 1
            bytes.length == 1
            chars.length == 1
            shorts.length == 1
            ints.length == 1
            longs.length == 1
            floats.length == 1
            doubles.length == 1
            strings.length == 1
            anEnums.length == 1
        }
        instance.primitiveArrays.with {
            booleans[0] == defaultValueProvider.boolean
            bytes[0] == defaultValueProvider.byte
            chars[0] == defaultValueProvider.character
            shorts[0] == defaultValueProvider.short
            ints[0] == defaultValueProvider.integer
            longs[0] == defaultValueProvider.long
            floats[0] == defaultValueProvider.float
            doubles[0] == defaultValueProvider.double
            strings[0] == defaultValueProvider.charSequence
            anEnums[0] == AnEnum.FOO
        }

        instance.finalPrimitiveArrays.with {
            booleans.length == 1
            bytes.length == 1
            chars.length == 1
            shorts.length == 1
            ints.length == 1
            longs.length == 1
            floats.length == 1
            doubles.length == 1
            strings.length == 1
            anEnums.length == 1
        }
        instance.finalPrimitiveArrays.with {
            booleans[0] == defaultValueProvider.boolean
            bytes[0] == defaultValueProvider.byte
            chars[0] == defaultValueProvider.character
            shorts[0] == defaultValueProvider.short
            ints[0] == defaultValueProvider.integer
            longs[0] == defaultValueProvider.long
            floats[0] == defaultValueProvider.float
            doubles[0] == defaultValueProvider.double
            strings[0] == defaultValueProvider.charSequence
            anEnums[0] == AnEnum.FOO
        }
    }

}
