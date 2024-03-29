package tests.edu.ucalgary.ensf409;

import edu.ucalgary.ensf409.*;
import org.junit.*;
import static org.junit.Assert.*;

//Nutrition class, Nutrition Content, Insufficent Food Exception 
public class TestNutrition{
   
//Testing getNutrition 
@Test 
    public void TestGetNutrition(){
        NutritionContent defaultNutrition = new NutritionContent(100,0,50,120,50);
        int[] expected = {100,0,50,120,50};
        int[] actual = defaultNutrition.getNutrition();
        assertArrayEquals("There is something wrong with getNurition", expected, actual);

    }
//Testing getGrains
@Test 
    public void TestGetGrains(){
        NutritionContent defaultNutrition = new NutritionContent(100,0,50,120,50);
        int expected = 100;
        int actual = defaultNutrition.getGrains();
        assertEquals("There is something wrong with getGrains", expected, actual);

    }
//Testing getFruitVeggies
@Test
    public void TestGetFruitVeggies(){
        NutritionContent defaultNutrition = new NutritionContent(100,0,50,120,50);
        int expected = 0;
        int actual = defaultNutrition.getFruitVeggies();
        assertEquals("There is something wrong with getFruitVeggies", expected, actual);



    }
//Testing getProteins 
@Test 
    public void TestGetProtein(){
        NutritionContent defaultNutrition = new NutritionContent(100,0,50,120,50);
        int expected = 50;
        int actual = defaultNutrition.getProtein();
        assertEquals("There is something wrong with getProtein", expected, actual);

    }
//Testing getCalories 
@Test 
    public void TestGetOther(){
        NutritionContent defaultNutrition = new NutritionContent(100,0,50,120,50);
        int expected = 120;
        int actual = defaultNutrition.getOther();
        assertEquals("There is something wrong with getOther", expected, actual);

    
    }
//Testing getCalories
@Test 
    public void TestGetCalories(){
        NutritionContent defaultNutrition = new NutritionContent(100,0,50,120,50);
        int expected = 50;
        int actual = defaultNutrition.getCalories();
        assertEquals("There is something wrong with getCalories", expected, actual);


    }
//Testing enums
@Test 
    public void TestEnumNutritionClasses(){
        Nutrition expected = Nutrition.WHOLEGRAINS;
        Nutrition actual = Nutrition.valueOf("WHOLEGRAINS");
        assertEquals("There is something wrong with enum method Whole Grains ",expected, actual);

        expected = Nutrition.FRUITVEGGIES;
        actual = Nutrition.valueOf("FRUITVEGGIES");
        assertEquals("There is something wrong with enum method Fruit Veggies ",expected, actual);

        expected = Nutrition.PROTEIN;
        actual = Nutrition.valueOf("PROTEIN");
        assertEquals("There is something wrong with enum method Protein ",expected, actual);

        expected = Nutrition.OTHER;
        actual = Nutrition.valueOf("OTHER");
        assertEquals("There is something wrong with enum method Other ",expected, actual);

        expected = Nutrition.CALORIES;
        actual = Nutrition.valueOf("CALORIES");
        assertEquals("There is something wrong with enum method Calories ",expected, actual);

               
    }
}