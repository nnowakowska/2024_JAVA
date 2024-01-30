package pl.psi.creatures;

import com.google.common.collect.Range;
import pl.psi.Point;

import java.beans.PropertyChangeEvent;

public class TravelBonusCreature extends Creature {
    private final Creature decorated;
    private int hexesTravelled;
    private double factor;
    public TravelBonusCreature(final Creature aDecorated, double aFactor) {

        decorated = aDecorated;
        factor = aFactor;
        stats = decorated.getStats();
    }

    @Override
    public CreatureStatisticIf getStats() {
        return decorated.getStats();
    }
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int getAmount() {
        return decorated.getAmount();
    }

    @Override
    public int getCounterAttackCounter() {
        return decorated.getCounterAttackCounter();
    }

    @Override
    public DamageCalculatorIf getCalculator() {
        return decorated.getCalculator();
    }

    @Override
    public void attack(final Creature aDefender) {
        decorated.attack(aDefender);
    }

    @Override
    public boolean isAlive() {
        return decorated.isAlive();
    }

    @Override
    public int getCurrentHp() {
        return decorated.getCurrentHp();
    }

    @Override
    Range<Integer> getDamage() {
        Range<Integer> aDamage = decorated.getDamage();
        double bonus = 1 + (hexesTravelled*factor);
        int lower = (int) (aDamage.lowerEndpoint() *bonus);
        int upper = (int) (aDamage.upperEndpoint() *bonus);
        aDamage = Range.closed(lower, upper);
        return aDamage;
    }

    @Override
    int getAttack() {
        return decorated.getAttack();
    }

    @Override
    int getArmor() {
        return decorated.getArmor();
    }

    @Override
    protected void restoreCurrentHpToMax() {
        decorated.restoreCurrentHpToMax();
    }

    @Override
    public int getMaxHp() {
        return decorated.getMaxHp();
    }

    @Override
    protected void setCurrentHp(int aCurrentHp) {
        decorated.setCurrentHp(aCurrentHp);
    }

    @Override
    public void setAmount(int amount) {
        decorated.setAmount(amount);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {

        decorated.propertyChange(evt);
    }
}
