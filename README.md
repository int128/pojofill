# Pojofill [![CircleCI](https://circleci.com/gh/int128/pojofill.svg?style=shield)](https://circleci.com/gh/int128/pojofill)

This is a library to instantiate a POJO (Plain Old Java Object) filled with generated values.
It is useful for building test double.


## Getting Started

Dependencies:

```groovy
// build.gradle
compile 'org.hidetake:pojofill:1.1.0'
```

```xml
<!-- pom.xml -->
<dependency>
  <groupId>org.hidetake</groupId>
  <artifactId>pojofill</artifactId>
  <version>1.1.0</version>
  <type>pom</type>
</dependency>
```

Let's take a look at the example.

```java
import lombok.Data;

@Data
public class Person {
  private final int id;
  private final String name;
}
```

```java
import org.hidetake.pojofill.Pojofill;

public class Main {
  public static void main(String[] args) {
    Pojofill pojofill = new Pojofill();
    pojofill.newInstance(Person.class).ifPresent(System.out::println);
  }
}
```

Output will be following:

```
Person(id=123456, name=abcde)
```


## Specification

`Pojofill#newInstance()` method accepts a type `T` and returns an instance wrapped with `Optional<T>`.

Given type                | Generated instance
--------------------------|-------------------
`boolean`                 | true
`byte`                    | 123
`char`                    | `a`
`short`                   | 12345
`int`                     | 123456
`long`                    | 1234567890
`float`                   | 1.23456
`double`                  | 1.23456
`CharSequence` (`String`) | `abcde`
`enum`                    | the first constant
`Collection<E>`           | a `List` with an `E` instantiated recursively (or empty `List` if it failed)
Array of `E`              | an array with an `E` instantiated recursively (or empty array if it failed)
Class                     | an object

If a class is given, the method tries to instantiate an object of the class.
Members of the class are recursively initialized by the constructor and setters.

The method returns `Optional.empty()` if one of following is given:

- `void`
- Class without any public constructor
- Abstract class or interface


## Custom value provider

`Pojofill` class accepts a custom value provider on the constructor.

```java
class MyValueProvider extends DefaultValueProvider {
  public CharSequence getCharSequence(InstantiationContext context) {
    return "my custom string";
  }
}

class Example {
  public void main() {
    Pojofill pojofill = new Pojofill();
    pojofill.getRootInstantiator().addInstantiator(new PrimitiveInstantiator(new MyValueProvider()));
  }
}
```

An actual type of `InstantiationContext` should be determined by the context as follows:

Context                                   | Actual type
------------------------------------------|------------
Given in the method of `Pojofill` class   | `TopLevel`
Instantiating a constructor argument      | `ConstructorArgument`
Instantiating a setter argument           | `SetterArgument`
Instantiating an array element            | `ArrayElement`
Instantiating an collection element       | `CollectionElement`


## Logging

Pojofill depends on SLF4J.
It describes error messages in the `DEBUG` level and trace messages in the `TRACE` level.
Tested with Logback.


## Contributions

This is an open source software licensed under the Apache License Version 2.0.
Feel free to open issues or pull requests.

```
Copyright 2016-2017 Hidetake Iwata

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
