import java.io.File;

public class Util {
    private static final File[] possibleOsuLocations = {
            new File("/home/michael/.wine-osu/drive_c/users/michael/Local Settings/Application Data/osu!") // hardcoding LUL
    };

    public static File detectOsuLocation() {
        for (File location : possibleOsuLocations) {
            if (!location.exists())
                continue;

            // check for existence of osu.exe
            File osuExe = new File(location, "osu!.exe");
            if (!osuExe.exists())
                continue;

            // check for existence of songs folder
            File songsFolder = new File(location, "Songs");
            if (!songsFolder.exists() || !songsFolder.isDirectory())
                continue;

            // we good Bois
            return location;
        }
        return new File("."); // fallback to not knowing where we are
    }
}