package pulkit.coffeemachine;

import java.util.HashMap;

/**
 * Enum for types of Ingredients available.
 */
public enum IngredientType {    
    HOT_WATER("hot_water"),
    GINGER_SYRUP("ginger_syrup"),
    SUGAR_SYRUP("sugar_syrup"),
    GREEN_MIX("green_mixture"),
    TEA_LEAVES_SYRUP("tea_leaves_syrup"),
    HOT_MILK("hot_milk");

    private String ingredient;
    private static final HashMap<String,IngredientType> lookup = new HashMap<String,IngredientType>();    

    private IngredientType(String i){
        this.ingredient = i;
    }

    public String getIngredientName(){
        return ingredient;
    }

    public static IngredientType lookup(String ing){
        return lookup.get(ing);
    }
    

    static{
        for(IngredientType ing : IngredientType.values()){
            lookup.put(ing.getIngredientName(),ing);
        }
    }
}