package praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerCompleteTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule().strictness(Strictness.LENIENT);

    private Burger burger;

    @Mock
    private Bun bunMock;

    @Mock
    private Ingredient sauceMock;

    @Mock
    private Ingredient fillingMock;

    @Before
    public void setUp() {
        burger = new Burger();

        when(bunMock.getName()).thenReturn("Complete Bun");
        when(bunMock.getPrice()).thenReturn(100.0f);

        when(sauceMock.getType()).thenReturn(IngredientType.SAUCE);
        when(sauceMock.getName()).thenReturn("Complete Sauce");
        when(sauceMock.getPrice()).thenReturn(50.0f);

        when(fillingMock.getType()).thenReturn(IngredientType.FILLING);
        when(fillingMock.getName()).thenReturn("Complete Filling");
        when(fillingMock.getPrice()).thenReturn(75.0f);
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(bunMock);
        assertEquals(bunMock, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(sauceMock);
        assertEquals(1, burger.ingredients.size());
        assertEquals(sauceMock, burger.ingredients.get(0));
    }

    @Test
    public void testAddMultipleIngredients() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);
        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void testAddMultipleIngredientsMaintainsOrder() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);
        assertEquals(sauceMock, burger.ingredients.get(0));
        assertEquals(fillingMock, burger.ingredients.get(1));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);
        burger.removeIngredient(0);
        assertEquals(1, burger.ingredients.size());
        assertEquals(fillingMock, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);

        burger.moveIngredient(0, 1);

        assertEquals(fillingMock, burger.ingredients.get(0));
        assertEquals(sauceMock, burger.ingredients.get(1));
    }

    @Test
    public void testGetPrice() {
        burger.setBuns(bunMock);
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);

        float price = burger.getPrice();
        assertEquals(325.0f, price, 0.001f);
    }

    @Test
    public void testGetPriceWithOnlyBun() {
        burger.setBuns(bunMock);
        float price = burger.getPrice();
        assertEquals(200.0f, price, 0.001f);
    }

    @Test
    public void testGetReceipt() {
        burger.setBuns(bunMock);
        burger.addIngredient(sauceMock);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains("Complete Bun"));
        assertTrue(receipt.contains("Complete Sauce"));
        assertTrue(receipt.contains("Price: 250.000000"));
    }

    @Test
    public void testGetReceiptWithMultipleIngredients() {
        burger.setBuns(bunMock);
        burger.addIngredient(sauceMock);
        burger.addIngredient(fillingMock);

        String receipt = burger.getReceipt();
        assertTrue(receipt.contains("Complete Bun"));
        assertTrue(receipt.contains("Complete Sauce"));
        assertTrue(receipt.contains("Complete Filling"));
        assertTrue(receipt.contains("Price: 325.000000"));
    }
    @Test
    public void testGetReceiptFormat() {
        burger.setBuns(bunMock);
        String receipt = burger.getReceipt();
        assertTrue(receipt.startsWith("(==== Complete Bun ====)"));
        assertTrue(receipt.contains("(==== Complete Bun ====)"));
        assertTrue(receipt.contains("Price: 200.000000"));
    }
}