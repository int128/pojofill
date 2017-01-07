package org.hidetake.pojofill

import org.hidetake.pojofill.context.InstantiationContext
import org.hidetake.pojofill.context.SetterArgument
import org.hidetake.pojofill.fixture.AnEnum
import org.hidetake.pojofill.fixture.TypeAggregateWithConstructor
import org.hidetake.pojofill.fixture.TypeAggregateWithSetters
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

    def 'newInstance(class with setters) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(TypeAggregateWithSetters)

        then:
        instance instanceof TypeAggregateWithSetters
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
        instance.anInterface == null
        instance.anAbstractClass == null

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
        instance.anInterfaces.length == 0
        instance.anAbstractClasses.length == 0

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

    def 'newInstance(class with constructor) should return an object'() {
        when:
        def instance = pojofill.newInstanceOrNull(TypeAggregateWithConstructor)

        then:
        instance instanceof TypeAggregateWithConstructor
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
        instance.anInterface == null
        instance.anAbstractClass == null

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
        instance.anInterfaces.length == 0
        instance.anAbstractClasses.length == 0

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
        def instance = pojofill.newInstanceOrNull(TypeAggregateWithSetters)

        then:
        instance instanceof TypeAggregateWithSetters
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
