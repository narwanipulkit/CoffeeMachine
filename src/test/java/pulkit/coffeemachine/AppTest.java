package pulkit.coffeemachine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.google.gson.Gson;
import org.junit.Test;


public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testServePossible() throws FileNotFoundException
    {
        String dir = System.getProperty("user.dir");        
        File f = new File(dir +"/src/test/json/positive");
        for(File testJson : f.listFiles()){

            Scanner sc = new Scanner(testJson);
            StringBuilder s = new StringBuilder();
            while(sc.hasNextLine()){
                 s.append(sc.nextLine());
            }
            String json = s.toString();
            Gson gson = new Gson();
            CoffeeMachineDetails details = gson.fromJson(json,CoffeeMachineDetails.class);
            CoffeeMachine machine = new CoffeeMachine(details);
            assertTrue(machine.serve(BeverageType.HOT_TEA));
        }
        
    }

    @Test
    public void testServeNegative() throws FileNotFoundException
    {
        String dir = System.getProperty("user.dir");        
        File f = new File(dir +"/src/test/json/negative");
        for(File testJson : f.listFiles()){

            Scanner sc = new Scanner(testJson);
            StringBuilder s = new StringBuilder();
            while(sc.hasNextLine()){
                 s.append(sc.nextLine());
            }
            String json = s.toString();
            Gson gson = new Gson();
            CoffeeMachineDetails details = gson.fromJson(json,CoffeeMachineDetails.class);
            CoffeeMachine machine = new CoffeeMachine(details);
            
            assertFalse(machine.serve(BeverageType.HOT_TEA));
        }
        
    }

    @Test
    public void testServeMultiple() throws FileNotFoundException
    {
        String dir = System.getProperty("user.dir");        
        File f = new File(dir +"/src/test/json/positive/sample2.json");
        Scanner sc = new Scanner(f);
        StringBuilder s = new StringBuilder();
        while(sc.hasNextLine()){
                s.append(sc.nextLine());
        }
        String json = s.toString();
        Gson gson = new Gson();
        CoffeeMachineDetails details = gson.fromJson(json,CoffeeMachineDetails.class);
        CoffeeMachine machine = new CoffeeMachine(details);
        assertTrue(machine.serve(BeverageType.HOT_TEA));
        assertTrue(machine.serve(BeverageType.HOT_TEA));
    }    
}
