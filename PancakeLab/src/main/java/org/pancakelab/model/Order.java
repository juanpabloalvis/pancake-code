package org.pancakelab.model;

import java.util.UUID;

// we need to make the object immutable, so we can use record, but we need to generate the id in the constructor, so we need to define a custom constructor
public record Order(UUID id, int building, int room) {
    public Order(int building, int room) {
        this(UUID.randomUUID(), building, room);
    }
}
