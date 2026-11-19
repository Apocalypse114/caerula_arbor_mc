package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.entity.PromotedGlowSquidEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class GlowSquidSkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double skillP = 0;
		double transT = 0;
		double dura = 0;
		Entity enemy = null;
		if (entity.isAlive()) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.GLOW, x, y, z, 1, 0.5, 0.5, 0.5, 0);
			skillP = entity instanceof PromotedGlowSquidEntity _datEntI ? _datEntI.getEntityData().get(PromotedGlowSquidEntity.DATA_SKILL_P) : 0;
			transT = entity instanceof PromotedGlowSquidEntity _datEntI ? _datEntI.getEntityData().get(PromotedGlowSquidEntity.DATA_TRANS_T) : 0;
			dura = entity instanceof PromotedGlowSquidEntity _datEntI ? _datEntI.getEntityData().get(PromotedGlowSquidEntity.DATA_DURATION) : 0;
			if (dura > 0) {
				if (entity instanceof PromotedGlowSquidEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedGlowSquidEntity.DATA_DURATION, (int) (dura - 1));
			}
			if (transT > 0) {
				if (entity instanceof PromotedGlowSquidEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedGlowSquidEntity.DATA_TRANS_T, (int) (transT - 1));
			}
			if (skillP > 0) {
				if (entity instanceof PromotedGlowSquidEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedGlowSquidEntity.DATA_SKILL_P, (int) (skillP - 1));
			} else if (transT <= 0) {
				enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
				if (!(enemy == null) && enemy.isAlive() && (enemy != null ? entity.distanceTo(enemy) : -1) < 6) {
					if (entity instanceof PromotedGlowSquidEntity _datEntSetI)
						_datEntSetI.getEntityData().set(PromotedGlowSquidEntity.DATA_SKILL_P, 240);
					if (entity instanceof PromotedGlowSquidEntity _datEntSetI)
						_datEntSetI.getEntityData().set(PromotedGlowSquidEntity.DATA_DURATION, 25);
					if (entity instanceof PromotedGlowSquidEntity) {
						((PromotedGlowSquidEntity) entity).setAnimation("blast");
					}
					CaerulaArborMod.queueServerWork(13, () -> {
						if (entity.isAlive()) {
							SquidSquizProcedure.execute(world, x, y, z, entity, true);
						}
					});
				}
			}
		}
	}
}
