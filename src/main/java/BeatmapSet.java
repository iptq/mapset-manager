import java.io.File;
import java.io.IOException;
import java.util.TreeSet;
import javax.swing.JPanel;

public class BeatmapSet {
    private File location;
    public TreeSet<Beatmap> beatmaps = new TreeSet<Beatmap>();

    public static BeatmapSet fromLocation(File location) {
        BeatmapSet set = new BeatmapSet();

        set.location = location;

        // get all maps from set
        for (File child : set.location.listFiles()) {
            if (child.getName().endsWith(".osu")) {
                Beatmap map = null;
                try {
                    map = Beatmap.fromLocation(child);
                } catch (IOException e) {
                    System.err.println("Something was wrong when fetching '" + child.getName() + "'.");
                    e.printStackTrace();
                }
                if (map != null)
                    set.beatmaps.add(map);
            }
        }

        return set;
    }
}