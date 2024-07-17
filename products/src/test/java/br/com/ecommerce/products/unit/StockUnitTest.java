package br.com.ecommerce.products.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ecommerce.products.infra.entity.product.Stock;

public class StockUnitTest {

    private final int POSITIVE_UNITS = 100;
    private final int NEGATIVE_UNITS = -100;


    @Test
    @DisplayName("Unit - Must create a stock")
    void createStockTest01() {
        assertDoesNotThrow(() -> new Stock(POSITIVE_UNITS));
    }
    @Test
    @DisplayName("Unit - Should not create a stock with negative units")
    void createStockTest02() {
        assertThrows(IllegalArgumentException.class, () -> new Stock(NEGATIVE_UNITS));
    }

    @Test
    void updateStock_withNegativeOne_shouldHaveOneUnitLeft() {
        // arran
        Stock stock = new Stock(POSITIVE_UNITS);
        var SUBTRACT = -2;
        var EXPECTED = stock.getUnit() + SUBTRACT;

        // act
        stock.update(SUBTRACT);

        // assert
        int remainingUnits = stock.getUnit();
        assertEquals(EXPECTED, remainingUnits, "Stock should be updated to 1 unit");
    }
    @Test
    void updateStock_withNegativeTwo_shouldHaveZeroUnitsLeft() {
        // arrange
        Stock stock = new Stock(POSITIVE_UNITS);
        var EXPECTED = 0;

        // act
        stock.update(NEGATIVE_UNITS);

        // assert
        int remainingUnits = stock.getUnit();
        assertEquals(EXPECTED, remainingUnits, "Stock should be updated to 0 units");
    }
    @Test
    void updateStock_withExcessivelyNegativeValue_shouldHaveZeroUnitsLeft() {
        // arrange
        Stock stock = new Stock(POSITIVE_UNITS);

        // act
        stock.update(-1000);

        // assert
        int remainingUnits = stock.getUnit();
        assertEquals(0, remainingUnits, "Stock should be updated to 0 units even when the update value is excessively negative");
    }
}