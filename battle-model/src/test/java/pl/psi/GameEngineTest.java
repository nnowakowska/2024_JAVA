package pl.psi;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;

import pl.psi.creatures.CastleCreatureFactory;
import pl.psi.creatures.CastleFactory;
import pl.psi.creatures.Creature;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngineTest {


    @Test
    void shoudWorksHeHe() {
        final CastleCreatureFactory creatureFactory = new CastleCreatureFactory();
        final GameEngine gameEngine =
                new GameEngine(new Hero(List.of(creatureFactory.create(1, false, 5))),
                        new Hero(List.of(creatureFactory.create(1, false, 5))));

        gameEngine.attack(new Point(1, 1));
    }

    private static final boolean NOT_IMPORTANT_UPGRADE = false;

    @Test
    void shouldBeAbleToAttack() {
        final CastleFactory creatureFactory = new CastleFactory();
        final Creature aAttacker = creatureFactory.create(NOT_IMPORTANT_UPGRADE, 2, 5);
        final Creature aDefender = creatureFactory.create(NOT_IMPORTANT_UPGRADE, 1, 5);
        final List<Creature> c1 = List.of(aAttacker);
        final List<Creature> c2 = List.of(aDefender);
        final Board board = new Board(c1, c2);
        final GameEngine gameEngine =
                new GameEngine(new Hero(c1),new Hero(c2),board);
        final Point aPoint = board.getPosition(aDefender);
        assertTrue(gameEngine.canAttack(aPoint));
    }
}

