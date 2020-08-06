package pulkit.coffeemachine;

import java.util.HashMap;
import java.lang.reflect.Field;


/**
 * Class to collect amount of each ingredient.
 * 
 */
public class Ingredients {

    int hot_water;
    int hot_milk;
    int ginger_syrup;
    int sugar_syrup;
    int green_mixture;
    int tea_leaves_syrup;

    public Ingredients(int hot_milk,int hot_water, int ginger_syrup,int sugar_syrup,int green_mixture,int tea_leaves_syrup){
        this.hot_milk = hot_milk;
        this.hot_water = hot_water;
        this.ginger_syrup = ginger_syrup;
        this.sugar_syrup = sugar_syrup;
        this.tea_leaves_syrup = tea_leaves_syrup;
        this.green_mixture = green_mixture;
    }
    
    /**
     * 
     * @param i 
     * Add two ingredients objects;
     */
    public void add(Ingredients i){
        this.hot_milk += i.hot_milk;
        this.hot_water += i.hot_water;
        this.ginger_syrup += i.ginger_syrup;
        this.sugar_syrup += i.sugar_syrup;
        this.tea_leaves_syrup += i.tea_leaves_syrup;
        this.green_mixture += i.green_mixture;
    }

    /**
     * Subtract two ingredients objects
     */
    public void reduce(Ingredients i){
        this.hot_milk -= i.hot_milk;
        this.hot_water -= i.hot_water;
        this.ginger_syrup -= i.ginger_syrup;
        this.sugar_syrup -= i.sugar_syrup;
        this.tea_leaves_syrup -= i.tea_leaves_syrup;
        this.green_mixture -= i.green_mixture;
    }
     
    
    public HashMap<String,Integer> getMap(){
        try{
            HashMap<String,Integer> ingredientMap = new HashMap<String,Integer>();            
            Field[] fields = Ingredients.class.getDeclaredFields();
            for(Field f:fields){
                ingredientMap.put(f.getName(), (Integer) f.get(this));
            }
            return ingredientMap;
        }
        catch(IllegalAccessException e){
            System.out.println("Error Occured" + e);
            return null;
        }
    }

}