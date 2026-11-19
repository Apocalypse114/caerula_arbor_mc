package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class MarineDropTombProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity tomb = null;
		double sublm = 0;
		double rate = 0;
		double subs_mig = 0;
		sublm = CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation;
		subs_mig = Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting, CaerulaArborModVariables.MapVariables.get(world).strategy_migration);
		if (sublm > 0 && OnNetherseaBrandProcedure.execute(world, x, y, z) || subs_mig > 0 && subs_mig <= 2 && Math.random() < 0.1 || subs_mig > 2 && Math.random() < 0.25) {
			rate = (subs_mig <= 2) ? 0.3 : 0.5;
			tomb = SpawnLivingTombProcedure.execute(world, x, y, z, true,
					(entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rate,
					(entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity2.getAttribute(Attributes.MAX_HEALTH).getValue() : 0) * rate);
			if (!(tomb == null)) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.CLOUD, x, (y + 0.5), z, 16, 0.5, 0.5, 0.5, 0.1);
			}
		}
	}
}
