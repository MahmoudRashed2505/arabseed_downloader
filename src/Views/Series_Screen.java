package Views;

import Controllers.Series_Controller;

import java.util.Scanner;

public class Series_Screen extends base {

    Series_Controller series_controller;
    Series_Screen() {
        super();
        this.series_controller = new Series_Controller();
        RUN();
    }

     private void RUN() {
        // Ask the user if he wants to download a single movie or multiple movies
        System.out.println("\n\nPlease Choose one of the following options: ");
        System.out.println("1. Download a single Series");
        System.out.println("2. Download Multiple Series");
        System.out.println("3. Return to Main Menu");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

         if (choice == 1) {
             this.series_controller.DownloadSeries();
             RUN();
         } else if (choice == 2) {
             this.series_controller.DownloadMultipleSeries();
             RUN();
         } else if (choice == 3) {// Return to Main Menu
             Main_Screen mainScreen = new Main_Screen();
             this.series_controller.tearDown();
         } else if (choice == 4) {// Exit
             this.series_controller.tearDown();
             System.exit(0);
         } else {
             System.out.println("\n\nPlease enter a valid choice");
             try {
                 Thread.sleep(2000);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
             RUN();
         }
    }
}
