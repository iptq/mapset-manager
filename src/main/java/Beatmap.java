import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.francesco149.koohii.*;

public class Beatmap implements Comparable<Beatmap> {
    public static Koohii.Parser kparser = new Koohii.Parser();
    public static Koohii.DiffCalc kcalc = new Koohii.DiffCalc();

    private File location;
    private BeatmapPanel panel = null;
    public BeatmapSet set;
    public boolean modified = false;
    public int index;

    public HashMap<String, ArrayList<String>> sections = new HashMap<String, ArrayList<String>>();

    // metadata
    public Metadata metadata = new Metadata();
    public int BeatmapID;
    public int BeatmapSetID;

    public String difficultyName;
    public double stars;

    public static Beatmap fromLocation(File location) throws IOException {
        if (!location.exists())
            return null;

        BufferedReader in = new BufferedReader(new FileReader(location));
        Beatmap map = BeatmapDeserializer.load(in);
        map.location = location;

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

        KeyListener modifiedListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                map.modified = true;
                map.set.updateModifyTracker();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        };
        map.metadata.Title.getInputBox().addKeyListener(modifiedListener);
        map.metadata.TitleUnicode.getInputBox().addKeyListener(modifiedListener);
        map.metadata.Artist.getInputBox().addKeyListener(modifiedListener);
        map.metadata.ArtistUnicode.getInputBox().addKeyListener(modifiedListener);
        map.metadata.Creator.getInputBox().addKeyListener(modifiedListener);
        map.metadata.Source.getInputBox().addKeyListener(modifiedListener);
        map.metadata.Tags.getInputBox().addKeyListener(modifiedListener);

        return map;
    }

    public void save() throws IOException {
        if (!modified)
            return;
        // BeatmapSerializer.write(new PrintWriter(System.out, true), this);
        modified = false;
        metadata.save();
        System.out.println("metadata:\n" + metadata.toString());

        PrintWriter out = new PrintWriter(location);
        BeatmapSerializer.write(out, this);
        out.flush();
        out.close();
    }

    public JPanel getPanel() {
        if (panel == null) {
            GridBagLayout layout = new GridBagLayout();
            layout.columnWidths = new int[] { 82, 184, 0 };
            layout.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 0 };
            panel = new BeatmapPanel(layout);

            panel.addRow(new JLabel(metadata.Title.name), metadata.Title.getInputBox());
            panel.addRow(new JLabel(metadata.TitleUnicode.name), metadata.TitleUnicode.getInputBox());
            panel.addRow(new JLabel(metadata.Artist.name), metadata.Artist.getInputBox());
            panel.addRow(new JLabel(metadata.ArtistUnicode.name), metadata.ArtistUnicode.getInputBox());
            panel.addRow(new JLabel(metadata.Creator.name), metadata.Creator.getInputBox());
            panel.addRow(new JLabel(metadata.Source.name), metadata.Source.getInputBox());
            panel.addRow(new JLabel(metadata.Tags.name), metadata.Tags.getInputBox());

            JButton saveFileBtn = new JButton("Commit to File");
            saveFileBtn.setToolTipText("Save your metadata changes to the CURRENT MAP only.");
            saveFileBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Beatmap.this.save();
                        Beatmap.this.set.updateModifyTracker();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            JButton saveMapsetBtn = new JButton("Commit to Mapset");
            saveMapsetBtn.setToolTipText("Save your metadata changes to the ENTIRE MAPSET.");
            saveFileBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Beatmap.this.set.commitMapset(metadata);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            panel.addRow(saveFileBtn, saveMapsetBtn);
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

    public void addRow(Component left, Component right) {
        GridBagConstraints constraint;

        constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.BOTH;
        constraint.insets = new Insets(0, 0, 5, 5);
        constraint.gridx = 0;
        constraint.gridy = y;
        add(left, constraint);

        constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.BOTH;
        constraint.insets = new Insets(0, 0, 5, 0);
        constraint.gridx = 1;
        constraint.gridy = y;
        add(right, constraint);

        y++;
    }
};