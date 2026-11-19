package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class OnEatAlchemyHyperProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		LivingHeal1Procedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) * 0.35);
		if (entity instanceof Player _player)
			_player.giveExperiencePoints(9);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get(), 300, 1, false, false));
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ALCHEMY_PARTICLE.get()), (entity.getX()), (entity.getY() + 0.8), (entity.getZ()), 24, 0.8, 0.8, 0.8, 0.1);
	}
}
