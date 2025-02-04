import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Main
{
    private static ArrayList<Star> stars = new ArrayList<Star>();

    public static void main(String[] args)
    {
        try
        {
            Star test = new Star("LAM-1234", new Declination(80, 30, 30.30), new Rectascension(14, 30, 30), 10,3,"Swan",  "PN", 2500, 0.5);
            Star test1 = new Star("RAM-1234", new Declination(80, 30, 30.30), new Rectascension(14, 30, 30), 10,3,"Swan",  "PN", 2500, 0.5);
            Star testDel = new Star("ABB-1234", new Declination(80, 30, 30.30), new Rectascension(14, 30, 30), 10,3,"Ryb",  "PN", 2500, 0.5);

            loadStars();
            addStar(test);
            addStar(test1);
            addStar(testDel);
            saveStars();
            //loadStars();
            showStars();
            //deleteStar("beta Ryb");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    //funkcja wyświetlająca gwiazdy
    public static void showStars()
    {
        //dopracować
        for (Star s : stars)
        {
            System.out.println("Gwiazda o nazwie: " + s.getName());
        }
    }

    //dodawanie elementu typu gwiazda
    public static void addStar(Star newStar) throws Exception
    {
        boolean isNameInFile = false;
        for (Star star : stars){
            if (star.getName().equals(newStar.getName()))
            {
                isNameInFile = true;
            }
        }

        if (!isNameInFile)
        {
            stars.add(newStar);
        }
        else
        {
            throw new Exception("Star with this name already exists");
        }
    }

    //dodawanie elementu przez parametry
    public static void addStar(String name, Declination declination, Rectascension rectascension,
                        double observedStellarMagnitude, double distance, String constellation,
                        String hemisphere, double temperature, double mass) throws Exception
    {
        boolean isNameInFile = false;
        for (Star star : stars){
            if (star.getName().equals(name))
            {
                isNameInFile = true;
            }
        }

        if (!isNameInFile)
        {
            Star newStar = new Star(name, declination, rectascension, observedStellarMagnitude, distance, constellation, hemisphere, temperature, mass);
            stars.add(newStar);
        }
        else
        {
            throw new Exception("Star with this name already exists");
        }
    }

    public static void loadStars()
    {
        String filePath = "stars.csv";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                //odczytanie wszystkich danych o gwieździe
                String[] values = line.split(";");

                //odczytanie deklinacji gwiazdy
                String[] decTab = values[2].split(":");

                int xx = Integer.parseInt(decTab[0]);
                int yy = Integer.parseInt(decTab[1]);
                double zz = Double.parseDouble(decTab[2]);

                Declination dec = new Declination(xx, yy, zz);

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
                //System.out.println("Nazwa: " + values[0] + ", Nazwa katalogowa: " + values[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveStars()
    {
        String filePath = "stars.csv";

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)))
        {
            //bw.write("ID,Nazwa,Cena\n");
            for (Star s : stars)
            {
                bw.write(s.getName() +";"+ s.getCatalogName() +";"+ s.getDeclination().getXx()+":"+s.getDeclination().getYy()+":"+s.getDeclination().getZz()+";"+s.getRectascension().getXx()+":"+s.getRectascension().getYy()+":"+s.getRectascension().getZz()+";"+s.getObservedStellarMagnitude()+";"+s.getAbsoluteStellarMagnitude()+";"+s.getDistance()+";"+s.getConstellation()+";"+s.getHemisphere()+";"+s.getTemperature()+";"+s.getMass()+"\n");
            }
            //bw.write("1,Laptop,2500\n");
            //bw.write("2,Smartfon,1500\n");
            //bw.write("3,Tablet,1200\n");

            System.out.println("Plik CSV został zapisany.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteStar(String catalogName)
    {
        for (Star star : stars)
        {
            if (star.getCatalogName().equals(catalogName))
            {
                stars.remove(star);
            }
        }
        saveStars();
    }

    public void updateCatalogNames()
    {

    }
}

