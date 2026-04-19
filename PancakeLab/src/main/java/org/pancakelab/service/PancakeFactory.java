package org.pancakelab.service;

import org.pancakelab.model.pancakes.DarkChocolatePancake;
import org.pancakelab.model.pancakes.DarkChocolateWhippedCreamHazelnutsPancake;
import org.pancakelab.model.pancakes.DarkChocolateWhippedCreamPancake;
import org.pancakelab.model.pancakes.MilkChocolateHazelnutsPancake;
import org.pancakelab.model.pancakes.MilkChocolatePancake;
import org.pancakelab.model.pancakes.PancakeRecipe;
import org.pancakelab.model.pancakes.PancakeType;

import java.util.List;

public class PancakeFactory {
    public PancakeRecipe createPancake(PancakeType type, List<String> ingredients) {

        return switch (type) {
            case DARK_CHOCOLATE -> new DarkChocolatePancake(ingredients);
            case DARK_CHOCOLATE_WHIPPED_CREAM_HAZELNUTS -> new DarkChocolateWhippedCreamHazelnutsPancake(ingredients);
            case DARK_CHOCOLATE_WHIPPED_CREAM -> new DarkChocolateWhippedCreamPancake(ingredients);
            case MILK_CHOCOLATE -> new MilkChocolatePancake(ingredients);
            case MILK_CHOCOLATE_HAZELNUTS -> new MilkChocolateHazelnutsPancake(ingredients);
        };
    }


}
