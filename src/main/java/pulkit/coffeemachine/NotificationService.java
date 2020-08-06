package pulkit.coffeemachine;

import java.util.HashSet;

/**
 * Service to generate notifications on console output.
 */

public class NotificationService {

    public static HashSet<IngredientType> generated = new HashSet<IngredientType>(); //to keep a track of notifications generated to avoid repeated notification.

    public static void generateNotification(IngredientType ingredient){
        if(!generated.contains(ingredient)){
            System.out.println("*******SYSTEM NOTIFICATION********");
            System.out.println(ingredient.toString() + " is running low");
            generated.add(ingredient);
        }
    }

    public static void reset(){
        generated.clear();
    }
    
}