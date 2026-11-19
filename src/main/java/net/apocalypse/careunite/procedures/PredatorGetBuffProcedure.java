package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class PredatorGetBuffProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity.tickCount % 20 == 0 && (world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:sea_trail")))) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1));
		}
		if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
			_livingEntity4.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(80);
		if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting >= 4) {
			if (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
				_livingEntity5.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(90);
		}
		if (entity.isOnFire() && !entity.fireImmune()) {
			if (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
				_livingEntity8.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(0);
		}
		if (entity instanceof LivingEntity _livEnt9 && _livEnt9.hasEffect(CaerulaArborModMobEffects.DIZZY.get()) || entity instanceof LivingEntity _livEnt10 && _livEnt10.hasEffect(CaerulaArborModMobEffects.FROZEN.get())
				|| entity instanceof LivingEntity _livEnt11 && _livEnt11.hasEffect(MobEffects.LEVITATION) || entity instanceof LivingEntity _livEnt12 && _livEnt12.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)
				|| entity instanceof LivingEntity _livEnt13 && _livEnt13.hasEffect(MobEffects.SLOW_FALLING)) {
			if (entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
				_livingEntity14.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(0);
		}
	}
}
