package example

import groovy.util.logging.Slf4j

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Slf4j
class ExampleGenerator {
    def <T> T newInstanceWithValues(Class<T> clazz) {
        log.debug("Generating an instance of $clazz")
        if (clazz == null) {
            null
        } else if (clazz.enum) {
            null
        } else if (clazz == boolean || Boolean.isAssignableFrom(clazz)) {
            true as T
        } else if (clazz == char || Character.isAssignableFrom(clazz)) {
            ('a' as char) as T
        } else if (clazz in [byte, short, int, long, float, double] || Number.isAssignableFrom(clazz)) {
            12345 as T
        } else if (clazz.array) {
            [] as T
        } else if (CharSequence.isAssignableFrom(clazz)) {
            'abcde' as T
        } else if (Collection.isAssignableFrom(clazz)) {
            newCollectionInstance(clazz.genericSuperclass) as T
        } else {
            def minConstructor = clazz.constructors.min { ctor -> ctor.parameterCount }
            def args = (0..<minConstructor.parameterCount).collect { int index ->
                def parameterClass = minConstructor.parameterTypes[index]
                if (Collection.isAssignableFrom(parameterClass)) {
                    def genericType = minConstructor.genericParameterTypes[index]
                    newCollectionInstance(genericType)
                } else {
                    newInstanceWithValues(parameterClass)
                }
            }.toArray()

            log.debug("Calling $minConstructor with $args")
            def instance = clazz.newInstance(args)
            instance.properties.each { key, _ ->
                def setter = instance.class.methods.find { method ->
                    method.name == "set${key.toString().capitalize()}".toString() && method.parameterCount == 1
                }
                if (setter) {
                    def parameterClass = setter.parameterTypes.head()
                    if (Collection.isAssignableFrom(parameterClass)) {
                        def genericType = setter.genericParameterTypes.head()
                        log.debug("Calling $setter with $genericType")
                        setter.invoke(instance, newCollectionInstance(genericType))
                    } else {
                        log.debug("Calling $setter with $parameterClass")
                        setter.invoke(instance, newInstanceWithValues(parameterClass))
                    }
                }
            }
            instance
        }
    }

    def <E> Collection<E> newCollectionInstance(Type genericType) {
        def collection = []
        if (genericType instanceof ParameterizedType) {
            def elementClass = Class.forName(genericType.actualTypeArguments[0].typeName)
            log.debug("Adding an instance of $elementClass into $genericType")
            def element = newInstanceWithValues(elementClass)
            collection.add(element)
        } else {
            log.debug("Could not infer element type: $genericType")
        }
        collection
    }
}
