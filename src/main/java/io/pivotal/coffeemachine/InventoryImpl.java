/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.pivotal.coffeemachine;

import io.pivotal.coffeemachine.exception.InsufficientStockException;
import io.pivotal.coffeemachine.exception.NoSuchIngredientException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author shahs
 */
public class InventoryImpl implements Inventory {

    private static Map<String, Integer> INGREDIENTS_MAP;

    static {
        INGREDIENTS_MAP = new HashMap();
        INGREDIENTS_MAP.put("coffee", 10);
        INGREDIENTS_MAP.put("sugar", 10);
        INGREDIENTS_MAP.put("cream", 10);
    }

    public InventoryImpl() {
    }

    public Map<String, Integer> getIngredients() {
        return INGREDIENTS_MAP;
    }

    public void deduct(String name, Integer amount) throws NoSuchIngredientException, InsufficientStockException {
        if (name != null && !name.isEmpty()) {
            if (INGREDIENTS_MAP.containsKey(name)) {
                Integer productCount = INGREDIENTS_MAP.get(name);
                if (productCount > amount) {
                    INGREDIENTS_MAP.put(name, (productCount - amount));
                } else {
                    throw new InsufficientStockException("Insufficient Stock for " + name + "in the Inventory");
                }
            } else {
                throw new NoSuchIngredientException("Ingredient: " + name + " not found in the Inventory!");
            }
        }
    }

}
