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
//    @Override
//    public int calculateDamage(Creature aAttacker, Creature aDefender){
//        int aDamage = calculateDamage(aAttacker, aDefender);
//        if(aDefender.getName().toLowerCase().contains("behemoth")){
//            aDamage *= factor;
//        }
//        return aDamage;
//    }

    @Override
    public int getAttack(Creature aAttacker, Creature aDefender){
        int aAttack = aAttacker.getAttack();
        if((aDefender.getName().toLowerCase().contains("behemoth"))){
            aAttack *= factor;
        }
        return (int) aAttack;
    }

}

