package pulkit.coffeemachine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import pulkit.coffeemachine.BeverageType;
import pulkit.coffeemachine.IngredientType;
import pulkit.coffeemachine.Ingredients;

/**
 * Main CoffeeMachine class with coffee machine functionalities.
 * 
 */
public class CoffeeMachine{

    
    private enum Power{ON,OFF} 
    private int outlets;
    private AtomicInteger serving;  
    private AtomicInteger servingRequest;  
    private IngredientStorage storage;
    ScheduledExecutorService executorService;
    private HashMap<BeverageType,Recipe> recipes;
    Power machinePower;    

    /**
     * 
     * @param details - Serialized json input to create coffee machine with recipes and intital storage
     */
    CoffeeMachine(CoffeeMachineDetails details){
        machinePower = Power.ON;
        serving = new AtomicInteger(0);
        servingRequest = new AtomicInteger(1);
        recipes = new HashMap<BeverageType,Recipe>();
        outlets = details.machine.outlets.count_n;
        storage = new IngredientStorage(details.machine.total_items_quantity);
        createRecipeMap(details.machine.beverages); 
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(()->monitorLevels(10), 0, 2, TimeUnit.SECONDS);
    }

    
    public void stopMachine(){
        machinePower = Power.OFF;
    }

    /**
     * 
     * @param threshold - defines the amount after with notification should be generated.
     * This function is called as a Scheduled Executor with period of 2 seconds. So as to check
     * levels every 2 seconds and give a notfication if low
     */
    public void monitorLevels(int threshold){        
        HashMap<IngredientType,AtomicInteger> ingredients = storage.getStorageStatus();
        if(ingredients!=null){
            for(IngredientType ingredient : ingredients.keySet()){
                int currentIngredientValue = ingredients.get(ingredient).get();
                if(currentIngredientValue<threshold){
                    NotificationService.generateNotification(ingredient);
                }
            }
        }

        
    }

    /**
     * 
     * @param b - Beverages object containg Ingredients and amount.
     * This function helps populate hashmap to get mapping of beverage type to its recipe.
     *
     */
    public void createRecipeMap(Beverages b){
        try{
            Field[] beverages = b.getClass().getDeclaredFields();
            for(Field beverage:beverages){
                Ingredients ingredients = (Ingredients)beverage.get(b);                
                recipes.put(BeverageType.valueOf(beverage.getName().toUpperCase()),new Recipe(beverage.getName(),ingredients));
            }
        }catch(IllegalAccessException e){
            System.err.println("Error with beverage passed");
            System.err.println(e);
        }
    }
    
    /**
     * 
     * @param ingredients
     * Method to refill the storage
     */
    public void refillStorage(Ingredients ingredients){
        storage.refill(ingredients);
    }

    /**
     * 
     * @param beverageName beverage to be served
     * Async Function to serve beverage.
     */
    public void serveBeverage(BeverageType beverageName){            
            CompletableFuture.runAsync(() -> serve(beverageName));                    
    }

    /**
     * Serve logic function.
     */
    public boolean serve(BeverageType beverageName){     
        try{   
            final int current_request = servingRequest.getAndIncrement(); //Keep Track of Request Numbers.
            System.out.println("Request to serve - " + beverageName.toString() + " Request Number - "+current_request);
            System.out.println("Processing Request");
            TimeUnit.SECONDS.sleep(1); //Simulate request process time            
            Recipe required = recipes.get(beverageName); //Get recipe for the beverage i.e amount of each ingredient required.
            int current_serving = serving.getAndIncrement(); //Get count of requests currently being served
            //Check if Outlet is free to be used.
            if(current_serving>=outlets){
                System.out.println("All Outlets Busy Request is in queue.Please note your request number - "+current_request);
                serving.decrementAndGet();                
                while(serving.get()>=outlets);
            }
            
            //Check Availability in storage;
            if(storage.checkAvailability(required.getAmounts())){
                System.out.println("Trying to serve request - " + current_request);            
                
                storage.useIngredients(required.getAmounts());                
                System.out.println("Mixing Request - " + current_request);
                TimeUnit.SECONDS.sleep(2); //Simulate serving takes 2 seconds 
                System.out.println("*******Beverage Served, Request - " + current_request + "*********"); 
                serving.decrementAndGet();  
                return true;         
            }
            else{
                System.err.println("Not Enough Ingredients Available.");                
            }
                serving.decrementAndGet();
                return false;
            }
            catch(InsufficientIngredientException e){
                System.out.println("Ingredients are not available");
                return false;                
            }
            catch(InterruptedException e){
                System.out.println("Serving Interrupted due to Malfunction " + e);
                return false;                
            }
        }
}