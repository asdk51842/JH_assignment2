/*
Algorithm:
1. If prefix string is a single variable, it's its own postfix equivalent
2. Let op = first operator of the prefix string
3. Find first operand opnd1 of the string. Convert it to postfix and call it post1
4. Find second operand opnd2 of the string. Convert it to postfix and call it post2
5. Concatenate post1, post2, and op
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[ ] args) throws IOException{
        InputStream is = null; 
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileWriter myWriter = new FileWriter("output.txt");

        try{
            // open input stream test.txt for reading purpose.
            is = new FileInputStream("D:/Downloads/input_lab2.txt");
            // create new input stream reader
            isr = new InputStreamReader(is);
            // create new buffered reader
            br = new BufferedReader(isr);
            String str = "";

            while((str = br.readLine()) != null){
                Prefix prefix = new Prefix(str);
                Postfix postfix = new Postfix("");
                //isPrefix(prefix.getPrefix());
                convert(prefix, postfix);
                System.out.println(postfix.getPostfix());
            }

            
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            myWriter.close();
        }
    }

    public static boolean isOperator(char x){
        if((x == '+') || (x == '-') ||(x == '*') ||(x == '/') ||(x == '$')){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isOperand(char x){
        if((x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z')){
            return true;
        }
        else{
            return false;
        }
    }

    static class Prefix{
        String prefix;
        public Prefix(String n){
            this.prefix = n;
        }

        public String getPrefix() {
            return prefix;
        }
        public void setPrefix(String prefix){
            this.prefix = prefix;
        }
        public void subString(String prefix){
            this.prefix = prefix.substring(1);
        }
    }

    static  class Postfix{
        private String postfix;
        public Postfix(String n){
            this.postfix = n;
        }
        public String getPostfix(){
            return postfix;
        }
        public void setPostfix(String postfix){
            this.postfix = postfix;
        }

        public void addChar(Postfix postfix, char add) {
            this.postfix = postfix.getPostfix() + add;
        }
    }

    // //if it's an operand, it's a prefix
    // //if it's an operator, has to be followed by 2 prefix expressions
    // public static boolean isPrefix(String prefix){
    //     int length;
    //     length = prefix.length();
    //     if(length ==1){
    //         if (isOperand(prefix.charAt(0))){
    //             return true;
    //         }
    //         else{
    //             System.out.println("Not a valid prefix string!");
    //             System.exit(1);

    //         }
    //     }
    //     else{
    //         char op;
    //         op = prefix.charAt(0);
    //         if (!isOperator(op)){
    //             System.out.println("Not a valid prefix string!");
    //             System.exit(1);
    //         }
    //     }
    //     return true;

    // }
    public static int find(String str){
        String temp;
        int m, n, length;
        length = str.length();
        if((length == 0)){
            return 0;
        }
        if (Character.isLetter((str.charAt(0)))){ // if first letter is a character,
            return 1;
        }
        if (length <2){
            return 0;
        }
        //find first operand
        temp = str.substring(1, length);
        m = find(temp);
        if (m == 0 || str.length() == m){
            return 0; //no valid prefix operand or no second operand
        }
        temp = str.substring(m+1, length);
        n = find(temp);
        if (n == 0){
            return 0;
        }
        return m + n +1;
    }


    public static boolean isPrefix(String prefix){
        int length;
        String temp;
        int m, n;
        length = prefix.length();
        if(length ==1){
            if (isOperand(prefix.charAt(0))){
                return true;
            }
            else{
                System.out.println("Not a valid prefix string!");
                System.exit(1);

            }
        }
        else{
            char op;
            op = prefix.charAt(0);
            temp = new String(prefix.substring(1,length));
            m = find(temp);
            temp = new String (prefix.substring(m+1,length));
            n = find(temp);
            if (!isOperator(op)||(m==0)||(n==0)||(m+n+1 !=length)){
                System.out.println("Not a valid prefix string!");
                System.exit(1);
            }
        }
        return true;

    }

    public static void convert(Prefix prefix, Postfix postfix){
        char char1 = prefix.getPrefix().charAt(0);
        prefix.subString(prefix.getPrefix());
        if (isOperand(char1)){
            postfix.addChar(postfix, char1);
        }
        else{
            convert(prefix, postfix);
            convert(prefix, postfix);
            postfix.addChar(postfix, char1);
        }
    }
}
