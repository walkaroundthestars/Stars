public class Star
{
    private String name;
    private String catalogName;
    private int declination;
    private int rectascension;
    private double observedStellarMagnitude;
    private double absoluteStellarMagnitude;
    private int distance;
    private String constellation;
    private String hemisphere;
    private float temperature;
    private double mass;
    private GreekLetters[] greekLetters = GreekLetters.values();

    public Star(String name, String catalogName, int declination, int rectascension,
                double observedStellarMagnitude, double absoluteStellarMagnitude, int distance,
                String constellation, String hemisphere, float temperature, double mass)
    {
        if (name.matches("^[A-Z][A-Z][A-Z]-[0-9][0-9][0-9][0-9]$")) {
            this.name = name;
        }

        setCatalogName(constellation);
    }

    public void setCatalogName(String constellation)
    {
        //ustawiać nazwe na podstawie id
        int id = 1;

        this.catalogName = greekLetters[id].toString() + " " + constellation;
    }
}
