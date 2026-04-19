package org.pancakelab.model.pancakes;

import java.util.List;
import java.util.UUID;

public class MilkChocolateHazelnutsPancake extends MilkChocolatePancake {
    private UUID orderId;

    public MilkChocolateHazelnutsPancake(List<String> ingredients) {
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
