import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Frame extends JFrame {
    private static final long serialVersionUID = 1L;
    private State state;

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
                    if (valid) {
                        state.mapset = BeatmapSet.fromLocation(result);
                    } else {
                        JOptionPane.showMessageDialog(Frame.this, "Please pick a directory that contains .osu files.");
                    }
                }
            }
        });
        fileMenu.add(fileOpenItem);
        bar.add(fileMenu);

        setJMenuBar(bar);
    }

    public Frame() {
        state = new State();

        setTitle("Mapset Manager");
        setSize(new Dimension(1024, 768));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initMenuBar();
    }
}