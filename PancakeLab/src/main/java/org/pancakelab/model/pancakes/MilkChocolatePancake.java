package org.pancakelab.model.pancakes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MilkChocolatePancake implements PancakeRecipe {
    private List<String> ingredients = Collections.synchronizedList(new ArrayList<>());
    private UUID orderId;

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

    @Override
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
