package Views;

import java.io.IOException;

import static Views.Main_Screen.drawArtwork;

public class base {

    public void base(){
        try {
            Main_Screen.clearScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Main_Screen.drawArtwork();
        drawArtwork();
    };
}
