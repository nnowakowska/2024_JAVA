package pl.psi;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

import pl.psi.creatures.Creature;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngine {

    public static final String CREATURE_MOVED = "CREATURE_MOVED";
    private final TurnQueue turnQueue;
    private final Board board;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);

    public  GameEngine(final Hero aHero1, final Hero aHero2) {
        this(aHero1, aHero2, new Board(aHero1.getCreatures(), aHero2.getCreatures()));
    }

    GameEngine(Hero aHero1, Hero aHero2, Board aBoard) {
        turnQueue = new TurnQueue(aHero1.getCreatures(), aHero2.getCreatures());
        board = aBoard;
    }

    public void attack(final Point point) {
        board.getCreature(point)
                .ifPresent(defender -> turnQueue.getCurrentCreature()
                        .attack(defender));
        pass();
    }

    public boolean canMove(final Point aPoint) {
        return board.canMove(turnQueue.getCurrentCreature(), aPoint);
    }

    public void move(final Point aPoint) {
        board.move(turnQueue.getCurrentCreature(), aPoint);
        observerSupport.firePropertyChange(CREATURE_MOVED, null, aPoint);
    }

    public Optional<Creature> getCreature(final Point aPoint) {
        return board.getCreature(aPoint);
    }

    public void pass() {
        turnQueue.next();
    }

    public void addObserver(final PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
        turnQueue.addObserver(aObserver);
    }

    public boolean canAttack(final Point point) {
        double distance = board.getPosition(turnQueue.getCurrentCreature())
                .distance(point);
        double range = turnQueue.getCurrentCreature().getAttackRange();

        return board.getCreature(point)
                .isPresent()
                &&  distance < range  && distance > 0;
    }

    public boolean isCurrentCreature(Point aPoint) {
        return Optional.of(turnQueue.getCurrentCreature()).equals(board.getCreature(aPoint));
    }
}
