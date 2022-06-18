//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Controllers;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Scrapper {
    WebDriver driver;

    public Scrapper() {
        this.setupDriver();
    }

    void setupDriver() {
        System.out.println("Setting up Driver with Options...");
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap();
        prefs.put("profile.managed_default_content_settings.javascript", 2);
        options.setExperimentalOption("prefs", prefs);
        this.driver = new ChromeDriver(options);
    }

    void tearDown() {
        if (this.driver != null) {
            this.driver.quit();
        }

    }

    public void getSeriesPage(String url) {

        this.driver.get(url);
        System.out.println("Getting all episodes...");
        WebElement EpisodeContainer = this.driver.findElement(By.className("ContainerEpisodesList"));
        System.out.println(EpisodeContainer);
        List<WebElement> Episodes = EpisodeContainer.findElements(By.tagName("a"));
        System.out.println("Found " + Episodes.size() + " episodes.");
        Iterator var4 = Episodes.iterator();

        while(var4.hasNext()) {
            WebElement Episode = (WebElement)var4.next();
            System.out.println(Episode.getText());
        }

        Map<String, String> EpisodesMap = new HashMap();
        Iterator var10 = Episodes.iterator();

        while(var10.hasNext()) {
            WebElement Episode = (WebElement)var10.next();
            String EpisodeNumber = Episode.findElement(By.tagName("em")).getText();
            String EpisodeLink = Episode.getAttribute("href");
            EpisodesMap.put(EpisodeNumber, EpisodeLink);
        }

        System.out.println(EpisodesMap);
    }

    void getDownloadBLock() {
        System.out.println("Getting Download Block...");
        List<WebElement> downloadBlocks = this.driver.findElements(By.className("DownloadBlock"));
        System.out.println("Found " + downloadBlocks.size() + " download blocks.");
        Iterator var2 = downloadBlocks.iterator();

        while(var2.hasNext()) {
            WebElement downloadBlock = (WebElement)var2.next();
            WebElement TitleCenteral = downloadBlock.findElement(By.className("TitleCenteral"));
            System.out.println(TitleCenteral.findElement(By.tagName("span")).getText());
        }

    }

    void getDirectDownloadPage(String url) {
        this.driver.get(url);
    }

    static {
        System.out.println("Setting up Driver...");
        WebDriverManager.chromedriver().setup();
    }
}
