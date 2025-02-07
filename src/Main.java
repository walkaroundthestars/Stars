import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main
{
    public static ArrayList<Star> stars = new ArrayList<Star>();

    public static void main(String[] args)
    {
        try
        {
            //przykładowe obiekty klasy Star
            Star lam = new Star("LAM-1234", new Declination(80, 30, 30.30), new Rectascension(14, 30, 30), 10,3,"Swan",  "PN", 2500, 0.5);
            Star ram = new Star("RAM-1234", new Declination(80, 30, 30.30), new Rectascension(14, 30, 30), 10,3,"Swan",  "PN", 2500, 0.5);
            Star abb = new Star("ABB-1234", new Declination(80, 30, 30.30), new Rectascension(14, 30, 30), 10,3,"Ryb",  "PN", 2500, 0.5);
            Star supernova = new Star("STS-2734", new Declination(-80, 30, 30.30), new Rectascension(14, 30, 30), 10,3,"Swan",  "PD", 3700, 1.7);

            //loadStars();    //odczytanie danych z pliku i wczytanie do listy
            //addStar(lam);  //dodanie przykładowych gwiazd
            //addStar(ram);
            //addStar(abb);
            //addStar(supernova);
            //saveStars();     //zapisanie danych do pliku
            //showStars();      //wyświetlenie wszystkich gwiazd
            //deleteStar("STS-2734");    //usuwanie gwiazd po nazwie
            //searchByConstellation("Ryb");    //wyszukiwanie po gwiazdozbiorze
            //searchByDistance(9.78);        //wyszukiwanie po dystansie
            //searchByTemperature(2300, 2700);     //wyszukiwanie po temperaturze
            //searchByObserved(5, 12);       //wyszukiwanie po obserwowanej wielkości gwiazdowej
            //searchByHemisphere("PN");     //wyszukiwanie po półkuli
            //findSupernovas();             //wyświetlenie potencjalnych supernowych
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    //funkcja wyświetlająca gwiazdy
    public static void showStars()
    {
        if (!stars.isEmpty())
        {
            for (Star s : stars)
            {
                System.out.println("Gwiazda o nazwie: " + s.getName() + " " + s.getCatalogName() +" "+ s.getDeclination().getXx()+" "+s.getDeclination().getYy()+" "+s.getDeclination().getZz()+" "+s.getRectascension().getXx()+" "+s.getRectascension().getYy()+" "+s.getRectascension().getZz()+" "+s.getObservedStellarMagnitude()+" "+s.getAbsoluteStellarMagnitude()+" "+s.getDistance()+" "+s.getConstellation()+" "+s.getHemisphere()+" "+s.getTemperature()+" "+s.getMass());
            }
        }
        else
        {
            loadStars();
            if (stars.isEmpty())
            {
                System.out.print("Baza jest pusta");
            }
            else
            {
                for (Star s : stars)
                {
                    System.out.println("Gwiazda o nazwie: " + s.getName()  + " " + s.getCatalogName() +" "+ s.getDeclination().getXx()+" "+s.getDeclination().getYy()+" "+s.getDeclination().getZz()+" "+s.getRectascension().getXx()+" "+s.getRectascension().getYy()+" "+s.getRectascension().getZz()+" "+s.getObservedStellarMagnitude()+" "+s.getAbsoluteStellarMagnitude()+" "+s.getDistance()+" "+s.getConstellation()+" "+s.getHemisphere()+" "+s.getTemperature()+" "+s.getMass());
                }
            }
        }
    }

    //dodawanie elementu typu gwiazda. Funkcja przyjmuje jako parametr nowy obiekt
    public static void addStar(Star newStar) throws Exception
    {
        boolean isNameInFile = false;
        for (Star star : stars)
        {
            if (star.getName().equals(newStar.getName()))
            {
                isNameInFile = true;
            }
        }

        if (!isNameInFile)
        {
            stars.add(newStar);
            updateCatalogNames();
        }
        else
        {
            throw new Exception("Gwiazda o tej nazwie już istnieje.");
        }
    }

    //odczytywanie gwiazd z pliku csv
    public static void loadStars()
    {
        String filePath = "stars.csv";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                //odczytanie wszystkich danych o gwieździe
                String[] values = line.split(";");

                if (values.length == 11)
                {
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

                    Star newStar = new Star(values[0], dec, rect, oSM, distance, values[7], values[8], temp, mass);
                    boolean isNameInFile = false;
                    for (Star s : stars)
                    {
                        if (s.getName().equals(newStar.getName()))
                        {
                            isNameInFile = true;
                        }
                    }

                    if (!isNameInFile)
                    {
                        stars.add(newStar);
                    }
                }
            }

            if (stars.isEmpty())
            {
                System.out.println("Baza jest pusta");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //zapisywanie gwiazd do pliku csv
    public static void saveStars()
    {
        String filePath = "stars.csv";

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)))
        {
            for (Star s : stars)
            {
                bw.write(s.getName() +";"+ s.getCatalogName() +";"+ s.getDeclination().getXx()+":"+s.getDeclination().getYy()+":"+s.getDeclination().getZz()+";"+s.getRectascension().getXx()+":"+s.getRectascension().getYy()+":"+s.getRectascension().getZz()+";"+s.getObservedStellarMagnitude()+";"+s.getAbsoluteStellarMagnitude()+";"+s.getDistance()+";"+s.getConstellation()+";"+s.getHemisphere()+";"+s.getTemperature()+";"+s.getMass()+"\n");
            }

            System.out.println("Plik CSV został zapisany.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //usuwanie gwiazdy na podstawie nazwy, która jest przekazywana przez parametr
    public static void deleteStar(String name)
    {
        for (int i = stars.size() - 1; i >= 0; i--)
        {
            if (stars.get(i).getName().equals(name))
            {
                stars.remove(i);  // usuwanie elementu po indeksie
            }
        }
        saveStars();
        updateCatalogNames();
    }

    //funckja aktualizująca nazwy katalogowe gwiazd
    public static void updateCatalogNames()
    {
        for (Star star : stars)
        {
            star.setCatalogName(star.getConstellation());
        }
        saveStars();
    }

    //funkcja do wyszukiwania wszystkich gwiazd w danym gwiazdozbiorze, który jest przekazywany przez parametr
    public static void searchByConstellation(String constellation)
    {
        for (Star star : stars)
        {
            if (star.getConstellation().equals(constellation))
            {
                System.out.println(constellation + ": " + star.getName() + " " + star.getCatalogName());
            }
        }
    }

    //wyszukiwanie gwiazd na podstawie dystansu, który jest podany jako parametr
    public static void searchByDistance(double parsecs)
    {
        for (Star star : stars)
        {
            double distanceInParsecs = star.getDistance() * 3.26;
            if (distanceInParsecs == parsecs )
            {
                System.out.println(star.getName() + " " + star.getCatalogName() + " - " + distanceInParsecs);
            }
        }
    }

    //wyszukiwanie gwiazd których temperatura mieści się w danym przedziale, przekazanym do funkcji jako parametry (początek i koniec przedziału)
    public static void searchByTemperature(double minTemperature, double maxTemperature)
    {
        for (Star star : stars)
        {
            if (star.getTemperature() <= maxTemperature && star.getTemperature() >= minTemperature)
            {
                System.out.println(star.getName() + " " + star.getCatalogName() + " - " + star.getTemperature());
            }
        }
    }

    //wyszukiwanie po obserwowanej wielkości gwiazdowej, zakres przekazywany jest do funcji jako parametry
    public static void searchByObserved(double minOSM, double maxOSM)
    {
        for (Star star : stars)
        {
            if (star.getObservedStellarMagnitude() <= maxOSM && star.getObservedStellarMagnitude() >= minOSM)
            {
                System.out.println(star.getName() + " " + star.getCatalogName() + " - " + star.getObservedStellarMagnitude());
            }
        }
    }

    //wyszukowanie gwiazd z danej półkuli, która przekazywana jest w parametrze
    public static void searchByHemisphere(String hemisphere)
    {
        for (Star star : stars)
        {
            if (star.getHemisphere().equals(hemisphere))
            {
                System.out.println(hemisphere + ": " + star.getName() + " " + star.getCatalogName());
            }
        }
    }

    //wyszukiwanie potencjalnych supernowych
    public static void findSupernovas()
    {
        for (Star star : stars)
        {
            if (star.getMass() >= 1.44)
            {
                System.out.println(star.getName() + " " + star.getCatalogName() + " - " + star.getMass());
            }
        }
    }
}

