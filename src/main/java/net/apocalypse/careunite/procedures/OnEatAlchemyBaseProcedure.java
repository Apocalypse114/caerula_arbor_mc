package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class OnEatAlchemyBaseProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		LivingHeal1Procedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) * 0.25);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ALCHEMY_PARTICLE.get()), (entity.getX()), (entity.getY() + 0.75), (entity.getZ()), 16, 0.75, 0.75, 0.75, 0.1);
		if (entity instanceof Player _player)
			_player.giveExperiencePoints(4);
	}
}
