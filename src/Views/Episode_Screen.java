package Views;

import java.util.Scanner;

public class Episode_Screen extends base {

    Episode_Screen() {
        super();
        RUN();
    }

     private void RUN() {
        // Ask the user if he wants to download a single movie or multiple movies
        System.out.println("\n\nPlease Choose one of the following options: ");
        System.out.println("1. Download a single Episode");
        System.out.println("2. Download Multiple Episodes");
        System.out.println("3. Return to Main Menu");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
    }

}
