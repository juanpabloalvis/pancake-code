package org.pancakelab.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.pancakelab.model.Order;
import org.pancakelab.model.pancakes.PancakeRecipe;
import org.pancakelab.model.pancakes.PancakeType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PancakeServiceImplTest {
    private final static String DARK_CHOCOLATE_PANCAKE_DESCRIPTION = "Delicious pancake with dark chocolate!";
    private final static String MILK_CHOCOLATE_PANCAKE_DESCRIPTION = "Delicious pancake with milk chocolate!";
    private final static String MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION = "Delicious pancake with milk chocolate, hazelnuts!";
    private final PancakeService pancakeServiceImpl = new PancakeServiceImpl();
    private final PancakeFactory pancakeFactory = new PancakeFactory();


    private Order order = null;

    @Test
    @org.junit.jupiter.api.Order(10)
    public void GivenOrderDoesNotExist_WhenCreatingOrder_ThenOrderCreatedWithCorrectData_Test() {
        // setup

        // exercise
        order = pancakeServiceImpl.createOrder(10, 20);

        assertEquals(10, order.building());
        assertEquals(20, order.room());

        // verify

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    public void GivenOrderExists_WhenAddingPancakes_ThenCorrectNumberOfPancakesAdded_Test() {
        // setup

        // exercise
        addPancakes();

        // verify
        List<String> ordersPancakes = pancakeServiceImpl.viewOrder(order.id());

        assertEquals(List.of(DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION), ordersPancakes);

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    public void GivenPancakesExists_WhenRemovingPancakes_ThenCorrectNumberOfPancakesRemoved_Test() {
        // setup

        // exercise
        pancakeServiceImpl.removePancakes(DARK_CHOCOLATE_PANCAKE_DESCRIPTION, order.id(), 2);
        pancakeServiceImpl.removePancakes(MILK_CHOCOLATE_PANCAKE_DESCRIPTION, order.id(), 3);
        pancakeServiceImpl.removePancakes(MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION, order.id(), 1);

        // verify
        List<String> ordersPancakes = pancakeServiceImpl.viewOrder(order.id());

        assertEquals(List.of(DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION,
                MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION), ordersPancakes);

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(40)
    public void GivenOrderExists_WhenCompletingOrder_ThenOrderCompleted_Test() {
        // setup

        // exercise
        pancakeServiceImpl.completeOrder(order.id());

        // verify
        Set<UUID> completedOrdersOrders = pancakeServiceImpl.listCompletedOrders();
        assertTrue(completedOrdersOrders.contains(order.id()));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(50)
    public void GivenOrderExists_WhenPreparingOrder_ThenOrderPrepared_Test() {
        // setup

        // exercise
        pancakeServiceImpl.prepareOrder(order.id());

        // verify
        Set<UUID> completedOrders = pancakeServiceImpl.listCompletedOrders();
        assertFalse(completedOrders.contains(order.id()));

        Set<UUID> preparedOrders = pancakeServiceImpl.listPreparedOrders();
        assertTrue(preparedOrders.contains(order.id()));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(60)
    public void GivenOrderExists_WhenDeliveringOrder_ThenCorrectOrderReturnedAndOrderRemovedFromTheDatabase_Test() {
        // setup
        List<String> pancakesToDeliver = pancakeServiceImpl.viewOrder(order.id());

        // exercise
        Object[] deliveredOrder = pancakeServiceImpl.deliverOrder(order.id());

        // verify
        Set<UUID> completedOrders = pancakeServiceImpl.listCompletedOrders();
        assertFalse(completedOrders.contains(order.id()));

        Set<UUID> preparedOrders = pancakeServiceImpl.listPreparedOrders();
        assertFalse(preparedOrders.contains(order.id()));

        List<String> ordersPancakes = pancakeServiceImpl.viewOrder(order.id());

        assertEquals(List.of(), ordersPancakes);
        assertEquals(order.id(), ((Order) deliveredOrder[0]).id());
        assertEquals(pancakesToDeliver, deliveredOrder[1]);

        // tear down
        order = null;
    }

    @Test
    @org.junit.jupiter.api.Order(70)
    public void GivenOrderExists_WhenCancellingOrder_ThenOrderAndPancakesRemoved_Test() {
        // setup
        order = pancakeServiceImpl.createOrder(10, 20);
        addPancakes();

        // exercise
        pancakeServiceImpl.cancelOrder(order.id());

        // verify
        Set<UUID> completedOrders = pancakeServiceImpl.listCompletedOrders();
        assertFalse(completedOrders.contains(order.id()));

        Set<UUID> preparedOrders = pancakeServiceImpl.listPreparedOrders();
        assertFalse(preparedOrders.contains(order.id()));

        List<String> ordersPancakes = pancakeServiceImpl.viewOrder(order.id());

        assertEquals(List.of(), ordersPancakes);

        // tear down
    }

    private void addPancakes() {

        PancakeRecipe pancakeDarkChocolate = pancakeFactory.createPancake(PancakeType.DARK_CHOCOLATE);
        pancakeServiceImpl.addIngredient(pancakeDarkChocolate, "dark chocolate");
        pancakeServiceImpl.addPancakes(pancakeDarkChocolate, order.id(), 3);

        PancakeRecipe pancakeMilkChocolate = pancakeFactory.createPancake(PancakeType.MILK_CHOCOLATE);
        pancakeServiceImpl.addIngredient(pancakeMilkChocolate, "milk chocolate");
        pancakeServiceImpl.addPancakes(pancakeMilkChocolate, order.id(), 3);


        PancakeRecipe pancakeMilkChocolateHazelnuts = pancakeFactory.createPancake(PancakeType.MILK_CHOCOLATE_HAZELNUTS);
        pancakeServiceImpl.addIngredient(pancakeMilkChocolateHazelnuts, "milk chocolate");
        pancakeServiceImpl.addIngredient(pancakeMilkChocolateHazelnuts, "hazelnuts");
        pancakeServiceImpl.addPancakes(pancakeMilkChocolateHazelnuts, order.id(), 3);

    }
}
