import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.Math;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Star
{
    private String name;
    private String catalogName;
    private Declination declination;
    private Rectascension rectascension;
    private double observedStellarMagnitude;
    private double absoluteStellarMagnitude;
    private double distance;
    private String constellation;
    private String hemisphere;
    private double temperature;
    private double mass;
    private GreekLetters[] greekLetters = GreekLetters.values();

    public Star(String name, Declination declination, Rectascension rectascension,
                double observedStellarMagnitude, double distance, String constellation,
                String hemisphere, double temperature, double mass) throws Exception {
        //ustawianie nazwy gwiazdy
        setName(name);

        //ustawienie nazwy konstelacji
        setConstellation(constellation);

        //ustawianie nazwy katalogowej
        setCatalogName(constellation);

        //ustawienie półkuli, na której widoczna jest gwiazda
        setHemisphere(hemisphere);

        //ustawienie deklinacji
        setDeclination(declination);

        //ustawienie rektascensji
        setRectascension(rectascension);

        //ustawienie odległości
        setDistance(distance);

        //ustawienie obserwowanej wielkości gwiazdowej
        setObservedStellarMagnitude(observedStellarMagnitude);

        //ustawienie absolutnej wielkości gwiazdowej
        setAbsoluteStellarMagnitude(observedStellarMagnitude, distance);

        //ustawienie temperatury
        setTemperature(temperature);

        //ustawienie masy
        setMass(mass);
    }

    public String getName()
    {
        return name;
    }
    //metoda do ustawiania nazwy
    public void setName(String name) throws Exception {
        if (name.matches("^[A-Z][A-Z][A-Z]-[0-9][0-9][0-9][0-9]$"))
        {
            this.name = name;
        }
        else
        {
            throw new Exception("Name doesn't match the pattern");
        }
    }

    public String getConstellation()
    {
        return constellation;
    }

    public void setConstellation(String constellation) throws Exception
    {
        this.constellation = constellation;
    }

    public String getCatalogName()
    {
        return catalogName;
    }

    //metoda do ustawiania nazwy katalogowej
    public void setCatalogName(String constellation)
    {
        //TODO index
        //ustawiać nazwe na podstawie indexu

        int numberOfStars = 0;
        String filePath = "stars.csv";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                //odczytanie gwiazd z tego samego gwiazdozbioru

                //ma odczytywać tylko do momentu napotkania tej gwiazdy rekurencyjnie,
                // a nie zliczać liczbę wszystkich gwiazd w gwiazdozbiorze
                String[] values = line.split(";");
                if (values.length > 7 && values[7].equals(constellation))
                {
                    numberOfStars++;

                    if (values[0].equals(this.name))
                    {
                        break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (numberOfStars > 0)
            this.catalogName = greekLetters[numberOfStars - 1].toString() + " " + constellation;
        else if (numberOfStars == 0)
            this.catalogName = greekLetters[numberOfStars].toString() + " " + constellation;
    }

    public String getHemisphere()
    {
        return hemisphere;
    }

    //metoda do ustawiania półkuli
    public void setHemisphere(String hemisphere) throws Exception
    {
        if (hemisphere.equals("PN") || hemisphere.equals("PD"))
        {
            this.hemisphere = hemisphere;
        }
        else
        {
            throw new Exception("Hemisphere doesn't match the pattern");
        }
    }

    public Declination getDeclination()
    {
        return declination;
    }
    //metoda do ustawiania deklinacji
    public void setDeclination(Declination declination) throws Exception
    {
        //ustawienie deklinacji dla półkuli północnej
        if (hemisphere.equals("PN"))
        {
            if (declination.getXx() >= 0 && declination.getXx() < 90
                    && declination.getYy() >= 0 && declination.getYy() < 60
                    && declination.getZz() >= 0 && declination.getZz() < 60)
            {
                this.declination = declination;
            }
            else if (declination.getXx() == 90 && declination.getYy() == 0 && declination.getZz() == 0)
            {
                this.declination = declination;
            }
            else
            {
                throw new Exception("Declination is out of range");
            }
        }

        //ustawienie deklinacji dla półkuli południowej
        if (hemisphere.equals("PD"))
        {
            if (declination.getXx() >= -90 && declination.getXx() < 0
                    && declination.getYy() >= 0 && declination.getYy() < 60
                    && declination.getZz() >= 0 && declination.getZz() < 60)
            {
                this.declination = declination;
            }
            else if (declination.getXx() == 0 && declination.getYy() == 0 && declination.getZz() == 0)
            {
                this.declination = declination;
            }
            else
            {
                throw new Exception("Declination is out of range");
            }
        }
    }

    public Rectascension getRectascension()
    {
        return rectascension;
    }

    //metoda do ustawienia rektascencji
    public void setRectascension(Rectascension rectascension) throws Exception
    {
        if (rectascension.getXx() >= 0 && rectascension.getXx() < 24
                && rectascension.getYy() >= 0 && rectascension.getYy() < 60
                && rectascension.getZz() >= 0 && rectascension.getZz() < 60)
        {
            this.rectascension = rectascension;
        }
        else if (rectascension.getXx() == 24 && rectascension.getYy() == 0 && rectascension.getZz() == 0)
        {
            this.rectascension = rectascension;
        }
        else
        {
            throw new Exception("Rectascension is out of range");
        }
    }

    public double getObservedStellarMagnitude()
    {
        return observedStellarMagnitude;
    }

    //metoda do ustawienia obserwowanej wielkości gwiazdowej
    public void setObservedStellarMagnitude(double observedStellarMagnitude) throws Exception
    {
        if (observedStellarMagnitude >= -26.74 && observedStellarMagnitude <= 15.00)
        {
            this.observedStellarMagnitude = observedStellarMagnitude;
        }
        else
        {
            throw new Exception("ObservedStellarMagnitude is out of range");
        }
    }

    public double getAbsoluteStellarMagnitude()
    {
        return absoluteStellarMagnitude;
    }

    //metoda do ustawienia absolutnej wielkości gwaizdowej
    public void setAbsoluteStellarMagnitude(double observedStellarMagnitude, double distance)
    {
        double distanceInParsecs = distance * 3.26;
        this.absoluteStellarMagnitude = observedStellarMagnitude - (5 * Math.log10(distanceInParsecs)) + 5;
    }

    public double getDistance()
    {
        return distance;
    }

    //metoda do ustawienia dystansu
    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public double getTemperature()
    {
        return temperature;
    }

    //metoda do ustawienia temperatury
    public void setTemperature(double temperature) throws Exception
    {
        if (temperature >= 2000)
        {
            this.temperature = temperature;
        }
        else
        {
            throw new Exception("Temperature is out of range");
        }
    }

    public double getMass()
    {
        return mass;
    }

    //metoda do ustawienia masy
    public void setMass(double mass) throws Exception
    {
        if (mass >= 0.1 && mass <= 50)
        {
            this.mass = mass;
        }
        else
        {
            throw new Exception("Mass is out of range");
        }
    }
}
