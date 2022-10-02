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

    FileWriter tb;



    static String CREATE = "CREATE";
    static String INSERT = "INSERT";
    static String PRINTFILE = "PRINTFILE";
    static String REMOVE = "REMOVE";
    static String EXIT = "EXIT";

    private Integer MAX = 0;
    private Integer NO_COLS = 0;
    ArrayList<String> tb_columns = new ArrayList<String>();
    ArrayList<ArrayList> tableRecords = new ArrayList<ArrayList>();

    Dictionary dictionary = new Hashtable();
    Hashtable<String, ArrayList<ArrayList>> dataFileObj = new Hashtable<String, ArrayList<ArrayList>>();


    public void readerFun(){

        try {
        BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
        this.input = objReader.readLine();
        }
        catch (Exception e){
            this.exceptionFun();
        }
    }
    protected static HashMap<Character, String> mappingOperations(){
        HashMap<Character, String> opObj = new HashMap<Character, String>();
        opObj.put('c', CREATE);
        opObj.put('i', INSERT);
        opObj.put('p', PRINTFILE);
        opObj.put('r', REMOVE);
        opObj.put('e', EXIT);
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

            BufferedReader new_data = new BufferedReader(Files.newBufferedReader(this.DBLOG));
            String line;
            int count = 0;
            while ((line = new_data.readLine()) != null){
                if (count >= 1){
                    String[] l = line.split(",");
                    String fname = l[0].strip();
                    String cname = l[1].strip();
                    String size = l[2].strip();

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
    //---------------------------------------------------------------------------------------------------
    //------------------------------CREATION------------------------------------------------------------
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
                Files.writeString(this.DBLOG, "file name, column name, size"+'\n');
            }
            this.dataLog();

            if (this.input.strip().isEmpty() == false){
                if ((this.dataFileObj.containsKey(this.input)) == false){
                    String tname = this.input.strip();
                    this.TABLENAME =  Path.of(this.STORAGE_DIR + "/" + this.input.strip() + ".db");
                    Files.createFile(this.TABLENAME);
                    while (this.input.strip().isEmpty() == false){
                        System.out.println("Enter column name and its length:");
                        this.readerFun();

                        while (this.tb_columns.contains(this.input.split(" ")[0].strip())){
                            System.out.println("------------------------------------------------------------");
                            System.out.println("Column name already exists. Please try Again...");
                            System.out.println("------------------------------------------------------------");
                            System.out.println("Enter column name and its length:");
                            this.readerFun();

                        }
                        if (this.input.strip().isEmpty() == false){
                            String[] splnc = this.input.split(" ");

                            ArrayList<String> colname = new ArrayList<>();
                            ArrayList<String> sze = new ArrayList<>();


                            colname.add(splnc[0].strip());
                            sze.add(splnc[1].strip());

                            if (this.dataFileObj.containsKey(tname)){
                                this.tb_columns.add(splnc[0].strip());
                                this.dataFileObj.get(tname).get(0).add(splnc[0].strip());
                                this.dataFileObj.get(tname).get(1).add(splnc[1].strip());
                            }
                            else{
                                this.dataFileObj.put(tname, new ArrayList<>());
                                this.tb_columns.add(splnc[0].strip());
                                this.dataFileObj.get(tname).add(colname);
                                this.dataFileObj.get(tname).add(sze);
                            }


                        }
                    }
                    if (this.input.isEmpty()){
                        System.out.println("------------------------------------------------------------");
                        System.out.println(this.dataFileObj);
                        this.writeDBRec();
                        this.tableColumnsWriter();
                        System.out.println("************Table is created Successfully********************");
                        System.out.println("------------------------------------------------------------");
                        //homepage();

                        System.out.println("If you would like to create more tables, Type 'Yes' or 'No' (y/n):");
                        this.readerFun();
                        if (this.input.toLowerCase().startsWith("y")){
                            System.out.println("------------------------------------------------------------");
                            this.createTable();
                        }
                        else{
                            System.out.println("------------------------------------------------------------");
                            System.out.println("Redirecting to Home Page..");
                            System.out.println("------------------------------------------------------------");
                            System.out.println("------------------------------------------------------------");

                            this.homepage();
                        }

                    }

                }
                else{
                    System.out.println("------------------------------------------------------------");
                    System.out.println("Table name already exists. So, Please try AGAIN ");
                    System.out.println("------------------------------------------------------------");
                    this.createTable();
                }
            }
            else {
                this.inValidTableNameMsg();
                this.createTable();
            }


        }
        catch (Exception e){
            this.exceptionFun();
        }
    }
    //---------------------------------------------------------------------------------------------------------

    public void writeDBRec()  {
        try{

            FileWriter bw = new FileWriter(this.DBLOG.toFile());

            String newLine = System.getProperty("line.separator");

            String inputstream ="file name, column name, size"+ newLine;
            bw.write(inputstream);

            for (String key : this.dataFileObj.keySet() ){
                ArrayList<ArrayList> v1 = this.dataFileObj.get(key).get(0);
                ArrayList<ArrayList> v2 = this.dataFileObj.get(key).get(1);
                for (int i = 0; i < (v1.size()); i++){
                    //System.out.println(key + "  "+ v1.get(i)+"  "+ v2.get(i));
                    inputstream =key + ", "+ v1.get(i)+", "+ v2.get(i)+newLine;
                    bw.write(inputstream);

                }
            }
            bw.close();
            this.dataFileObj.clear(); //important clear

        }
        catch (Exception e){
            this.exceptionFun();
        }



    }
    public void tableColumnsWriter(){

        try {
            tb = new FileWriter(this.TABLENAME.toFile());
            String newLine = System.getProperty("line.separator");
            String tbcolumns = this.tb_columns.get(0);

            for (int i = 1; i<this.tb_columns.size(); i++){
                tbcolumns +=", " + this.tb_columns.get(i);
            }
            tbcolumns = tbcolumns+newLine;

            System.out.println(tbcolumns);
            tb.write(tbcolumns);
            tb.close();
            this.tb_columns.clear();

        }
        catch (Exception e){
            this.exceptionFun();
        }

    }
    public void tableRecordWriter(){
        try {

            BufferedReader new_data = new BufferedReader(Files.newBufferedReader(this.TABLENAME));
            String line;
            while ((line = new_data.readLine())  != null){
                String[] l = line.split(",");
                ArrayList<String> al = new ArrayList<String>();
                for (int i = 0; i < l.length; i++){
                    al.add(l[i]);
                }
                this.tableRecords.add(al);

            }
            this.tableRecords.add(this.tb_columns);

            tb = new FileWriter(this.TABLENAME.toFile());
            String newLine = System.getProperty("line.separator");
            String tbcolumns = "";

            for (int i = 0; i<this.tableRecords.size(); i++){
                String subtbcolumns = String.valueOf( this.tableRecords.get(i).get(0));
                for (int x = 1;  x <  this.tableRecords.get(i).size(); x++ ){
                    subtbcolumns += ", "+this.tableRecords.get(i).get(x);
                }
                tbcolumns = subtbcolumns+newLine ;
                tb.write(tbcolumns);
            }

            tb.close();
            this.tb_columns.clear();

        }
        catch (Exception e){
            this.exceptionFun();
        }
    }
    //-----------------------------------------------------------------------------------------
    //---------------------------INSERTION------------------------------------------------------
    public void insertTable(){
        try{

            System.out.println("-----------------------------------------------------------------");
            System.out.println("Choose Table name:");
            if (this.dataFileObj.isEmpty()){
                this.dataLog();
            }
            this.readerFun();

            if (this.input.strip().isEmpty() == false){
                if (this.dataFileObj.containsKey(this.input.strip())){

                    this.TABLENAME =  Path.of(this.STORAGE_DIR + "/" + this.input.strip() + ".db");

                    ArrayList<ArrayList> value = this.dataFileObj.get(this.input.strip());
                    for (int i=0; i< value.get(0).size(); i++){

                        String cols = String.valueOf(value.get(0).get(i));
                        Integer sze = Integer.valueOf((String) value.get(1).get(i));
                        System.out.println("-----------------------------------------------------------------");
                        System.out.println("Enter "+value.get(0).get(i)+":");

                        this.readerFun();
                        Integer  input_size = this.input.strip().length();

                        while (input_size > sze){
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println("Entered value's length is larger. So, Please try again");
                            System.out.println("The width of this particular column("+cols+") is :"+value.get(1).get(i));
                            System.out.println("Enter "+cols+":");
                            System.out.println("-----------------------------------------------------------------");
                            this.readerFun();
                            input_size = this.input.strip().length();

                        }

                        this.tb_columns.add(this.input.strip());
                    }

                    System.out.println("------------------------------------------------------------");

                    this.tableRecordWriter();
                    System.out.println("************Record is inserted Successfully********************");
                    System.out.println("------------------------------------------------------------");
                    System.out.println("If you would like to create more tables, Type 'Yes' or 'No' (y/n):");
                    this.readerFun();
                    if (this.input.toLowerCase().startsWith("y")){
                        System.out.println("------------------------------------------------------------");
                        this.insertTable();
                    }
                    else{
                        System.out.println("------------------------------------------------------------");
                        System.out.println("Redirecting to Home Page..");
                        System.out.println("------------------------------------------------------------");
                        System.out.println("------------------------------------------------------------");

                        this.homepage();
                    }
                }
                else{
                    System.out.println("------------------------------------------------------------");
                    System.out.println("Table name doesn't exist. So, Please try AGAIN ");
                    System.out.println("------------------------------------------------------------");
                    this.insertTable();
                }

            }
            else{
                this.inValidTableNameMsg();
                this.insertTable();
            }

        }
        catch (Exception e){
            this.exceptionFun();
        }

    }

    public void inValidTableNameMsg(){
        System.out.println("------------------------------------------------------------");
        System.out.println("You have entered invalid table name. So, Please try AGAIN ");
        System.out.println("------------------------------------------------------------");
    }
    public String eachRec(ArrayList<String> rec){
        String record = "|";

        for (String cell : rec){
            int no_char = cell.strip().length();
            String spaces = " ";
            int count = 0;
            while (count < (this.MAX-no_char)){
                spaces += " ";
                count += 1;
            }
            record += cell.strip()+ spaces+"|";
            System.out.println(cell);
        }

        return record;
    }
    public String dashingFun(){
        String dashes = "";
        int count = 0;
        while (count < ((this.MAX * this.NO_COLS)+this.NO_COLS+2)){
            dashes += "_";
            count += 1;
        }
        System.out.println(dashes);
        return dashes;
    }
    public void displayTable(){
        try{
            String dashes = dashingFun();
            for (int i=0; i < this.tableRecords.size(); i++){
                if (i == 0){
                    System.out.println(dashes);
                    String record = this.eachRec(this.tableRecords.get(0));
                    System.out.println(record);
                    System.out.println(dashes);
                }
                else{
                    System.out.println(this.tableRecords.get(i));
                    System.out.println(dashes);
                }

            }
        }
        catch (Exception e){
            this.exceptionFun();
        }
    }
    public void loadTableRec(){
        try{

            BufferedReader new_data = new BufferedReader(Files.newBufferedReader(this.TABLENAME));
            String line;
            while ((line = new_data.readLine())  != null) {
                String[] l = line.split(",");
                ArrayList<String> al = new ArrayList<String>();
                for (int i = 0; i < l.length; i++) {
                    al.add(l[i]);
                }
                this.tableRecords.add(al);
            }

        }
        catch (Exception e){
            this.exceptionFun();
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    public void printFile(){
        try{
            System.out.println("Enter All to disaply all tables or Enter table name to display: ");
            this.readerFun();
            this.dataLog();

            if (this.input.strip().isEmpty() == false){
                if (this.dataFileObj.containsKey(this.input.strip())){

                    this.TABLENAME =  Path.of(this.STORAGE_DIR + "/" + this.input.strip() + ".db");

                    ArrayList<ArrayList> value = this.dataFileObj.get(this.input.strip());
                    for (int i=0; i< value.get(0).size(); i++) {

                        String cols = String.valueOf(value.get(0).get(i));
                        Integer sze = Integer.valueOf((String) value.get(1).get(i));
                        this.MAX = Math.max(sze, this.MAX);
                        this.NO_COLS += 1;
                    }
                    this.loadTableRec();
                    System.out.println("HIIIII"+ this.NO_COLS);
                    this.displayTable();

                }
                else if (this.input.strip().toLowerCase() == "all"){

                }else{
                    this.inValidTableNameMsg();
                    this.printFile();

                }

            }
            else{

                this.inValidTableNameMsg();
                this.printFile();

            }

        }
        catch (Exception e){
            this.exceptionFun();
        }
    }


    //------------------------------------------------------------------------------------------
    public void homepage(){
        try {
            System.out.println("You are on the menu");
            System.out.println("c - CREATE \t" + "i - INSERT \t" +
                    "p - PRINTFILE \t" +
                    "r - REMOVE \t" +
                    "e - EXIT");
            BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("type anyone of operations to perform  (c/i/p/r/e) & click ENTER: ");
            String operation = objReader.readLine();
            char op_symbol = operation.strip().toLowerCase().charAt(0);
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
                    this.insertTable();

                }
            else if (mapOp.get(op_symbol) == PRINTFILE){
                    this.printFile();

                }
                else if (mapOp.get(op_symbol) == REMOVE){
                    System.out.println("hiii");

                }
                else if (mapOp.get(op_symbol) == EXIT){
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
