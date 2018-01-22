public class MapsetManager {
    private static MapsetManager instance;

    public MapsetManager() {
        Frame frame = new Frame();
        frame.setVisible(true);
    }

    public static MapsetManager getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new MapsetManager();
    }
}