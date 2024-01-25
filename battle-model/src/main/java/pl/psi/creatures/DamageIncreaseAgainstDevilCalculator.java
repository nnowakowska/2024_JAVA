package pl.psi.creatures;

import java.util.Random;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
class DamageIncreaseAgainstDevilCalculator extends AbstractCalculateDamageStrategy
{
    private final double factor;
    public DamageIncreaseAgainstDevilCalculator(double aFactor)
    {
        super( new Random() );
        factor = aFactor;
    }


    @Override
    public int getAttack(Creature aAttacker, Creature aDefender){
        int aAttack = aAttacker.getAttack();
        if((aDefender.getName().toLowerCase().contains("behemoth"))){
            aAttack *= factor;
        }
        return (int) aAttack;
    }

}

