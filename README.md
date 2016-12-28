# Pojofill

This is a library to instantiate a POJO (Plain Old Java Object) filled with example values.
Useful for test double.


## Getting Started

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


## How works

`Pojofill#newInstance()` method accepts a class `T` and returns an instance wrapped with `Optional<T>`.

Given class               | Generated instance
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
`Collection<E>`           | a `List` with 1 element (or empty `List` if it could not instantiate `E`)
Array of `E`              | an array with 1 element (or empty array if it could not instantiate `E`)
Class                     | an object filled with value(s)

If one of following is given, the method fails and returns `Optional.empty()`.

- `void`
- Abstract class
- Interface
- Class without any public constructor

If a class is given, the method tries to instantiate an object.

- Instantiation
  1. Find a constructor with the longest parameters.
  1. Instantiate arguments for the constructor.
     If any argument could not be instantiated, try the next longest constructor.
  1. Invoke the constructor with arguments.
  1. If no more constructor left, the method fails.
- Value assignment
  1. Find a setter method of the object.
  1. Instantiate an argument for the setter. If the argument could not instantiated, ignore this setter.
  1. Invoke the setter with the argument.
- Returning the object


## Value provider

TODO


## TODO

- Maven Central Publication
