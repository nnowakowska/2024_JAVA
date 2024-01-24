package pl.psi.creatures;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DamageIncreaseAgainstDevilCalculatorTest {

    private static final Range<Integer> NOT_IMPORTANT_DMG = Range.closed(0, 0);

    @Test
    void shouldAttackWithIncreasedDamageAgainstCreature(){
        final CastleFactory castleFactory = new CastleFactory();
        final StrongholdFactory strongholdFactory = new StrongholdFactory();

        final Creature angel = castleFactory.create(  false, 7,  1);
        final Creature behemoth = strongholdFactory.create(  false, 7,  1);

        angel.attack(behemoth);

        assertThat(behemoth.getCurrentHp()).isEqualTo(78);
    }
}