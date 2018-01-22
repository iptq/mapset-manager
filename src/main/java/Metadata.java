public class Metadata {
    public MetadataEntry Title = new MetadataEntry("Title");
    public MetadataEntry TitleUnicode = new MetadataEntry("Title Unicode");
    public MetadataEntry Artist = new MetadataEntry("Artist");
    public MetadataEntry ArtistUnicode = new MetadataEntry("Artist Unicode");
    public MetadataEntry Creator = new MetadataEntry("Creator");
    public MetadataEntry Source = new MetadataEntry("Source");
    public MetadataEntry Tags = new MetadataEntry("Tags");

    public void save() {
        Title.save();
        TitleUnicode.save();
        Artist.save();
        ArtistUnicode.save();
        Creator.save();
        Source.save();
        Tags.save();
    }

    public void assimilate(Metadata other) {
        Title.setValue(other.Title.getValue());
        TitleUnicode.setValue(other.TitleUnicode.getValue());
        Artist.setValue(other.Artist.getValue());
        ArtistUnicode.setValue(other.ArtistUnicode.getValue());
        Creator.setValue(other.Creator.getValue());
        Source.setValue(other.Source.getValue());
        Tags.setValue(other.Tags.getValue());
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Title:" + Title.getValue() + "\n");
        builder.append("TitleUnicode:" + TitleUnicode.getValue() + "\n");
        builder.append("Artist:" + Artist.getValue() + "\n");
        builder.append("ArtistUnicode:" + ArtistUnicode.getValue() + "\n");
        builder.append("Creator:" + Creator.getValue() + "\n");
        builder.append("Source:" + Source.getValue() + "\n");
        builder.append("Tags:" + Tags.getValue() + "\n");
        return builder.toString();
    }
}