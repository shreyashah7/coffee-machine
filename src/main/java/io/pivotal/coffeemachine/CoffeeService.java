package io.pivotal.coffeemachine;

import io.pivotal.coffeemachine.exception.InsufficientStockException;
import io.pivotal.coffeemachine.exception.NoSuchDrinkException;
import io.pivotal.coffeemachine.exception.NoSuchIngredientException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CoffeeService {

    private Inventory inventory;
    private static final Map<String, Double> menu;
    private static final Map<String, Drink> drinkIngredientMap;

    static {
        menu = new HashMap();
        menu.put("coffee", 2.75);
        menu.put("cappuccino", 2.90);
        menu.put("caffe mocha", 3.90);

        drinkIngredientMap = new HashMap();
        Map<String, Integer> ingredientList = new HashMap<String, Integer>();

        /*Object Initializer here bacause generally this objects comes from Controller class 
        or JSON comes from UI and gets converted into object into controller 
        And services mostly comprise of core operation performed on the objects available from the controller */
        
        Drink coffee = new Drink();
        coffee.setName("coffee");
        coffee.setCost(2.75);

        ingredientList.put("coffee", 2);
        ingredientList.put("sugar", 1);
        coffee.setIngredients(ingredientList);

        drinkIngredientMap.put("coffee", coffee);

        Drink cappuccino = new Drink();
        cappuccino.setName("cappuccino");
        cappuccino.setCost(2.90);

        ingredientList = new HashMap<String, Integer>();
        ingredientList.put("coffee", 2);
        ingredientList.put("sugar", 1);
        ingredientList.put("cream", 2);
        cappuccino.setIngredients(ingredientList);

        drinkIngredientMap.put("cappuccino", cappuccino);

        Drink mocha = new Drink();
        mocha.setName("caffe mocha");
        mocha.setCost(3.90);

        ingredientList = new HashMap<String, Integer>();
        ingredientList.put("coffee", 1);
        ingredientList.put("sugar", 1);
        ingredientList.put("cream", 1);
        mocha.setIngredients(ingredientList);

        drinkIngredientMap.put("caffe mocha", mocha);
    }

    public CoffeeService(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Returns the menu for this coffee machine.
     *
     * @return a map of drink name to drink cost
     */
    public Map<String, Double> getMenu() {
        return Collections.unmodifiableMap(menu);
    }

    /**
     * Make a drink using the given name. Ingredients for the drink are deducted
     * from the inventory.
     *
     * @param name the name of the drink
     * @return
     * @throws io.pivotal.coffeemachine.exception.NoSuchDrinkException
     * @throws io.pivotal.coffeemachine.exception.NoSuchIngredientException
     * @throws io.pivotal.coffeemachine.exception.InsufficientStockException
     */
    public Drink makeDrink(String name) throws NoSuchDrinkException, NoSuchIngredientException, InsufficientStockException {
        if (name != null && !name.isEmpty()) {
            if (menu.containsKey(name)) {
                for (Map.Entry<String, Integer> ingredient : drinkIngredientMap.get(name).getIngredients().entrySet()) {
                    this.inventory.deduct(ingredient.getKey(), ingredient.getValue());
                }
                return drinkIngredientMap.get(name);
            } else {
                throw new NoSuchDrinkException("No such drink with name :" + name + " found!");
            }
        }
        return null;
    }

    /**
     * Adds a new drink on the menu. Checks whether that drink already on the
     * list. Later checks for the inventory stock whether present or not Creates
     * new drink if successful
     *
     * @param drinkObj - Drink Class Object with pre defined Values.
     * @throws java.lang.Exception
     */
    public void addDrinkOnMenu(Drink drinkObj) throws Exception {
        if (drinkObj != null && drinkObj.getIngredients() != null) {
            if (!menu.containsKey(drinkObj.getName())) {
                if (checkIngredientStock(drinkObj)) {
                    menu.put(drinkObj.getName(), drinkObj.getCost());
                    drinkIngredientMap.put(drinkObj.getName(), drinkObj);
                } else {
                    throw new Exception("No such Stock in the inventory!");
                }
            } else {
                throw new Exception("Drink Already Exists on the Menu");
            }
        }
    }

    /**
     * Checks the inventory status whether it contains the ingredient. Later
     * checks for the count of the stock for that ingredient. Returns the status
     * for the stock
     *
     * @param drinkObj - Drink Class Object with pre defined Values.
     * @return Inventory Check Boolean
     */
    private boolean checkIngredientStock(Drink drink) {
        //Need to use (this.inventory) object instead of "new InventoryImpl()" but mockito class doesn't create the object calling constructor nor static initializer.
        Map<String, Integer> invIngredients = new InventoryImpl().getIngredients();
        for (Map.Entry<String, Integer> ingredient : drink.getIngredients().entrySet()) {
            if (invIngredients.containsKey(ingredient.getKey())) {
                if (invIngredients.get(ingredient.getKey()) < ingredient.getValue()) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

}
