import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;
import com.github.francesco149.koohii.*;

public class Beatmap implements Comparable<Beatmap> {
    public static Koohii.Parser kparser = new Koohii.Parser();
    public static Koohii.DiffCalc kcalc = new Koohii.DiffCalc();

    private JPanel panel = null;
    private boolean modified = false;

    // metadata
    public String difficultyName;
    public double stars;

    public static Beatmap fromLocation(File location) throws IOException {
        Beatmap map = new Beatmap();

        if (!location.exists())
            return null;

        BufferedReader in = new BufferedReader(new FileReader(location));
        Koohii.Map kmap;
        Koohii.DiffCalc stars;
        try {
            kmap = Beatmap.kparser.map(in);
            stars = Beatmap.kcalc.calc(kmap);
        } catch (UnsupportedOperationException e) {
            return null;
        }
        map.stars = stars.total;

        map.difficultyName = kmap.version;

        return map;
    }

    public JPanel getPanel() {
        if (panel == null) {
            panel = new JPanel();
        }
        return panel;
    }

    @Override
    public int compareTo(Beatmap other) {
        return new Double(stars).compareTo(new Double(other.stars));
    }
}