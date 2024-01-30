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


}

