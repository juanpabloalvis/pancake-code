package org.pancakelab.service;

import org.pancakelab.model.Order;
import org.pancakelab.model.pancakes.PancakeRecipe;
import org.pancakelab.model.pancakes.PancakeType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PancakeService {
    Order createOrder(int building, int room);

    void addPancakes(PancakeRecipe pancake, UUID orderId, int count);

    List<String> viewOrder(UUID orderId);

    void removePancakes(String description, UUID orderId, int count);

    void cancelOrder(UUID orderId);

    void completeOrder(UUID orderId);

    Set<UUID> listCompletedOrders();

    void prepareOrder(UUID id);

    Set<UUID> listPreparedOrders();

    Object[] deliverOrder(UUID orderId);

    void addIngredient(PancakeRecipe pancake, String ingredient);
}
