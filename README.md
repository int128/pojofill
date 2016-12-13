# POJO

This is a library to instantiate a POJO (Plain Old Java Object) filled with example values.

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
import xxx;

public class Main {
    public static void main(String[] args) {
        Foo foo = ExampleGenerator.newInstance(Foo.class);
        System.out.println(foo);
    }
}
```
