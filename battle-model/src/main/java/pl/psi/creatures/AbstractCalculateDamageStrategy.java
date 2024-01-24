package pl.psi.creatures;

import java.util.Random;

abstract class AbstractCalculateDamageStrategy implements DamageCalculatorIf
{

    public static final int MAX_ATTACK_DIFF = 60;
    public static final int MAX_DEFENCE_DIFF = 12;
    public static final double DEFENCE_BONUS = 0.025;
    public static final double ATTACK_BONUS = 0.05;
    private final Random rand;

    protected AbstractCalculateDamageStrategy( final Random aRand )
    {
        rand = aRand;
    }

    @Override
    public int calculateDamage( final Creature aAttacker, final Creature aDefender )
    {
        final int armor = getArmor( aDefender );

        final int randValue = rand.nextInt( aAttacker.getDamage()
            .upperEndpoint()
            - aAttacker.getDamage()
                .lowerEndpoint()
            + 1 ) + aAttacker.getDamage()
                .lowerEndpoint();

        double oneCreatureDamageToDeal;
        if( getAttack(aAttacker,aDefender) >= armor )
        {
            int attackPoints = getAttack(aAttacker,aDefender) - armor;
            if( attackPoints > MAX_ATTACK_DIFF )
            {
                attackPoints = MAX_ATTACK_DIFF;
            }
            oneCreatureDamageToDeal = randValue * (1 + attackPoints * ATTACK_BONUS);
        }
        else
        {
            int defencePoints = armor - getAttack(aAttacker,aDefender);
            if( defencePoints > MAX_DEFENCE_DIFF )
            {
                defencePoints = MAX_DEFENCE_DIFF;
            }
            oneCreatureDamageToDeal = randValue * (1 - defencePoints * DEFENCE_BONUS);
        }

        if( oneCreatureDamageToDeal < 0 )
        {
            oneCreatureDamageToDeal = 0;
        }
        return (int)(aAttacker.getAmount() * oneCreatureDamageToDeal);
    }

    protected int getArmor( final Creature aDefender )
    {
        return aDefender.getArmor();
    }

    protected int getAttack(final Creature aAttacker, final Creature aDefender){ return aAttacker.getAttack();}
}
