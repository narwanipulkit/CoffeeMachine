package pulkit.coffeemachine;

import pulkit.coffeemachine.BeverageType;
import pulkit.coffeemachine.Ingredients;

/**
 * Class to serialize json data.
 */
public class CoffeeMachineDetails {
    Machine machine;    
}

class Machine{
    Outlets outlets;
    Ingredients total_items_quantity;
    Beverages beverages;
}

class Outlets{ 
    int count_n;
}

class Beverages {    
    Ingredients hot_tea;
    Ingredients hot_coffee;
    Ingredients black_tea;
    Ingredients green_tea;

    public Ingredients get(BeverageType beverage){
        switch (beverage){
            case BLACK_TEA:
                return black_tea;
            case GREEN_TEA:
                return green_tea;
            case HOT_COFFEE:
                return hot_coffee;
            case HOT_TEA:
                return hot_tea;
            default:
                return null;
        }
    }
        
}



