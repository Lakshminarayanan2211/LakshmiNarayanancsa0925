import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<String> book = new ArrayList<String>();
        ArrayList<Integer> cost = new ArrayList<Integer>();
        book.add("Java");
        cost.add(500);
        book.add("Python");
        cost.add(450);
        book.add("Harry Potter");
        cost.add(300);
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter book name: ");
        String name = sc.nextLine();
        boolean found = false;
        for (int i = 0; i < book.size(); i++) {
            if (book.get(i).equalsIgnoreCase(name)) {
                System.out.println("Book is available");
                System.out.println("Cost = " + cost.get(i));
                found = true;
                break;
            }
        }

        if (found == false) {
            System.out.println("Book not available");
        }
    }
}