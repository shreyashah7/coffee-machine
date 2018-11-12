package io.pivotal.coffeemachine;

import io.pivotal.coffeemachine.exception.InsufficientStockException;
import io.pivotal.coffeemachine.exception.NoSuchDrinkException;
import io.pivotal.coffeemachine.exception.NoSuchIngredientException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link CoffeeService}.
 */
public class CoffeeServiceTests {

    private CoffeeService machine;

    private Inventory inventory;

    private Drink newDrink;

    /**
     * Setting Up the required objects to be used for creating new drinks
     */
    @Before
    public void setUp() {
        this.inventory = mock(Inventory.class);
        this.machine = new CoffeeService(this.inventory);

        newDrink = new Drink();
        newDrink.setName("latte");
        newDrink.setCost(2.10);
        Map<String, Integer> ingredients = new HashMap();
        ingredients.put("coffee", 1);
        ingredients.put("sugar", 1);
        ingredients.put("cream", 2);
        newDrink.setIngredients(ingredients);
    }

    @Test
    public void getMenu() {
        Map<String, Double> menu = this.machine.getMenu();
        assertThat(menu).contains(entry("coffee", 2.75));
        assertThat(menu).contains(entry("cappuccino", 2.90));
        assertThat(menu).contains(entry("caffe mocha", 3.90));
    }

    @Test
    public void makeDrink() throws NoSuchIngredientException, InsufficientStockException, NoSuchDrinkException {
        this.machine.makeDrink("cappuccino");
        verify(this.inventory).deduct("coffee", 2);
        verify(this.inventory).deduct("sugar", 1);
        verify(this.inventory).deduct("cream", 2);
    }

    /**
     * Test case for adding new drink on the menu
     *
     * @throws Exception
     */
    @Test
    public void addDrink() throws Exception {
        this.machine.addDrinkOnMenu(newDrink);
        Map<String, Double> menu = this.machine.getMenu();
        assertThat(menu).contains(entry("latte", 2.10));
    }

}
