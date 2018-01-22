import java.io.File;
import java.util.ArrayList;

public class BeatmapSet {
    private File location;
    private ArrayList<Beatmap> beatmaps = new ArrayList<Beatmap>();

    public static BeatmapSet fromLocation(File location) {
        BeatmapSet set = new BeatmapSet();

        set.location = location;

        // get all maps from set
        for (File child : set.location.listFiles()) {
            if (child.getName().endsWith(".osu")) {
                Beatmap map = Beatmap.fromLocation(child);
                if (map != null)
                    set.beatmaps.add(map);
            }
        }

        return set;
    }
}