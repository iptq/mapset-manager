import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import com.github.francesco149.koohii.*;

public class Beatmap implements Comparable<Beatmap> {
    public static Koohii.Parser kparser = new Koohii.Parser();
    public static Koohii.DiffCalc kcalc = new Koohii.DiffCalc();

    private BeatmapPanel panel = null;
    private boolean modified = false;

    // metadata
    public MetadataEntry metaTitle = new MetadataEntry("Title");
    public MetadataEntry metaTitleUnicode = new MetadataEntry("Title Unicode");
    public MetadataEntry metaArtist = new MetadataEntry("Artist");
    public MetadataEntry metaArtistUnicode = new MetadataEntry("Artist Unicode");

    public String difficultyName;
    public double stars;

    public static Beatmap fromLocation(File location) throws IOException {
        if (!location.exists())
            return null;

        BufferedReader in = new BufferedReader(new FileReader(location));
        Beatmap map = BeatmapDeserializer.load(in);

        // probably a better way to do this but i'm lazy
        in = new BufferedReader(new FileReader(location));
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
            GridBagLayout layout = new GridBagLayout();
            layout.columnWidths = new int[] { 82, 184, 0 };
            layout.rowHeights = new int[] { 30, 30, 30, 30, 0 };
            panel = new BeatmapPanel(layout);

            panel.addRow(new JLabel(metaTitle.name), metaTitle.getInputBox());
            panel.addRow(new JLabel(metaTitleUnicode.name), metaTitleUnicode.getInputBox());
            panel.addRow(new JLabel(metaArtist.name), metaArtist.getInputBox());
            panel.addRow(new JLabel(metaArtistUnicode.name), metaArtistUnicode.getInputBox());
        }
        return panel;
    }

    @Override
    public int compareTo(Beatmap other) {
        return new Double(stars).compareTo(new Double(other.stars));
    }
}

class BeatmapPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private int y = 0;

    public BeatmapPanel(LayoutManager layout) {
        super(layout);
    }

    public void addRow(JLabel label, Component component) {
        GridBagConstraints constraint;

        constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.BOTH;
        constraint.insets = new Insets(0, 0, 5, 5);
        constraint.gridx = 0;
        constraint.gridy = y;
        add(label, constraint);

        constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.BOTH;
        constraint.insets = new Insets(0, 0, 5, 0);
        constraint.gridx = 1;
        constraint.gridy = y;
        add(component, constraint);

        y++;
    }
};