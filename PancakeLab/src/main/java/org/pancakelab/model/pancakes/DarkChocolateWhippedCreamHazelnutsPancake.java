package org.pancakelab.model.pancakes;

import java.util.List;
import java.util.UUID;

public class DarkChocolateWhippedCreamHazelnutsPancake extends DarkChocolateWhippedCreamPancake {
    private UUID orderId;

    public DarkChocolateWhippedCreamHazelnutsPancake(List<String> ingredients) {
        super(ingredients);
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
        return List.copyOf(super.ingredients());
    }
}
