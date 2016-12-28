package org.hidetake.pojofill.example;

import org.hidetake.pojofill.Pojofill;

public class Main {
    public static void main(String[] args) {
        Pojofill pojofill = new Pojofill();
        pojofill.newInstance(Person.class).ifPresent(System.out::println);
    }
}
