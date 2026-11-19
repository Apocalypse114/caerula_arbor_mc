package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class EndspeakerTickFuncProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double missR = 0;
		if (entity.tickCount % 5 == 0) {
			if (InquirybilityProcedure.execute(world, 2) && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.4) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 25, 0, false, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ENDSPEAER_BRANDGUIDE_BUFF.get(), 25, 0));
			}
			if (InquirybilityProcedure.execute(world, 4)) {
				entity.clearFire();
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(CaerulaArborModMobEffects.MUTE.get());
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(MobEffects.WEAKNESS);
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(CaerulaArborModMobEffects.FROZEN.get());
				entity.setTicksFrozen(0);
				if (entity.tickCount % 200 == 0 && !(entity instanceof LivingEntity _livEnt13 && _livEnt13.hasEffect(CaerulaArborModMobEffects.ESSENCE_RESISTANCE.get()))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ESSENCE_RESISTANCE.get(), 200, 2, false, false));
				}
				if (GetSpeedProcedure
						.execute(entity) > (entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED) ? _livingEntity15.getAttribute(Attributes.MOVEMENT_SPEED).getValue() : 0) * 1.25) {
					entity.setDeltaMovement(new Vec3(0, 0, 0));
				}
			}
			if (InquirybilityProcedure.execute(world, 1)) {
				missR = 50;
				if (entity.isOnFire() && !entity.fireImmune()) {
					missR = 0;
				}
				if (entity instanceof LivingEntity _livEnt19 && _livEnt19.hasEffect(CaerulaArborModMobEffects.DIZZY.get()) || entity instanceof LivingEntity _livEnt20 && _livEnt20.hasEffect(CaerulaArborModMobEffects.FROZEN.get())
						|| entity instanceof LivingEntity _livEnt21 && _livEnt21.hasEffect(MobEffects.LEVITATION) || entity instanceof LivingEntity _livEnt22 && _livEnt22.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)
						|| entity instanceof LivingEntity _livEnt23 && _livEnt23.hasEffect(MobEffects.SLOW_FALLING)) {
					missR = 0;
				}
				if (entity instanceof LivingEntity _livingEntity24 && _livingEntity24.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
					_livingEntity24.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(missR);
			}
		}
	}
}
