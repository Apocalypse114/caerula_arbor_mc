package net.apocalypse.careunite.core;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class CaerulaEvents {
	public static class SourcedEvents extends LivingEvent {
        private final DamageSource source;

        public SourcedEvents(LivingEntity entity, DamageSource source) {
            super(entity);
            this.source = source;
        }
    }
    public static class LivingMissEvent extends SourcedEvents {
        private double missRate;

        public LivingMissEvent(LivingEntity entity, DamageSource source, double missRate) {
            super(entity, source);
            this.missRate = missRate;
        }

        public double getMissRate() {
            return missRate;
        }

        public void setMissRate(double value) {
            this.missRate = value;
        }
    }
    public static class LivingBarrierBlockEvent extends SourcedEvents {
        private double amount;

        public LivingBarrierBlockEvent(LivingEntity entity, DamageSource source, double amount) {
            super(entity, source);
            this.amount = amount;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double value) {
            this.amount = value;
        }
    }
    public static class LayeredBarrierBlockEvent extends SourcedEvents {
        private int amount;

        public LayeredBarrierBlockEvent(LivingEntity entity, DamageSource source, int amount) {
            super(entity, source);
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int value) {
            this.amount = value;
        }
    }

    public static class DefenseEvent extends SourcedEvents {
        private double defence;
        private double defence1;

        public DefenseEvent(LivingEntity entity, DamageSource source, double defence) {
            super(entity, source);
            this.defence = defence;
            this.defence1 = defence;
        }

        public double getEffectiveDefence() {
            return this.defence1;
        }

        public void setEffectiveDefence(double value) {
            this.defence1 = Math.max(value, 0);
        }

        public void defencePierceByAmount(double amount) {
            this.setEffectiveDefence(this.defence - amount);
        }

        public void defencePierceByPercentage(double percentage) {
            this.setEffectiveDefence(this.defence * (1 - percentage));
        }
    }

    public static class MagicResistanceEvent extends SourcedEvents {
        private double magicResistance;
        private double magicResistanceE;

        public MagicResistanceEvent(LivingEntity entity, DamageSource source, double magicResistance) {
            super(entity, source);
            this.magicResistance = magicResistance;
            this.magicResistanceE = magicResistance;
        }

        public double getEffectiveMagicResistance() {
            return this.magicResistanceE;
        }

        public void setEffectiveMagicResistance(double value) {
            this.magicResistanceE = Mth.clamp(value, 0, 100);
        }

        public void resistancePierceByAmount(double amount) {
            this.setEffectiveMagicResistance(this.magicResistance - amount);
        }

        public void resistancePierceByPercentage(double percentage) {
            this.setEffectiveMagicResistance(this.magicResistance * (1 - percentage));
        }
    }
    public static class SanityEvent extends LivingEvent{
        private double amount;
        public SanityEvent(LivingEntity entity, double amount) {
            super(entity);
            this.amount = amount;
        }
        public void setAmount(double amount){this.amount = amount;}
        public double getAmount(){return this.amount;}

        public class Hurt extends SanityEvent{
            public Hurt(LivingEntity entity, double amount) {super(entity, amount);}
        }
        public class Heal extends SanityEvent{
            public Heal(LivingEntity entity, double amount) {super(entity, amount);}
        }
        @Cancelable
        public class Break extends SanityEvent{
            public Break(LivingEntity entity) {super(entity, 0);}
        }
    }
}
