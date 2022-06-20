package Controllers;

import Helpers.Scrapper;
import Views.Main_Screen;
import me.tongfei.progressbar.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Movie_Controller {

    Scrapper scrapper;
    Scanner scanner;

    public Movie_Controller(){
        this.scrapper = new Scrapper();
        this.scanner = new Scanner(System.in);
    }


    public void tearDown(){
        this.scrapper.tearDown();
        this.scanner.close();
    }

    public void DownloadMovies() {

        System.out.println("\n\nPlease enter the text file path: ");

        String filePath = this.scanner.nextLine();
        List<String> urls = new ArrayList<String>();
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                urls.add(line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        try {
            Main_Screen.clearScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (String url : ProgressBar.wrap(urls, "Downloading Movies")) {
            this.scrapper.downloadMovie(url);
        }
    }

    public void DownloadMovie() {
        System.out.println("\n\nPlease enter the Movie URL: ");
        String url = this.scanner.nextLine();
        this.scrapper.downloadMovie(url);
    }
}
