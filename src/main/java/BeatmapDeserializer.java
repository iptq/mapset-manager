import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class BeatmapDeserializer {
    public static Beatmap load(BufferedReader in) throws IOException {
        Beatmap map = new Beatmap();

        String line;
        String section = "Preamble";
        while ((line = in.readLine()) != null) {
            if (line.startsWith("[") && line.endsWith("]")) {
                section = line.substring(1, line.length() - 1);
                continue;
            }
            if (!map.sections.containsKey(section))
                map.sections.put(section, new ArrayList<String>());
            switch (section.toLowerCase()) {
            case "metadata":
                if (!line.contains(":")) // probably a blank line
                    break;
                String[] parts = line.trim().split(":");
                String key = parts[0], value;
                if (parts.length > 1)
                    value = parts[1];
                else // empty
                    value = "";
                switch (key) {
                case "Title":
                    map.metadata.Title.setValue(value);
                    break;
                case "TitleUnicode":
                    map.metadata.TitleUnicode.setValue(value);
                    break;
                case "Artist":
                    map.metadata.Artist.setValue(value);
                    break;
                case "ArtistUnicode":
                    map.metadata.ArtistUnicode.setValue(value);
                    break;
                case "Creator":
                    map.metadata.Creator.setValue(value);
                    break;
                case "Version":
                    map.difficultyName = value;
                    break;
                case "Source":
                    map.metadata.Source.setValue(value);
                    break;
                case "Tags":
                    map.metadata.Tags.setValue(value);
                    break;
                case "BeatmapID":
                    map.BeatmapID = Integer.parseInt(value);
                    break;
                case "BeatmapSetID":
                    map.BeatmapSetID = Integer.parseInt(value);
                    break;
                default:
                    // u don fukt
                    break;
                }
                break;
            default:
                map.sections.get(section).add(line);
                break;
            }
        }

        return map;
    }
}