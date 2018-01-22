import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BeatmapSerializer {
    private static void writeSection(PrintWriter out, Beatmap map, String name) throws IOException {
        if (!map.sections.containsKey(name))
            return;

        if (!name.equals("Preamble"))
            out.println("[" + name + "]");
        for (String line : map.sections.get(name)) {
            out.println(line + "");
        }
    }

    public static void write(PrintWriter out, Beatmap map) throws IOException {
        writeSection(out, map, "Preamble");
        writeSection(out, map, "General");
        writeSection(out, map, "Editor");

        // metadata
        out.println("[Metadata]");
        out.println("Title:" + map.metadata.Title.getValue());
        out.println("TitleUnicode:" + map.metadata.TitleUnicode.getValue());
        out.println("Artist:" + map.metadata.Artist.getValue());
        out.println("ArtistUnicode:" + map.metadata.ArtistUnicode.getValue());
        out.println("Creator:" + map.metadata.Creator.getValue());
        out.println("Version:" + map.difficultyName);
        out.println("Source:" + map.metadata.Source.getValue());
        out.println("Tags:" + map.metadata.Tags.getValue());
        out.println("BeatmapID:" + map.BeatmapID);
        out.println("BeatmapSetID:" + map.BeatmapSetID);
        out.println();

        writeSection(out, map, "Difficulty");
        writeSection(out, map, "Events");
        writeSection(out, map, "TimingPoints");
        writeSection(out, map, "Colours");
        writeSection(out, map, "HitObjects");
    }
}