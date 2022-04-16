package edu.ucalgary.ensf409;

import java.util.*;

public class CalculateHamperCopy{
    private static LinkedList<int[]> combinations = new LinkedList<int[]>();
    private static boolean allAreOverShot =false;
    private static ArrayList<FoodItem> foods;
    private static Hamper hamper = null;

    public static Hamper calculateHamper(String[] order) throws InsufficientFoodException{
        combinations.clear();
        tmp.clear();
        allAreOverShot = false;
        foods = Inventory.getInventory();
        // Get a copy of the Inventory
        // Create a Client array containing the people the hamper will need to serve
        Client clients[] = new Client[order.length];
        for(int i = 0; i<order.length; i++){
            clients[i] = new Client( Integer.parseInt(order[i]) );
        }
        // Create all possible combinations using the given FoodItems
        
        for(int i = 1;i<foods.size();i++){
            //TODO
            combination(foods.size(),1, i);
        }

        int[] theChosenOne = null;
        int currentLength = 0;
        for(int[] c:combinations){
            if(c.length != currentLength){
                if(allAreOverShot && hamper !=null){
                    break;
                }
                allAreOverShot = true;
                currentLength = c.length;
            }
            FoodItem[] food = new FoodItem[c.length];
            for(int i = 0; i<c.length;i++){
                food[i] = foods.get(c[i]);
            }
            Hamper compare = new Hamper(food, clients);
            boolean check= checkRequirementsMet(compare);
            if(!check){
                continue;
            }
            if(hamper ==null){
                hamper = new Hamper(food, clients);
                theChosenOne = c;
                continue;
            }

            if(calculateNutritionWaste(compare) < calculateNutritionWaste(hamper)){
                theChosenOne = c;
                hamper = compare;
            }
        }
        if(hamper == null || !checkRequirementsMet(hamper)){
            throw new InsufficientFoodException();
        }
        for(int i=0; i<theChosenOne.length;i++){
            Inventory.deleteFoodItem(foods.get(i));
        }
        return hamper;
    }
    private static LinkedList<Integer> tmp = new LinkedList<Integer>();
    private static void combination(int n, int left, int k){
        System.out.println("Calculating another combination");
        LinkedList<Integer> tmp2 = tmp;
        if (k == 0) {
            add(tmp);
        }
        for (int i = left; i <= n; ++i)
        {
            tmp.add(i);
            combination(n, i + 1, k - 1);
            tmp.remove(tmp.size() - 1);
        }
    }
    
    private static void add(LinkedList<Integer> tmp2) {
        Byte byteTotal =0b00000;
        int[]arr = new int[tmp2.size()];
        for(int i = 0;i<tmp2.size();i++){
            int imm = tmp2.get(i)-1;
            byteTotal = (byte) (byteTotal|foods.get(i).getNutritionContent().getBytes());
            arr[i] = imm ;
        }
        if(byteTotal !=0b11111){
            return;
        }
        combinations.add(arr);
    }

    public static int[] calculateHamperNutrition(Hamper hamper){
        int[] n = {0,0,0,0,0};
        for(FoodItem c: hamper.getContents()){
            for(int i = 0; i<n.length; i++){
                n[i] += c.getNutritionContent().getNutrition()[i];
            }
        }
        return n;
    }
    private static boolean checkRequirementsMet(Hamper hamper1){
        int[] foods = hamper1.getNutritionContent().getNutrition();
        int[] needs = hamper1.getNutritionNeeded().getNutrition();
        for(int i = 0; i<5;i++){
            if(foods[i]<needs[i]){
                allAreOverShot = false;
                return false;
            }
        }
        return true;
    }

    public static int calculateNutritionWaste(Hamper hamper){
        int items = hamper.getNutritionContent().getCalories();
        int needed =hamper.getNutritionNeeded().getCalories();
        return items-needed;
    }
}
