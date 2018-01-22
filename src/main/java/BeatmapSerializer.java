import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class BeatmapSerializer {
    private static void writeSection(Writer out, Beatmap map, String name) throws IOException {
        if (!map.sections.containsKey(name))
            return;

        if (!name.equals("Preamble"))
            out.write("[" + name + "]\n");
        for (String line : map.sections.get(name)) {
            out.write(line + "\n");
        }
        out.write("\n");
    }

    public static void write(Writer out, Beatmap map) throws IOException {
        writeSection(out, map, "Preamble");
        writeSection(out, map, "General");
        writeSection(out, map, "Editor");

        // metadata

        writeSection(out, map, "Difficulty");
        writeSection(out, map, "Events");
        writeSection(out, map, "TimingPoints");
        writeSection(out, map, "Colours");
        writeSection(out, map, "HitObjects");
    }
}