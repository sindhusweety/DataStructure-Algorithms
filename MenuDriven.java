import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.io.*;

public class MenuDriven {

    private String input;
    private String CURRENT_DIR = System.getProperty("user.dir");
    private Path STORAGE_DIR = Path.of(CURRENT_DIR + "/DB");
    private Path DBLOG = Path.of(STORAGE_DIR + "/dbFiles.txt");
    
    

    static String CREATE = "CREATE";
    static String INSERT = "INSERT";
    static String UPDATE = "UPDATE";
    static String DELETE = "DELETE";
    static String PURGE = "PURGE";

    /*enum Operations{ CREATE, INSERT, UPDATE, DELETE, PURGE }
    protected static EnumMap<Operations, Character> enumOperations(){
            EnumMap<Operations, Character> opObj = new EnumMap<Operations, Character>(Operations.class);
            opObj.put(Operations.CREATE, 'c');
            opObj.put(Operations.INSERT, 'i');
            opObj.put(Operations.UPDATE, 'u');
            opObj.put(Operations.DELETE, 'd');
            opObj.put(Operations.PURGE, 'e');
            return opObj;
    }*/

    public void readerFun(){

        try {

        BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
        this.input = objReader.readLine();
        }
        catch (Exception e){
            System.out.println("-------------------------------------------------");
            System.out.println("*******Something went wrong. Please try Again*****");
            System.out.println("---------------------------------------------------");
            this.homepage();
        }
    }
    protected static HashMap<Character, String> mappingOperations(){
        HashMap<Character, String> opObj = new HashMap<Character, String>();
        opObj.put('c', CREATE);
        opObj.put('i', INSERT);
        opObj.put('u', UPDATE);
        opObj.put('d', DELETE);
        return opObj;
    }

    public void exceptionFun(){
        System.out.println("-------------------------------------------------");
        System.out.println("*******Something went wrong. Please try Again*****");
        System.out.println("---------------------------------------------------");
        this.homepage();
    }

    public void dataLog(){
        try{
            File load_datalog = new File(this.DBLOG.toUri());
            BufferedReader dataset = new BufferedReader(new FileReader(load_datalog));
            System.out.println(dataset.readLine());
            String line;
            int count = 0;
            while ((line = dataset.readLine()) != null){
                if (count == 1){
                    System.out.println(line.split(",")[0]);
                }
                count += 1;

            }


            /*for (String line : (load_datalog.split(","))){
                System.out.println(line + "   hiii");

            }*/


        }
        catch (Exception e){
            this.exceptionFun();
        }
    }

    public void createTable(){
        try {
            System.out.println("Enter table name: ");
            this.readerFun();
            System.out.println(this.input);
            //System.out.println(this.CURRENT_DIR +" "+ this.STORAGE_DIR);

            if (Files.exists(this.STORAGE_DIR) == false){
                Files.createDirectory(this.STORAGE_DIR);
            }
            if (Files.exists(this.DBLOG) == false){
                Files.createFile(this.DBLOG);
                Files.writeString(this.DBLOG, "file name, column name, size ");
            }
            this.dataLog();



        }
        catch (Exception e){
            this.exceptionFun();
        }
    }



    public void homepage(){
        try {
            System.out.println("You are on the menu");
            System.out.println("c - CREATE \t" + "i - INSERT \t" +
                    "u - UPDATE \t" +
                    "d - DELETE \t");
            BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("type anyone of operations to perform  (c/i/u/d) & click ENTER: ");
            String operation = objReader.readLine();
            char op_symbol = operation.toLowerCase().charAt(0);
            HashMap<Character, String> mapOp = mappingOperations();
            if (mapOp.get(op_symbol) == null){
                System.out.println("-------------------------------------------------------");
                System.out.println("**** Chosen Wrong symbol & redirected to Homepage AGAIN *****");
                System.out.println("-------------------------------------------------------");
                this.homepage();

            }
            else{
                System.out.println((mapOp.get(op_symbol)) + " operation has been chosen");
                //System.out.println((Operations.CREATE).getClass().getName());
                if (mapOp.get(op_symbol) == CREATE){
                    System.out.println("hi");
                    this.createTable();

                }
                else if (mapOp.get(op_symbol) == INSERT){
                    System.out.println("hii");

                }
                else if (mapOp.get(op_symbol) == UPDATE){
                    System.out.println("hiii");

                }
                else if (mapOp.get(op_symbol) == DELETE){
                    System.out.println("hiii");

                }
                else if (mapOp.get(op_symbol) == PURGE){
                    System.out.println("hiii");

                }


            }
        }
        catch (Exception e){
            this.exceptionFun();

        }
    }




    public static void main(String[] args){
        MenuDriven menuobj = new MenuDriven();
        menuobj.homepage();

    }
}
