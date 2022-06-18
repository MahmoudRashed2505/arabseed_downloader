package Models;

public class Episode {
    private String title;
    private String url;
    private String downloadProviderUrl;

    public Episode(String title, String url) {
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

}
