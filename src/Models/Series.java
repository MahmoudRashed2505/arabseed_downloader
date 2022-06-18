package Models;

import java.util.List;

public class Series {
    private String title;
    private String url;
    private List<Episode> episodes;


    public Series(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public List<Episode> getEpisodes() {
        return List.copyOf(this.episodes);
    }

    public void addEpisode(Episode episode) {
        this.episodes.add(episode);
    }

}
