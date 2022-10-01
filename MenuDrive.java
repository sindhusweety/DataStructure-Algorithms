import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.HashMap;
public class MenuDrive {

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
        protected static HashMap<Character, String> mappingOperations(){
                HashMap<Character, String> opObj = new HashMap<Character, String>();
                opObj.put('c', CREATE);
                opObj.put('i', INSERT);
                opObj.put('u', UPDATE);
                opObj.put('d', DELETE);
                return opObj;
        }



        static void homepage(){
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
                                homepage();

                        }
                        else{
                                System.out.println((mapOp.get(op_symbol)) + " operation has been chosen");
                                //System.out.println((Operations.CREATE).getClass().getName());
                                if (mapOp.get(op_symbol) == CREATE){
                                        System.out.println("hi");

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
                        System.out.println(e);
                        e.printStackTrace();
                }
        }
        }
