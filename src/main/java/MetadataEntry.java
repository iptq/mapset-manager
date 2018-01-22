import java.awt.Dimension;

import javax.swing.JTextField;

public class MetadataEntry {
    public boolean modified = false;
    public final String name;
    private String value;
    private JTextField inputBox;

    public MetadataEntry(String name, String value) {
        this.name = name;
        this.value = value;
        inputBox = new JTextField();
        inputBox.setMaximumSize(new Dimension(30, 184));
    }

    public MetadataEntry(String name) {
        this(name, null);
    }

    public void setValue(String value) {
        this.inputBox.setText(value);
        this.value = value;
    }

    public void save() {
        this.value = this.inputBox.getText();
    }

    public String getValue() {
        return this.value;
    }

    public JTextField getInputBox() {
        return inputBox;
    }
}