package Models;

public class Movie {
    private String title;
    private String url;
    private String downloadProviderUrl;

    public Movie(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDownloadProviderUrl() {
        return this.downloadProviderUrl;
    }

    public void setDownloadProviderUrl(String downloadProviderUrl) {
        this.downloadProviderUrl = downloadProviderUrl;
    }
}
