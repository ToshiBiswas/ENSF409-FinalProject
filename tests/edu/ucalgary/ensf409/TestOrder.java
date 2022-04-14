package tests.edu.ucalgary.ensf409;

import edu.ucalgary.ensf409.*;
import static org.junit.Assert.assertArrayEquals;
import org.junit.*;

public class TestOrder
{
    public String[][] request = {{"Chocolate bar"}, {"sandwitch"}, {"pickles"}, {"cheese"}, {"shawarma", "fires"}, {"hamburger", "rosted potatoes"}, {"cerial", "milk"}};

    /**
     * sends in a valid request
     * expects the getHampers to return the correct hamper
     */
    @Test
    public void TestgetHampers() throws InsufficientFoodException
    {
        // Setup Inventory for the test
        FoodItem[] foods = new FoodItem[] {
            new FoodItem("1","Apple, dozen", new int[]{0,100,0,0,624}),     // ('Apple, dozen', 0, 100, 0, 0, 700),
            new FoodItem("2","Ham", new int[]{0,0,100,0,900}),              // ('Ham', 0, 0, 100, 0, 250),     
            new FoodItem("3","Spam", new int[]{50,0,50,0,800}),             // ('Spam', 50, 0, 50, 0, 800),
            new FoodItem("4","Eggs, dozen", new int[]{0,0,9,91,864}),       // ('Eggs, dozen', 0, 0, 9, 91, 864),
            new FoodItem("5","Banana, bunch 5", new int[]{0,97,3,0,605}),   // ('Banana, bunch 5', 0, 97, 3, 0, 605), 
            new FoodItem("6","Mineral Water", new int[]{0,0,0,100,750}),    // ('Mineral Water', 0, 0, 0, 100, 750),
            new FoodItem("7","Tuna", new int[]{0,0,100,0,100})              // ('Tuna', 0, 0, 100, 0, 100),
        };
        for (FoodItem food : foods) {
            Inventory.addFoodItem(food);
        }

        Order order = new Order(request);

        FoodItem expected[] = new Hamper( 
            new FoodItem[]{ foods[0], foods[1], foods[2], foods[5]}, 
            new Client[]{ new Client(1) }    // Client(id 1) = Adult Male (according to Project Handout)
            ).getContents();
        FoodItem actual[] = order.getHampers()[0].getContents();

        assertArrayEquals("getHampers did not returnt he correct string", expected, actual);
    }

    /**
     * uses create Hamper to create a expected hamper
     * then uses getHamper to get the result
     * then checks if the returned hamper is equal to the expected hamper
     */
    @Test
    public void TestcreateHampers() throws InsufficientFoodException
    {
        // Setup Inventory for the test
        FoodItem[] foods = new FoodItem[] {
            new FoodItem("1","Apple, dozen", new int[]{0,100,0,0,624}),     // ('Apple, dozen', 0, 100, 0, 0, 700),
            new FoodItem("2","Ham", new int[]{0,0,100,0,900}),              // ('Ham', 0, 0, 100, 0, 250),     
            new FoodItem("3","Spam", new int[]{50,0,50,0,800}),             // ('Spam', 50, 0, 50, 0, 800),
            new FoodItem("4","Eggs, dozen", new int[]{0,0,9,91,864}),       // ('Eggs, dozen', 0, 0, 9, 91, 864),
            new FoodItem("5","Banana, bunch 5", new int[]{0,97,3,0,605}),   // ('Banana, bunch 5', 0, 97, 3, 0, 605), 
            new FoodItem("6","Mineral Water", new int[]{0,0,0,100,750}),    // ('Mineral Water', 0, 0, 0, 100, 750),
            new FoodItem("7","Tuna", new int[]{0,0,100,0,100})              // ('Tuna', 0, 0, 100, 0, 100),
        };
        for (FoodItem food : foods) {
            Inventory.addFoodItem(food);
        }

        Order order = new Order(request);
        order.createHampers();

        FoodItem expected[] = new Hamper( 
            new FoodItem[]{ foods[0], foods[1], foods[2], foods[5]}, 
            new Client[]{ new Client(1) }    // Client(id 1) = Adult Male (according to Project Handout)
            ).getContents();
        FoodItem actual[] = order.getHampers()[0].getContents();

        assertArrayEquals("createHampers() did not correctly calculate the correct hampers", expected, actual);
    }
}