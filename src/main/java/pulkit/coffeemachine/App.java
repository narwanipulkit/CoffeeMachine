package pulkit.coffeemachine;

import com.google.gson.Gson;

import pulkit.coffeemachine.BeverageType;
import pulkit.coffeemachine.Ingredients;

import java.io.File;
import java.util.Scanner;

/**
 * This is a class to run the machine in interactive mode.
 * It uses the sample.json for Coffee Machine configuration.
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {        
        String dir = System.getProperty("user.dir");        
        File f = new File(dir +"/src/test/json/sample.json");
        Scanner sc = new Scanner(f);
        StringBuilder s = new StringBuilder();
        while(sc.hasNextLine()){
             s.append(sc.nextLine());
         }
         String json = s.toString();
         Gson gson = new Gson();
         CoffeeMachineDetails details = gson.fromJson(json,CoffeeMachineDetails.class);
         
        CoffeeMachine machine = new CoffeeMachine(details);        
        System.out.println("Powering on the Coffee Machine ....");
        Scanner userInput = new Scanner(System.in);
        System.out.println("Choose:");
        System.out.println("\u001B[31m"+"1. Serve Hot Tea\n2.Serve Hot Coffee\n3.Black Tea\n4.Black Coffee\n5.Green Tea\n6.Refill Machine\n0. Stop Machine"+"\u001B[0m");
        int input;
        while((input = userInput.nextInt())!=0){      
            if(input==0)
                break;
            switch(input){
                case 1:
                    machine.serveBeverage(BeverageType.HOT_TEA);
                    break;
                case 2:
                    machine.serveBeverage(BeverageType.HOT_COFFEE);
                    break;
                case 3:
                    machine.serveBeverage(BeverageType.BLACK_TEA);
                    break;
                case 4:
                    machine.serveBeverage(BeverageType.BLACK_COFFEE);
                    break;
                case 5:
                    machine.serveBeverage(BeverageType.GREEN_TEA);
                    break;
                case 6:
                    System.out.println("Enter general value to refill with");
                    int refillAmount = sc.nextInt();
                    machine.refillStorage(new Ingredients(refillAmount,refillAmount,refillAmount,refillAmount,refillAmount,refillAmount));
                    break;  
                case 0:
                    break;
                default:
                    input = sc.nextInt();                                  
            }
            System.out.println("\u001B[31m"+"1. Serve Hot Tea\n2.Serve Hot Coffee\n3.Black Tea\n4.Black Coffee\n5.Green Tea\n6.Refill Machine\n0. Stop Machine"+"\u001B[0m");

        }
        

    }
}
