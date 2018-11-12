package io.pivotal.coffeemachine;

import io.pivotal.coffeemachine.exception.InsufficientStockException;
import io.pivotal.coffeemachine.exception.NoSuchIngredientException;
import java.util.Map;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * Abstract base class for for {@link Inventory} tests.
 */
public abstract class InventoryTests {

	protected abstract Inventory getInventory();

	@Test
	public void getIngredientsShouldReturnTheIngredientsInInventory() {
		Inventory inventory = getInventory();
		Map<String, Integer> ingredients = inventory.getIngredients();
		assertThat(ingredients).contains(entry("coffee", 10));
		assertThat(ingredients).contains(entry("sugar", 10));
		assertThat(ingredients).contains(entry("cream", 10));
	}

	@Test
	public void deductShouldReduceQuantity() throws NoSuchIngredientException, InsufficientStockException {
		Inventory inventory = getInventory();
		inventory.deduct("coffee", 2);
		Map<String, Integer> ingredients = inventory.getIngredients();
		assertThat(ingredients).contains(entry("coffee", 8));
	}

}
