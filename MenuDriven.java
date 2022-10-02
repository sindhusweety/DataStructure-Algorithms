import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
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
    private Path TABLENAME;



    static String CREATE = "CREATE";
    static String INSERT = "INSERT";
    static String UPDATE = "UPDATE";
    static String DELETE = "DELETE";
    static String PURGE = "PURGE";

    Dictionary dictionary = new Hashtable();
    Hashtable<String, ArrayList<ArrayList>> dataFileObj = new Hashtable<String, ArrayList<ArrayList>>();


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

    public void genericRead(){

    }

    public void dataLog(){
        try{

            BufferedReader new_data = new BufferedReader(Files.newBufferedReader(this.DBLOG));
            String line;
            int count = 0;
            while ((line = new_data.readLine()) != null){
                if (count >= 1){
                    String[] l = line.split(",");
                    String fname = l[0].strip();
                    String cname = l[1].strip();
                    String size = l[2].strip();
                    //this.file_name.add(fname);
                    //this.column_name.add(cname);
                    //this.size.add(size);

                    if (this.dataFileObj.containsKey(fname)){

                        this.dataFileObj.get(fname).get(0).add(cname);
                        this.dataFileObj.get(fname).get(1).add(size);

                    }
                    else{
                        ArrayList<String> lcname = new ArrayList<String>();
                        lcname.add(cname);
                        ArrayList<String> lsize = new ArrayList<String>();
                        lsize.add(size);
                        this.dataFileObj.put(fname, new ArrayList<>());
                        this.dataFileObj.get(fname).add(lcname);
                        this.dataFileObj.get(fname).add(lsize);
                        //System.out.println(this.dataFileObj);

                    }

                }
                count += 1;
            }
        }
        catch (Exception e){
            this.exceptionFun();
        }
    }

    public void createTable(){
        try {
            System.out.println("Enter table name: ");
            this.readerFun();

            //System.out.println(this.CURRENT_DIR +" "+ this.STORAGE_DIR);

            if (Files.exists(this.STORAGE_DIR) == false){
                Files.createDirectory(this.STORAGE_DIR);
            }
            if (Files.exists(this.DBLOG) == false){
                Files.createFile(this.DBLOG);
                Files.writeString(this.DBLOG, "file name, column name, size ");
            }
            this.dataLog();

            if (this.input.isEmpty() == false){
                if ((this.dataFileObj.containsKey(this.input)) == false){
                    String tname = this.input;
                    this.TABLENAME =  Path.of(this.STORAGE_DIR + "/" + this.input + ".db");
                    Files.createFile(this.TABLENAME);
                    while (this.input.isEmpty() == false){
                        System.out.println("Enter column name and its length:");
                        this.readerFun();
                        if (this.input.isEmpty() == false){
                            String[] splnc = this.input.split(" ");
                            ArrayList<String> colname = new ArrayList<>();
                            ArrayList<String> sze = new ArrayList<>();

                            colname.add(splnc[0].strip());
                            sze.add(splnc[1].strip());

                            if (this.dataFileObj.containsKey(tname)){
                                this.dataFileObj.get(tname).get(0).add(splnc[0].strip());
                                this.dataFileObj.get(tname).get(1).add(splnc[1].strip());
                            }
                            else{
                                this.dataFileObj.put(tname, new ArrayList<>());
                                this.dataFileObj.get(tname).add(colname);
                                this.dataFileObj.get(tname).add(sze);
                            }


                        }
                    }
                    if (this.input.isEmpty()){
                        System.out.println("Table is created Successfully");
                        System.out.println(this.dataFileObj);
                        this.writeDBRec();

                    }

                }
                else{
                    System.out.println("Table name already exists. So, Please try AGAIN ");
                    this.createTable();
                }
            }
            else {
                System.out.println("You have entered invalid table name. So, Please try AGAIN ");
                this.createTable();
            }


        }
        catch (Exception e){
            this.exceptionFun();
        }
    }

    public void writeDBRec()  {
        try{

            FileWriter bw = new FileWriter(this.DBLOG.toFile());
            FileWriter tb = new FileWriter(this.TABLENAME.toFile());
            String newLine = System.getProperty("line.separator");
            String tbcolumns ="";
            String inputstream = "file name, column name, size"+newLine;
            bw.write(inputstream);
            for (String key : this.dataFileObj.keySet() ){
                ArrayList<ArrayList> v1 = this.dataFileObj.get(key).get(0);
                ArrayList<ArrayList> v2 = this.dataFileObj.get(key).get(1);
                for (int i = 0; i < (v1.size()); i++){
                    //System.out.println(key + "  "+ v1.get(i)+"  "+ v2.get(i));
                    inputstream = key + ", "+ v1.get(i)+", "+ v2.get(i)+newLine;
                    tbcolumns += String.valueOf(v1.get(i))+",";
                    System.out.println(inputstream);
                    bw.write(inputstream);

                }
            }
            bw.close();
            tb.write(tbcolumns+newLine);
            tb.close();
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
