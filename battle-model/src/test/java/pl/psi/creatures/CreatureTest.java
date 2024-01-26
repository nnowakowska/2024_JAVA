package pl.psi.creatures;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class CreatureTest {

    private static final Range<Integer> NOT_IMPORTANT_DMG = Range.closed(0, 0);

    @Test
    void creatureShouldAttackProperly() {
        // given
        final Creature angel = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(50)
                        .armor(0)
                        .build())
                .build();
        final Creature dragon = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(10)
                        .build())
                .build();
        // when
        angel.attack(dragon);
        // then
        assertThat(dragon.getCurrentHp()).isEqualTo(70);
    }

    @Test
    void creatureShouldNotHealCreatureEvenHasLowerAttackThanDefenderArmor() {
        final Creature angel = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(1)
                        .armor(0)
                        .build())
                .build();
        final Creature dragon = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(10)
                        .build())
                .build();
        // when
        angel.attack(dragon);
        // then
        assertThat(dragon.getCurrentHp()).isEqualTo(100);
    }

    @Test
    void defenderShouldCounterAttack() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(10)
                        .build())
                .build();
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(10)
                        .build())
                .build();
        // when
        attacker.attack(defender);
        // then
        assertThat(attacker.getCurrentHp()).isEqualTo(90);
    }

    @Test
    void defenderShouldNotCounterAttackWhenIsDie() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(1000)
                        .armor(10)
                        .build())
                .build();
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(20)
                        .armor(5)
                        .build())
                .build();
        // when
        attacker.attack(defender);
        // then
        assertThat(attacker.getCurrentHp()).isEqualTo(100);
    }

    @Test
    void defenderShouldCounterAttackOnlyOncePerTurn() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(0)
                        .build())
                .build();

        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(0)
                        .build())
                .build();

        // when
        attacker.attack(defender);
        attacker.attack(defender);
        // then
        assertThat(attacker.getCurrentHp()).isEqualTo(90);
    }

    @Test
    void counterAttackCounterShouldResetAfterEndOfTurn() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .build())
                .build();

        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final TurnQueue turnQueue = new TurnQueue(List.of(attacker), List.of(defender));

        attacker.attack(defender);
        attacker.attack(defender);
        assertThat(attacker.getCurrentHp()).isEqualTo(90);
        turnQueue.next();
        turnQueue.next();
        attacker.attack(defender);
        assertThat(attacker.getCurrentHp()).isEqualTo(80);
        // end of turn
    }

    @Test
    void creatureShouldHealAfterEndOfTurn() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Creature selfHealAfterEndOfTurnCreature = new SelfHealAfterTurnCreature(new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .build())
                .build());

        final TurnQueue turnQueue =
                new TurnQueue(List.of(attacker), List.of(selfHealAfterEndOfTurnCreature));

        attacker.attack(selfHealAfterEndOfTurnCreature);
        assertThat(selfHealAfterEndOfTurnCreature.getCurrentHp()).isEqualTo(90);
        turnQueue.next();
        turnQueue.next();
        assertThat(selfHealAfterEndOfTurnCreature.getCurrentHp()).isEqualTo(100);
    }

    @Test
    void defenderShouldCounterAttackTwicePerTurn() {
        final Creature defender = new IncreasedRetaliationCreature(new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .attack(0)
                        .armor(0)
                        .build())
                .build(), 2);

        final Creature aAttacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .build())
                .build();

        // when
        aAttacker.attack(defender);
        // then
        assertThat(defender.getCounterAttackCounter() >10);
    }

//    @Test
//    void creatureShouldIncreaseAttackByTravel() {
//        final Creature aAttacker = new TravelBonusCreature(new Creature.Builder()
//                .statistic(CreatureStats.builder()
//                        .maxHp(100)
//                        .damage(Range.closed(10, 10))
//                        .build())
//                .build(), 0.05);
//
//        final Creature aDefender = new Creature.Builder().statistic(CreatureStats.builder()
//                        .maxHp(100)
//                        .damage(NOT_IMPORTANT_DMG)
//                        .build())
//                .build();
//        final List<Creature> c1 = List.of(aAttacker);
//        final List<Creature> c2 = List.of(aDefender);
//        final Board board = new Board(c1, c2);
//
//        board.move(aAttacker, new Point(14, 2));
//        aAttacker.attack(aDefender);
//        assertThat(aDefender.getCurrentHp()).isEqualTo(100);
//    }

    @Test
    void shouldAttackTwice(){
        final Creature attackTwiceCreature = new AttackTwiceCreature(new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build());

        final Creature aDefender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .build())
                .build();
        attackTwiceCreature.attack(aDefender);
        assertThat(aDefender.getCurrentHp()).isEqualTo(80);
    }

    @Test
    void shouldBlockCounterAttack(){
            final Creature attacker = new NoEnemyRetaliationCreature(new Creature.Builder().statistic(CreatureStats.builder()
                            .maxHp(100)
                            .damage(NOT_IMPORTANT_DMG)
                            .attack(0)
                            .armor(0)
                            .build())
                    .build());

            final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                            .maxHp(100)
                            .damage(Range.closed(10, 10))
                            .attack(0)
                            .build())
                    .build();

            // when
            attacker.attack(defender);
            // then
            assertThat(attacker.getCurrentHp()).isEqualTo(100);
        }


}
