package pl.psi;

import lombok.Value;

import static java.lang.Math.abs;

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

    public boolean checkCollinear(double px, double py){
        if(px == getX() || py == getY()){
            return true;
    }
        else return false;
    }

    public boolean checkDiagonal(double px, double py){
        if(abs(px - getX()) == abs(py - getY())){
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
