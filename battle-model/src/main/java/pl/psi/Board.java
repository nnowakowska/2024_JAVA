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

    public Optional< Creature > getCreature(final Point aPoint)
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

    public boolean canMove(final Creature aCreature, final Point aPoint)
    {
        final Point oldPosition = getPosition( aCreature );

        if(!checkBlockedPositions(aCreature, aPoint) && !aCreature.isFlying()){
            return false;
        }
        if( map.containsKey( aPoint ) )
        {
            return false;
        }
        return aPoint.distance( oldPosition.getX(), oldPosition.getY() ) < aCreature.getMoveRange();
    }

    public Point getPosition(Creature aCreature)
    {
        return map.inverse()
            .get( aCreature );
    }
    boolean checkBlockedPositions( final Creature aCreature, final Point aPoint) {
        final Point oldPosition = getPosition(aCreature);
        for (Point key : map.keySet()) {

            if (!(key.getX() == oldPosition.getX() && key.getY() == oldPosition.getY())) {
                    if (aPoint.checkCollinear(oldPosition.getX(), oldPosition.getY()) && aPoint.checkCollinear(key.getX(), key.getY())) {
                        if (isBlockedPosition(oldPosition, aPoint, key)) {
                            return false;}
                    }
                    else if (aPoint.checkDiagonal(oldPosition.getX(), oldPosition.getY()) && aPoint.checkDiagonal(key.getX(), key.getY())){
                        if (isBlockedPosition(oldPosition, aPoint, key)) {
                            return false;}
                    }
                }
        }
        return true;
    }

    boolean isBlockedPosition( Point oldPosition, Point newPosition, Point obstaclePoint){
        if (newPosition.distance(oldPosition.getX(), oldPosition.getY()) > obstaclePoint.distance(oldPosition.getX(), oldPosition.getY())
            && newPosition.distance(oldPosition.getX(), oldPosition.getY()) > obstaclePoint.distance(newPosition.getX(), newPosition.getY() )) {
            return true;
        }
        else return false;
    }

}
