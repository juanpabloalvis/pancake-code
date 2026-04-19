package org.pancakelab.model.pancakes;

import java.util.List;

public enum PancakeType {
    DARK_CHOCOLATE,
    DARK_CHOCOLATE_WHIPPED_CREAM_HAZELNUTS,
    DARK_CHOCOLATE_WHIPPED_CREAM,
    MILK_CHOCOLATE,
    MILK_CHOCOLATE_HAZELNUTS;

    public List<String> ingredients() {
        return switch (this) {
            case DARK_CHOCOLATE -> List.of("dark chocolate");
            case DARK_CHOCOLATE_WHIPPED_CREAM_HAZELNUTS -> List.of("dark chocolate", "whipped cream", "hazelnuts");
            case DARK_CHOCOLATE_WHIPPED_CREAM -> List.of("dark chocolate", "whipped cream");
            case MILK_CHOCOLATE -> List.of("milk chocolate");
            case MILK_CHOCOLATE_HAZELNUTS -> List.of("milk chocolate", "hazelnuts");
        };
    }

}

