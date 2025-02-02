public class Declination
{
    private int xx;
    private int yy;
    private double zz;

    public Declination(int x, int y, double z)
    {
        this.xx = x;
        this.yy = y;
        this.zz = z;
    }

    public int getXx()
    {
        return xx;
    }

    public void setXx(int x)
    {
        this.xx = x;
    }
    public int getYy()
    {
        return yy;
    }

    public void setYy(int y)
    {
        this.yy = y;
    }

    public double getZz()
    {
        return zz;
    }

    public void setZz(double z)
    {
        this.zz = z;
    }
}
