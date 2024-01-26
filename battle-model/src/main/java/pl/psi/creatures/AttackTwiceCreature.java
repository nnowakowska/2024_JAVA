package pl.psi.creatures;

import java.beans.PropertyChangeEvent;

import com.google.common.collect.Range;

class AttackTwiceCreature extends Creature {
    private final Creature decorated;

    public AttackTwiceCreature(final Creature aDecorated) {
        decorated = aDecorated;
    }

    @Override
    public CreatureStatisticIf getStats() {
        return decorated.getStats();
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
        if (isAlive()) {
            int damage = getCalculator().calculateDamage(this, aDefender);
            decorated.applyDamage(aDefender, damage);
            if (decorated.canCounterAttack(aDefender)) {
                decorated.counterAttack(aDefender);
            }
            damage = getCalculator().calculateDamage(this, aDefender);
            applyDamage(aDefender, damage);
        }
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
        return decorated.getDamage();
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
