package pulkit.coffeemachine;

import java.lang.reflect.Field;
import java.util.HashMap;

import pulkit.coffeemachine.IngredientType;
import pulkit.coffeemachine.Ingredients;

/**
 * Class to depict beverage and the recipe, i.e the amount of ingredients it requires.
 */
public class Recipe {

    String beverage;
    HashMap<IngredientType,Integer> amounts;
    Ingredients ingredients;

    Recipe(String name,Ingredients ingredients){
        amounts = new HashMap<IngredientType,Integer>();
        try{
            beverage = name;
            this.ingredients = ingredients;
            Field[] fields = ingredients.getClass().getDeclaredFields();
            for(Field f : fields){
                IngredientType ingredient = IngredientType.lookup(f.getName());
                amounts.put(ingredient,f.getInt(ingredients));
            }
        }catch(IllegalAccessException e){
            System.err.println(e);
        }        
    }

    public HashMap<IngredientType,Integer> getAmounts(){
        return amounts;
    }
}