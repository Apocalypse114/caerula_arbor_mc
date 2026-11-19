package net.apocalypse.careunite.procedures;

import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.TheLastKnightEntity;
import net.apocalypse.careunite.entity.LastKnightAndHorseEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class InitLastKnightProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
							? _livingEntity0.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getValue()
							: 0) + 20));
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity3.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
							? _livingEntity2.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getValue()
							: 0) + 60));
		if (entity instanceof TheLastKnightEntity _datEntSetI)
			_datEntSetI.getEntityData().set(TheLastKnightEntity.DATA_duration, 45);
		if (entity instanceof TheLastKnightEntity) {
			((TheLastKnightEntity) entity).setAnimation("animation.last_knight.start");
		}
		if (entity instanceof LastKnightAndHorseEntity) {
			((LastKnightAndHorseEntity) entity).setAnimation("animation.last_knight_horse.start");
		}
		if (entity instanceof LastKnightAndHorseEntity) {
			if (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()))
				_livingEntity9.getAttribute(ForgeMod.SWIM_SPEED.get())
						.setBaseValue(((entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()) ? _livingEntity8.getAttribute(ForgeMod.SWIM_SPEED.get()).getBaseValue() : 0) * 12));
			new Object() {
				void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
					if (entity.isAlive()) {
						if (entity instanceof LivingEntity _entity)
							_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * (1 - (timedloopiterator + 1) * 0.0125)));
					}
					final int tick2 = ticks;
					CaerulaArborMod.queueServerWork(tick2, () -> {
						if (timedlooptotal > timedloopiterator + 1) {
							timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
						}
					});
				}
			}.timedLoop(0, 40, 1);
		} else {
			if (entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()))
				_livingEntity16.getAttribute(ForgeMod.SWIM_SPEED.get())
						.setBaseValue(((entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()) ? _livingEntity15.getAttribute(ForgeMod.SWIM_SPEED.get()).getBaseValue() : 0) * 8));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 45, 9, false, false));
		}
	}
}
