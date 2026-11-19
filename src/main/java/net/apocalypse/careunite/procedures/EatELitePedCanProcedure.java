package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class EatELitePedCanProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		DeductPlayerSanityProcedure.execute(entity, 40);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), x, (y + 0.9), z, 24, 0.55, 1, 0.55, 0.1);
		entity.push((Mth.nextDouble(RandomSource.create(), -0.15, 0.15)), (Mth.nextDouble(RandomSource.create(), 0, 0.15)), (Mth.nextDouble(RandomSource.create(), -0.15, 0.15)));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COUNTER_BACK.get(), 600, 1));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get(), 500, 4));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DEDUCT_ONE_SANITY.get(), 200, 0, false, false));
	}
}
