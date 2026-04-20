package org.pancakelab.service;

import org.pancakelab.model.Order;
import org.pancakelab.model.pancakes.PancakeRecipe;
import org.pancakelab.model.pancakes.PancakeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PancakeServiceImpl implements PancakeService {
    private static final List<Order> orders = Collections.synchronizedList(new ArrayList<>());
    private static final Set<UUID> completedOrders = Collections.synchronizedSet(new HashSet<>());
    private static final Set<UUID> preparedOrders = Collections.synchronizedSet(new HashSet<>());
    private static final List<PancakeRecipe> pancakes = Collections.synchronizedList(new ArrayList<>());
    private PancakeFactory pancakeFactory = new PancakeFactory();

    private static Order getOrderById(UUID orderId) {
        return orders.stream().filter(o -> o.id().equals(orderId)).findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Order with id [%s] not found".formatted(orderId)));
    }

    private static void removeOrderById(UUID orderId) {
        orders.removeIf(o -> o.id().equals(orderId));
    }

    @Override
    public Order createOrder(int building, int room) {
        Order order = new Order(building, room);
        orders.add(order);
        return order;
    }

    @Override
    public void addPancakes(PancakeRecipe pancake, UUID orderId, int count) {

        Order order = getOrderById(orderId);

        for (int i = 0; i < count; ++i) {

            pancake.setOrderId(order.id());
            pancakes.add(pancake);
            OrderLog.logAddPancake(order, pancake.description(), pancakes);
        }


    }

    @Override
    public List<String> viewOrder(UUID orderId) {
        return pancakes.stream()
                .filter(pancake -> pancake.getOrderId().equals(orderId))
                .map(PancakeRecipe::description).toList();
    }

    @Override
    public void removePancakes(String description, UUID orderId, int count) {
        final AtomicInteger removedCount = new AtomicInteger(0);
        pancakes.removeIf(pancake -> {
            return pancake.getOrderId().equals(orderId) &&
                    pancake.description().equals(description) &&
                    removedCount.getAndIncrement() < count;
        });

        Order order = getOrderById(orderId);
        OrderLog.logRemovePancakes(order, description, removedCount.get(), pancakes);
    }

    @Override
    public void cancelOrder(UUID orderId) {
        Order order = getOrderById(orderId);

        pancakes.removeIf(pancake -> pancake.getOrderId().equals(orderId));
        removeOrderById(orderId);
        preparedOrders.removeIf(u -> u.equals(orderId));
        completedOrders.removeIf(u -> u.equals(orderId));

        OrderLog.logCancelOrder(order, pancakes);
    }

    @Override
    public void completeOrder(UUID orderId) {
        completedOrders.add(orderId);
    }

    @Override
    public Set<UUID> listCompletedOrders() {
        return Set.copyOf(completedOrders);
    }

    @Override
    public void prepareOrder(UUID orderId) {
        preparedOrders.add(orderId);
        completedOrders.removeIf(u -> u.equals(orderId));
    }

    @Override
    public Set<UUID> listPreparedOrders() {
        return Set.copyOf(preparedOrders);
    }

    @Override
    public Object[] deliverOrder(UUID orderId) {
        if (!preparedOrders.contains(orderId)) return null;

        Order order = getOrderById(orderId);
        List<String> pancakesToDeliver = viewOrder(orderId);
        OrderLog.logDeliverOrder(order, pancakes);

        pancakes.removeIf(pancake -> pancake.getOrderId().equals(orderId));
        removeOrderById(orderId);
        preparedOrders.removeIf(u -> u.equals(orderId));

        return new Object[]{order, pancakesToDeliver};
    }

    @Override
    public void addIngredient(PancakeRecipe pancake, String ingredient) {

        List<String> ingredients = new ArrayList<>(pancake.ingredients());
        ingredients.add(ingredient);
        pancake.setIngredients(Collections.unmodifiableList(ingredients));

    }

}
