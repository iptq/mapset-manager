import java.io.File;

public class BeatmapSet {
    private File location;

    public static BeatmapSet fromLocation(File location) {
        BeatmapSet set = new BeatmapSet();

        set.location = location;

        return set;
    }
}