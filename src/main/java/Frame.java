import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.JMenu;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Frame extends JFrame {
    private static final long serialVersionUID = 1L;
    private State state;
    private JTabbedPane tabs;

    private void chooseMapset(BeatmapSet set) {
        setTitle("Mapset Manager: " + set.location.getName());
        state.mapset = set;

        // empty tab pane
        tabs.removeAll();

        // populate tab list
        for (Beatmap map : state.mapset.beatmaps) {
            tabs.addTab(map.difficultyName, map.getPanel());
        }
    }

    private void initMenuBar() {
        JMenuBar bar = new JMenuBar();

        // file menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem fileOpenItem = new JMenuItem("Open");
        fileOpenItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean valid = false;
                JFileChooser jfc = new JFileChooser();
                jfc.setCurrentDirectory(new File(Util.detectOsuLocation(), "Songs"));
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int res = jfc.showOpenDialog(Frame.this);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File result = jfc.getSelectedFile();
                    for (File child : result.listFiles()) {
                        if (child.getName().endsWith(".osu")) {
                            valid = true;
                            break;
                        }
                    }
                    if (!valid) {
                        JOptionPane.showMessageDialog(Frame.this, "Please pick a directory that contains .osu files.");
                        return;
                    }

                    BeatmapSet set = BeatmapSet.fromLocation(result);
                    Frame.this.chooseMapset(set);
                }
            }
        });
        fileOpenItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fileMenu.add(fileOpenItem);
        bar.add(fileMenu);

        // help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpAboutItem = new JMenuItem("About");
        helpAboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Frame.this,
                        "Created by Michael Zhang.\nContact: IOException#6405 on Discord");
            }
        });
        helpMenu.add(helpAboutItem);
        bar.add(helpMenu);

        setJMenuBar(bar);
    }

    private void initBody() {
        tabs = new JTabbedPane();
        tabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JLabel("File > Open to select a beatmap."));
        tabs.addTab("<empty>", panel);

        add(tabs);
    }

    public Frame() {
        state = new State();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setTitle("Mapset Manager");
        setSize(new Dimension(854, 480));
        setLocation(((int) screenSize.getWidth() - getWidth()) / 2, ((int) screenSize.getHeight() - getHeight()) / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initMenuBar();
        initBody();
    }
}