package btb.blindtest;

import java.util.ArrayList;

/**
 * Created by Tlucien on 25/12/2016.
 */

public class Music {
    String title;
    String artist;
    String filename;
    public Music(String title, String artist, String filename) {
        super();
        this.artist = artist;
        this.title = title;
        this.filename = filename;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {

        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
