package pulkit.coffeemachine;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import pulkit.coffeemachine.IngredientType;
import pulkit.coffeemachine.Ingredients;

import java.lang.reflect.Field;
import java.util.HashMap;


/**
 * Class for Storage of ingredients in a coffee machine
 */
public class IngredientStorage {

    private HashMap<IngredientType,AtomicInteger> ingredientsInStore = new HashMap<IngredientType,AtomicInteger>();

    IngredientStorage(Ingredients initial){  
        try{      
            Field[] fields = initial.getClass().getDeclaredFields();
            for(Field f : fields){
                ingredientsInStore.put(IngredientType.lookup(f.getName()),new AtomicInteger(f.getInt(initial)));            
            }
        }catch(IllegalAccessException e){
            System.err.println(e);
        }
    }
    

    /**
     * Function to refill the ingredients of the Machine.
     */
    public void refill(Ingredients i){        
        try{
            Field[] fields = i.getClass().getDeclaredFields();     //Get Ingredients type variables.     
            for(Field f : fields){                     
                IngredientType ingredient = IngredientType.lookup(f.getName());  //Get enum from name.
                final int ingredientAmount = f.getInt(i);                       //Get amount for given ingredient type.
                ingredientsInStore.get(ingredient).updateAndGet((val)->val+ingredientAmount);                                    
            } 
            NotificationService.reset();                        //Reset notification service to give notifications for all. 
        }catch(IllegalAccessException e){
            System.out.println(e);
        }
    }


    public HashMap<IngredientType,AtomicInteger> getStorageStatus(){
        return ingredientsInStore;
    }

    /**
     * Function to check availability of ingredients
     */
    public boolean checkAvailability(Ingredients i){
        /**
         * Get fields in Ingredients class and get name and value for it.
         * Check the amount with the amount available in storage.
         */
        try{
            Field[] fields = i.getClass().getDeclaredFields();
            for(Field f : fields){
                IngredientType ingredient = IngredientType.lookup(f.getName());
                int available = ingredientsInStore.get(ingredient).get();
                if(available<f.getInt(i)){
                    System.out.println(f.getName()+" is not available.Please refill");
                    return false;
                }
            }        
            return true;
        }catch(IllegalAccessException e){
            System.err.println(e);
            return false;
        }

    }


    /**
     * 
     * Overloaded function to check availability.
     */
    public boolean checkAvailability(HashMap<IngredientType,Integer> i){        
            for(IngredientType ing : i.keySet()){
                if(ingredientsInStore.get(ing).get()<i.get(ing)){
                    return false;
                }
            }
            return true;
        }        

    
    /**
     * 
     * Function to use the given set of ingredients from the storage.
     */
    public void useIngredients(Ingredients i){
        try{
            Field[] fields = i.getClass().getDeclaredFields();            
            for(Field f : fields){
                IngredientType ingredient = IngredientType.lookup(f.getName());
                final int ingredientAmount = f.getInt(i);
                final String ingredientName = f.getName();
                //Check if storage has amount to be reduced and then reduce.
                ingredientsInStore.get(ingredient).getAndUpdate(prev->{
                    if(prev<ingredientAmount){
                        System.err.println("The ingredient is not sufficient. Beverage Cannot be served");
                        System.err.println("Please refill ingredient - "+ingredientName);                          
                    }                  
                    else{
                        prev+=ingredientAmount;
                    }
                    return prev;
                });
            }                                                        
        }catch(IllegalAccessException e){
            System.out.println(e);
        }
    }


    /**
     * Overloaded function for using the ingredients.
     */
    public void useIngredients(HashMap<IngredientType,Integer> i) throws InsufficientIngredientException,InterruptedException{

        //Simulate getting ingredients takes some time  
        TimeUnit.SECONDS.sleep(2);       
        for(IngredientType ing : i.keySet()){
            //Check if storage has amount to be reduced and then reduce.
            ingredientsInStore.get(ing).getAndUpdate(prev->{
                if(prev<i.get(ing)){
                    System.err.println("The ingredient is not sufficient. Beverage Cannot be served");
                    System.err.println("Please refill ingredient - "+ing.toString());                      
                }                  
                else{
                    prev-=i.get(ing);                    
                }
                return prev;
            });
        }        
    }
            
}