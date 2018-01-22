import java.io.File;

public class Beatmap {
    public static Beatmap fromLocation(File location) {
        Beatmap map = new Beatmap();

        if (!location.exists())
            return null;

        return map;
    }
}