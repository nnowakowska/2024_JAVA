package pl.psi;

import lombok.Value;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
@Value
public class Point
{
    private final int x;
    private final int y;

    public Point( final int aX, final int aY )
    {
        x = aX;
        y = aY;
    }

    public double distance( Point aPoint )
    {
        return distance( aPoint.getX(), aPoint.getY() );
    }

    public String direction(double px, double py){
        if(px > getX()){
          return "E";
        }
        else if (py > getY()){
            return "N";
        }
        else if(px < getX()){
            return "W";
        }
        else {
            return "S";
        }

    }
    public boolean checkCollinear(double px, double py){
        if(px == getX() || py == getY()){
            return true;
    }
        else return false;
    }
    public double distance( double px, double py )
    {
        px -= getX();
        py -= getY();
        return Math.sqrt( px * px + py * py );
    }
}
