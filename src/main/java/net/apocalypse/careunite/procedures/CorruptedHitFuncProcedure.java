package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CorruptedHitFuncProcedure {
	@SubscribeEvent
	public static void entityTakesDamage(LivingDamageEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity(), event.getAmount());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity, double amount) {
		execute(null, damagesource, entity, amount);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity, double amount) {
		if (damagesource == null || entity == null)
			return;
		double deal = 0;
		if (entity instanceof SkadiCorruptedEntity && IsCorruptedSourceProcedure.execute(damagesource)) {
			if ((entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_phase) : 0) > 1.5) {
				return;
			}
			deal = (entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_deal) : 0) + amount;
			if (entity instanceof SkadiCorruptedEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_deal, (int) deal);
			if (deal >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.7) {
				if (entity instanceof SkadiCorruptedEntity _datEntSetL)
					_datEntSetL.getEntityData().set(SkadiCorruptedEntity.DATA_mayCorrupt, false);
				if ((entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_phase) : 0) > 0.5) {
					return;
				}
				if (entity instanceof SkadiCorruptedEntity) {
					((SkadiCorruptedEntity) entity).setAnimation("animation.skadi_corrupted.convert_in_1");
				}
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 9999, 9, false, false));
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_duration, 10000);
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_convertTick, 30);
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_convertP, 10000);
			} else {
				if (entity instanceof SkadiCorruptedEntity _datEntSetL)
					_datEntSetL.getEntityData().set(SkadiCorruptedEntity.DATA_mayCorrupt, true);
			}
		}
	}
}
