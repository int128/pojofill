package org.hidetake.pojofill.example;

import lombok.Data;

@Data
public class Pen {
    private final Color color;

    public enum Color {
        RED, YELLOW, GREEN, BLUE
    }
}
