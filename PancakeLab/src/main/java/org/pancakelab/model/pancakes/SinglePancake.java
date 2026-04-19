package org.pancakelab.model.pancakes;

import java.util.List;
import java.util.UUID;

public class SinglePancake implements PancakeRecipe {
    private final List<String> ingredients;
    private UUID orderId;

    public SinglePancake(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public UUID getOrderId() {
        return orderId;
    }

    @Override
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    @Override
    public List<String> ingredients() {
        return List.copyOf(ingredients);
    }
}
