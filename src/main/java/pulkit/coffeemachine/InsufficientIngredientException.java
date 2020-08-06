package pulkit.coffeemachine;


/**
 * Exception class to track for Insufficient Ingredients
 */
public class InsufficientIngredientException extends Exception {

    InsufficientIngredientException(String s){
        super(s);
        System.out.println("The Ingredients are finished");
    }

    
}