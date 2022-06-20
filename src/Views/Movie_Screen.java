package Views;

import Controllers.Movie_Controller;
import me.tongfei.progressbar.ProgressBar;
import Helpers.Scrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Movie_Screen extends base {

    Movie_Controller movie_Controller;
    Movie_Screen() {
        super();
        this.movie_Controller = new Movie_Controller();
        RUN();
    }

     void RUN() {
         // Ask the user if he wants to download a single movie or multiple movies
         System.out.println("\n\nPlease Choose one of the following options: ");
         System.out.println("1. Download a single Movie");
         System.out.println("2. Download Multiple Movies");
         System.out.println("3. Return to Main Menu");
         System.out.println("4. Exit");
         System.out.print("Enter your choice: ");
         Scanner scanner = new Scanner(System.in);
         int choice = scanner.nextInt();

         if (choice == 1) {
             this.movie_Controller.DownloadMovie();
             RUN();
         } else if (choice == 2) {
             this.movie_Controller.DownloadMovies();
             RUN();
         } else if (choice == 3) {// Return to Main Menu
             Main_Screen mainScreen = new Main_Screen();
             this.movie_Controller.tearDown();
         } else if (choice == 4) {// Exit
             this.movie_Controller.tearDown();
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
