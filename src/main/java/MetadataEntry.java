import javax.swing.JTextField;

public class MetadataEntry {
    public boolean modified = false;
    public final String name;
    private String value;
    private JTextField inputBox = new JTextField();

    public MetadataEntry(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public MetadataEntry(String name) {
        this(name, null);
    }

    public void setValue(String value) {
        this.inputBox.setText(value);
        this.value = value;
    }

    public String getActualValue() {
        return this.value;
    }

    public JTextField getInputBox() {
        return inputBox;
    }
}