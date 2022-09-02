package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final OperationHandler operationHandler = new BalanceOperationHandler();
    private static Transaction transaction;
    private static final Map<Fruit, Integer> storage = Storage.getAll();

    @Before
    public void setUp() {
        transaction = new Transaction("b", new Fruit("banana"), null);
    }

    @Test
    public void applyBalanceOperationHandler_getFruitBanana_isValid() {
        Fruit expected = new Fruit("banana");
        Fruit actual = transaction.getFruit();
        assertEquals(expected, actual);
    }

    @Test
    public void applyBalanceOperationHandler_getFruitApple_isValid() {
        transaction.getFruit().setName("apple");
        Fruit expected = new Fruit("apple");
        Fruit actual = transaction.getFruit();
        assertEquals(expected, actual);
    }

    @Test
    public void applyBalanceOperationHandler_currentQuantity15_isValid() {
        storage.put(new Fruit("banana"),15);
        Integer expected = 15;
        Integer actual = storage.get(transaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    public void applyBalanceOperationHandler_currentQuantityNull_isValid() {
        storage.put(new Fruit("apple"), 0);
        Integer expected = 0;
        Integer actual = storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void applyBalanceOperationHandler_AppleIs25_isValid() {
        storage.put(new Fruit("apple"), 20);
        operationHandler.apply(new Transaction("b", new Fruit("apple"), 5));
        Integer expected = 25;
        Integer actual = storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void applyBalanceOperationHandler_AppleIs10_isValid() {
        operationHandler.apply(new Transaction("b", new Fruit("apple"), 10));
        Integer expected = 10;
        Integer actual = storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
