package pl.psi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import pl.psi.creatures.Creature;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class Board
{
    private static final int MAX_WITDH = 14;
    private final BiMap< Point, Creature > map = HashBiMap.create();

    public Board( final List< Creature > aCreatures1, final List< Creature > aCreatures2 )
    {
        addCreatures( aCreatures1, 0 );
        addCreatures( aCreatures2, MAX_WITDH );
    }

    private void addCreatures( final List< Creature > aCreatures, final int aXPosition )
    {
        for( int i = 0; i < aCreatures.size(); i++ )
        {
            map.put( new Point( aXPosition, i * 2 + 1 ), aCreatures.get( i ) );
        }
    }

    Optional< Creature > getCreature( final Point aPoint )
    {
        return Optional.ofNullable( map.get( aPoint ) );
    }

    public void move(final Creature aCreature, final Point aPoint)
    {


        if( canMove( aCreature, aPoint ) )
        {
            map.inverse()
                .remove( aCreature );
            map.put( aPoint, aCreature );
        }
    }

    boolean canMove( final Creature aCreature, final Point aPoint )
    {
        final Point oldPosition = getPosition( aCreature );

        if(!checkBlockedPositions(aCreature, aPoint)){
            return false;
        }
        if( map.containsKey( aPoint ) )
        {
            return false;
        }
        return aPoint.distance( oldPosition.getX(), oldPosition.getY() ) < aCreature.getMoveRange();
    }

    Point getPosition( Creature aCreature )
    {
        return map.inverse()
            .get( aCreature );
    }
    boolean checkBlockedPositions( final Creature aCreature, final Point aPoint) {
        final Point oldPosition = getPosition(aCreature);
        List<Point> aList = new ArrayList<Point>();
        for (Point key : map.keySet()) {
            if (!(key.getX() == oldPosition.getX() && key.getY() == oldPosition.getY())) {
                aList = getPointsAroundPosition(key);
                if (aList.contains(aPoint)) {
                    if (!aPoint.checkCollinear(oldPosition.getX(), oldPosition.getY())
                            && !aPoint.checkCollinear(key.getX(), key.getY())) {
                        if (aPoint.distance(oldPosition.getX(), oldPosition.getY()) - 1 > key.distance(oldPosition.getX(), oldPosition.getY())) {
                            return false;
                        }
                    } else if (aPoint.checkCollinear(oldPosition.getX(), oldPosition.getY())
                            && aPoint.checkCollinear(key.getX(), key.getY())
                            && aPoint.distance(oldPosition.getX(), oldPosition.getY()) > key.distance(oldPosition.getX(), oldPosition.getY())) {
                        return false;
                    } else if (!aPoint.checkCollinear(oldPosition.getX(), oldPosition.getY())
                            && aPoint.checkCollinear(key.getX(), key.getY())
                            && aPoint.distance(oldPosition.getX(), oldPosition.getY()) - 2 > key.distance(oldPosition.getX(), oldPosition.getY())) {
                        return false;
                    }

                }
            }
        }
        return true;
    }

    List<Point> getPointsAroundPosition( Point aPoint){
        List<Point> list = new ArrayList<Point>();
        int aX = aPoint.getX();
        int aY = aPoint.getY();
        for(int i = -1; i < 2; i++){
            for(int j = -1; j<2; j++){
                list.add(new Point(aX+i, aY+j));
            }
        }
        return list;
    }
}
