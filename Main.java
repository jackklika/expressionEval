
import java.util.*;
import java.io.*;

import javax.swing.JFileChooser;

public class Main {

    /*
    Takes a character (that SHOULD be a valid operator)
    returns > 0 if an operator, higher numbers mean that operator is
    higher in terms of order of operations
    */
    private static int ordOfOp(char operator) {

        switch (operator) {
            case '+':
                return 0;
            case '-':
                return 0;
            case '*':
                return 1;
            case '/':
                return 1;
            case '<':
                return 2;
            case '>':
                return 2;
            default:
                return -1;
        }
    }


    /*
    Takes a character, and returns true if it is an operator
    */
    private static boolean isOperator(char operator) {

        return ((operator == '+') || (operator == '-') || (operator == '*')
                || (operator == '/') || (operator == '<') || (operator == '>'));
    }

    /*
    Evaluates two-integers expressions
        Takes the left operand, the operator, and the right operand.
        NOTE: The first argument is the RIGHT operand!
    */
    private static int evaluate(int right, char operator, int left) {

        //System.out.println("\tEvaluating [" + left + " " + operator + " " + right + "]");

        switch (operator) {
            case '<':
                return (Math.min(left, right));
            case '>':
                return (Math.max(left, right));
            case '*':
                return (left * right);
            case '/':
                return (left / right);
            case '+':
                return (left + right);
            case '-':
                return (left - right);
        }

        return 0;
    }

    /*
    Takes a string (which should be a valid mathmatical expression) and evaluates it
    */
    public static int expressionEvaluator(String expression) {

        Stack<Character> operator = new Stack();
        Stack<Integer> operand = new Stack();

        char[] inputChars = expression.toCharArray();

        for (char c : inputChars) {

            Character charObj = c; //Declaring this to make getNumericValue easier

            if (c >= '0' && c <= '9') {
                operand.push(Character.getNumericValue(charObj)); // Pushes numbers to operand stack as ints
            } else if (c == '(') {
                operator.push(c);

            } else if (c == ')') {

                while (operator.peek() != '(') {
                    operand.push(evaluate(operand.pop(), operator.pop(), operand.pop()));
                }
                operator.pop();


            } else if (isOperator(c)){

                while (!operator.empty() && !(operator.peek() == '(') && !(ordOfOp(operator.peek()) < ordOfOp(c))){
                    operand.push(evaluate(operand.pop(), operator.pop(), operand.pop()));
                }

                operator.push(c);

            } else {
                System.out.println("Error at character [ " + charObj + " ]");
            }
        }

        while(!operator.empty()){
            operand.push(evaluate(operand.pop(), operator.pop(), operand.pop()));
        }

        return operand.pop();
    }

    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        String fileName;


        if (returnVal == JFileChooser.APPROVE_OPTION){
            fileName = chooser.getSelectedFile().getAbsolutePath();
            try{
                FileReader fReader = new FileReader(fileName);
                BufferedReader reader = new BufferedReader(fReader);

                String line = reader.readLine();
                int x = 0;
                while (line != null){
                    System.out.println(line + " == " + expressionEvaluator(line));
                    line = reader.readLine();
                    x++;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a valid file!");
        }
    }
}
