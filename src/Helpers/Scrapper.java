//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Helpers;

import Models.Movie;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.util.*;

import me.tongfei.progressbar.ProgressBar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Scrapper {
    WebDriver driver;
    Scanner scanner;
    static {
        System.out.println("Setting up Driver...");
        WebDriverManager.chromedriver().setup();
    }

    public Scrapper() {
        this.scanner = new Scanner(System.in);
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

    public void tearDown() {
        if (this.driver != null) {
            this.driver.quit();
        }

    }

    public void downloadSeries(String url){
        this.driver.get(url);
        String seriesName = "series name";
        try {
            seriesName = this.driver.findElement(By.xpath("/html/body/ajaxpage/div[1]/div[2]/div[1]/div[1]/ol/li[4]/a/span")).getText();
        } catch (Exception e) {
            System.out.println("Sorry Error occurred while getting series name");
        }
        Map<String,String> episodeList = getEpisodes();
        System.out.println("Enter Series Download Path: ");
        String location = this.scanner.nextLine();
        chooseEpisodes(episodeList,location+'/');
    }

    private void chooseEpisodes(Map<String,String> episodeList,String location){
        List<Integer> episodeNumbers = new ArrayList<Integer>();

        for(String key: episodeList.keySet()){
            episodeNumbers.add(Integer.parseInt(key));
        }

        Collections.sort(episodeNumbers);

        System.out.println("Found Episodes from Episode "+episodeNumbers.get(0)+
                " to Episode number "+
                episodeNumbers.get(episodeNumbers.size()-1));

        System.out.println("Choose the Episodes that you want to download separated by ','\n"+
                "Or Enter a range 'start number:end number' or enter 0 to download all");

        System.out.print("Enter your choice: ");

        String userInput = this.scanner.nextLine();
        boolean validateInput = false;
        if(userInput.contains(":")){
            validateInput = checkRange(userInput,
                    episodeNumbers.get(0),
                    episodeNumbers.get(episodeNumbers.size()-1));
        } else if (userInput.contains(",")){
            validateInput = checkListOfEpisodes(userInput,
                    episodeNumbers.get(0),
                    episodeNumbers.get(episodeNumbers.size()-1));
        } else {
            try{
                int currentNumber = Integer.parseInt(userInput);
                if (currentNumber!=0){
                    System.out.println("Condition Value for download all: "
                            + String.valueOf(currentNumber!=0));

                    return;
                }
                validateInput =true;
            } catch (Exception e){
                System.out.println("Invalid Input returning....");
                return;
            }
        }
        if(!validateInput){
            return;
        } else {
            if(userInput.contains(":")){
                downloadRange(episodeList,userInput,location);
            } else if(userInput.contains(",")){
                downloadList(episodeList,userInput,location);
            } else if(Integer.parseInt(userInput) == 0){
                downloadAll(episodeList,location);
            }
        }
    }

    private void downloadRange(Map<String,String> episodeList, String userInput,String location){
        String[] rawData = userInput.split(":");
        int startEpisode = Integer.parseInt(rawData[0]);
        int endEpisode = Integer.parseInt(rawData[1]);
        String quality = getQuality(episodeList);
        for(int i=startEpisode;i<endEpisode+1;i++){
            System.out.println("Downloading Episode Number ["+ i +"]");
            String episodeUrl = episodeList.get(String.valueOf(i));
            this.driver.get(episodeUrl);
            this.driver.findElement(By.className("downloadBTn")).click();
            Map<String,String> downLoadBlocks = getDownloadBLock();
            String directDownloadLink = getDirectDownloadLink(downLoadBlocks.get(quality));
            Download(directDownloadLink,location);
        }
    }

    private void downloadList(Map<String, String> episodeList, String userInput,String location){
        String[] requiredEpisodes = userInput.split(",");
        String quality = getQuality(episodeList);
        for(String episode:requiredEpisodes){
            String episodeUrl = episodeList.get(episode);
            this.driver.get(episodeUrl);
            this.driver.findElement(By.className("downloadBTn")).click();
            Map<String,String> downLoadBlocks = getDownloadBLock();
            String directDownloadLink = getDirectDownloadLink(downLoadBlocks.get(quality));
            Download(directDownloadLink,location);
        }
    }

    private void downloadAll(Map<String,String> episodeList, String location){
        String quality = getQuality(episodeList);
        for(String key: ProgressBar.wrap(episodeList.keySet(),"Downloading Episodes")){
            String episodeUrl = episodeList.get(key);
            this.driver.get(episodeUrl);
            this.driver.findElement(By.className("downloadBTn")).click();
            Map<String,String> downLoadBlocks = getDownloadBLock();
            String directDownloadLink = getDirectDownloadLink(downLoadBlocks.get(quality));
            Download(directDownloadLink,location);
        }
    }

    private String getQuality(Map<String,String> episodeList){
        String qualityKey = "";
        Map<String,String> rawQualities = new HashMap<>();
        for(String key:episodeList.keySet()){
            this.driver.get(episodeList.get(key));
            this.driver.findElement(By.className("downloadBTn")).click();
            rawQualities = getDownloadBLock();
            break;
        }
        Map<Integer,String> modifiedQualities = new HashMap<>();
        int i = 1;
        for(String key:rawQualities.keySet()){
            modifiedQualities.put(i,key);
            i++;
        }
        System.out.println("\nChoose the desired quality: \n");
        for(Map.Entry<Integer,String> entry : modifiedQualities.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the quality number: ");
        int choice = scanner.nextInt();
        qualityKey = modifiedQualities.get(choice);

        return  qualityKey;
    }

    private boolean checkRange(String userInput, int start, int end){

        String[] splitValue=userInput.split(":");
        try{
            if(splitValue.length>2){
                return false;
            }
            else {
                int userStart = Integer.parseInt(splitValue[0]);
                int userEnd = Integer.parseInt(splitValue[1]);
                if(userStart<start || userStart>end || userEnd>end || userEnd<start){
                    return false;
                }
            }
        }catch(Exception e){
            return false;
        }

        return true;
    }

    private boolean checkListOfEpisodes(String userInput, int start, int end){
        String[] episodeNumbers = userInput.split(",");
        int currentNumber = -10;

        for(String number:episodeNumbers){
            try{
                currentNumber = Integer.parseInt(number);
                if (currentNumber>end || currentNumber<start){
                    return false;
                }
            } catch (Exception e){
                return false;
            }
        }

        return true;
    }

    private Map<String,String> getEpisodes() {

        System.out.println("Getting all episodes...");
        WebElement EpisodeContainer = this.driver.findElement(By.className("ContainerEpisodesList"));
        List<WebElement> Episodes = EpisodeContainer.findElements(By.tagName("a"));
        System.out.println("Found " + Episodes.size() + " episodes.");
        Iterator var4 = Episodes.iterator();

        while(var4.hasNext()) {
            WebElement Episode = (WebElement)var4.next();
        }

        Map<String, String> EpisodesMap = new HashMap();
        Iterator var10 = Episodes.iterator();

        while(var10.hasNext()) {
            WebElement Episode = (WebElement)var10.next();
            String EpisodeNumber = Episode.findElement(By.tagName("em")).getText();
            String EpisodeLink = Episode.getAttribute("href");
            EpisodesMap.put(EpisodeNumber, EpisodeLink);
        }

        return EpisodesMap;
    }

    Map<String,String> getDownloadBLock() {
        List<WebElement> downloadBlocks = this.driver.findElements(By.className("DownloadBlock"));
        Iterator var2 = downloadBlocks.iterator();

        Map<String, String> downloadBlocksMap = new HashMap();

        while(var2.hasNext()) {
            WebElement downloadBlock = (WebElement)var2.next();
            WebElement downloadBlockLink = downloadBlock.findElement(By.className("ArabSeedServer"));
            WebElement videoQuality = downloadBlock.findElement(By.className("TitleCenteral"));
            downloadBlocksMap.put(videoQuality.findElement(By.tagName("span")).getText(),
                    downloadBlockLink.getAttribute("href"));
        }

        return downloadBlocksMap;

    }

    public void downloadMovie(String url){
        this.driver.get(url);
        System.out.println("Getting Movie Name: ");
        String movieName = "";
        try {
             movieName = driver.findElement(By.xpath(
                    "/html/body/ajaxpage/div[1]/div[2]/div[1]/div[2]/div[2]/a/h1")).getText();
            System.out.println("Movie Name: "+ movieName);
        } catch (Exception e) {
            System.out.println("Movie Name not found");
        }
        this.driver.findElement(By.className("downloadBTn")).click();
        String downloadProviderLink = getMovieDownloadLink();
        Movie movie = new Movie(movieName, url);
        movie.setDownloadProviderUrl(downloadProviderLink);
        String downloadLocation = "/home/c1ph3r98/Entertainment/Movies/";
        String directDownloadLink = getDirectDownloadLink(movie.getDownloadProviderUrl());
        Download(directDownloadLink,downloadLocation);
    }

    private String getDirectDownloadLink(String url) {
        System.out.println("Downloading...");
        this.driver.get(url);
        this.driver.findElement(By.id("downloadbtn")).click();
        String directDownloadUrl = this.driver.findElement(
                By.id("direct_link")).findElement(By.tagName("a")).getAttribute("href");

        return directDownloadUrl;

    }

    String getMovieDownloadLink() {
        Map<String,String> downloadBlocksMap = this.getDownloadBLock();
        Map<Integer,String> qualities = new HashMap();
        int i = 1;
        for(String key : downloadBlocksMap.keySet()) {
            qualities.put(i, key);
            i++;
        }
        System.out.println("Choose the desired quality: \n");
        for(Map.Entry<Integer,String> entry : qualities.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the quality number: ");
        int choice = scanner.nextInt();
        String downloadLink = downloadBlocksMap.get(qualities.get(choice));
        return downloadLink;
    }

    private void Download(String url, String location){
        String command = "aria2c " + url + "-c --check-certificate=false"+" -d " + location;

        try {
            Process pb = new ProcessBuilder("/bin/bash", "-c", command).inheritIO().start();
            pb.waitFor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
