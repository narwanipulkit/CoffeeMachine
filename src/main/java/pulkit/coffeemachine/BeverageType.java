package pulkit.coffeemachine;


/**
 * enum is to define the types of beverages available to be served.
 * 
 */

public enum BeverageType {
    HOT_TEA("Hot Tea"),
    HOT_COFFEE("Hot Coffee"),
    BLACK_COFFEE("Black Coffee"),
    BLACK_TEA("Black Tea"),
    GREEN_TEA("Green Tea");
    
    private String beverage;

    private BeverageType(String b){
        beverage = b;
    }

    
}