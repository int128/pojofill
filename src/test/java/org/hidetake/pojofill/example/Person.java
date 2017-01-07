package org.hidetake.pojofill.example;

import lombok.Data;

import java.util.List;

@Data
public class Person {
    private final int id;
    private final String name;
    private List<Pen> pens;
}
