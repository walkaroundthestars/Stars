import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Main
{
    private static ArrayList<Star> stars = new ArrayList<Star>();

    public static void main(String[] args)
    {
        System.out.println("Hello world!");
        
    }

    public void loadStars()
    {

    }

    public void saveStars()
    {

    }

    public void addStar(String name, Declination declination, Rectascension rectascension,
                        double observedStellarMagnitude, double distance, String constellation,
                        String hemisphere, double temperature, double mass) throws Exception
    {
        Star star = new Star(name, declination, rectascension, observedStellarMagnitude, distance, constellation, hemisphere, temperature, mass);
        stars.add(star);
    }

    public void showStars()
    {
        String filePath = "stars.csv";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                //odczytanie wszystkich danych o gwieździe
                String[] values = line.split(",");

                //odczytanie deklinacji gwiazdy
                String[] decTab = values[2].split(":");
                int[] decInt = new int[decTab.length];
                for (int i = 0; i < decTab.length; i++)
                {
                    int el = Integer.parseInt(decTab[i]);
                    decInt[i] = el;
                }
                Declination dec = new Declination(decInt[0], decInt[1], decInt[2]);

                //odczytanie rektascencji
                String[] rectTab = values[3].split(":");
                int[] rectInt = new int[rectTab.length];
                for (int i = 0; i < rectTab.length; i++)
                {
                    int el = Integer.parseInt(rectTab[i]);
                    rectInt[i] = el;
                }
                Rectascension rect = new Rectascension(rectInt[0], rectInt[1], rectInt[2]);

                //odczytanie obserwowanej wielkości gwiazdy
                double oSM = Double.parseDouble(values[4]);

                //odczytanie dystansu
                double distance = Double.parseDouble(values[6]);

                //odczytanie temperatury
                double temp = Double.parseDouble(values[9]);

                //odczytanie masy gwiazdy
                double mass = Double.parseDouble(values[10]);

                addStar(values[0], dec, rect, oSM, distance, values[7], values[8], temp, mass);
                System.out.println("Nazwa: " + values[0] + ", Nazwa katalogowa: " + values[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStar()
    {

    }

    public void updateCatalogNames()
    {

    }
}

