package Views;

import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Main_Screen {

    public Main_Screen() {
        try {
            clearScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        startUp();
    }

    public static void drawArtwork(){

        int width = 100;
        int height = 30;

        BufferedImage bufferedImage = new BufferedImage(
                width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();


        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.drawString("ARABSEED", 12, 24);

        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {

                sb.append(bufferedImage.getRGB(x, y) == -16777216 ? " " : "$");

            }

            if (sb.toString().trim().isEmpty()) {
                continue;
            }

            System.out.println(sb);
        }
        }


    private void startUp(){
        drawArtwork();
        RUN();
    }


    public static void clearScreen() throws IOException, InterruptedException  {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try{
            String operatingSystem = System.getProperty("os.name") ;//Check the current operating system

            if(operatingSystem.contains("Windows")){
                Process pb = new ProcessBuilder("cmd", "/c", "cls").inheritIO().start();
            } else {
                Process pb = new ProcessBuilder("clear").inheritIO().start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void RUN()  {
        System.out.println("\n\nPlease Choose one of the following options: ");
        System.out.println("1. Download a movie");
        System.out.println("2. Download a series");
        System.out.println("3. Download a series episode");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");


        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("\nRedirecting to Download Movie...");
                Movie_Screen movie_Screen = new Movie_Screen();
            }
            case 2 -> {
                System.out.println("\nRedirecting to Download Series...");
                Series_Screen series_Screen = new Series_Screen();
            }
            case 3 -> {
                System.out.println("\nRedirecting to Download Series Episode...");
                Episode_Screen episode_Screen = new Episode_Screen();
            }
            case 4 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> {
                System.out.println("\nvalid Choice!");
                System.out.println("Please Try Again...");
                try {
                    clearScreen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                startUp();
            }
        }
    }
}
