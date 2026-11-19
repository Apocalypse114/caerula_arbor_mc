package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.PromotedSquidEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class SquidExplodeSkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double skillP = 0;
		double transT = 0;
		double dura = 0;
		Entity enemy = null;
		if (entity.isAlive()) {
			skillP = entity instanceof PromotedSquidEntity _datEntI ? _datEntI.getEntityData().get(PromotedSquidEntity.DATA_SKILL_P) : 0;
			transT = entity instanceof PromotedSquidEntity _datEntI ? _datEntI.getEntityData().get(PromotedSquidEntity.DATA_TRANS_T) : 0;
			dura = entity instanceof PromotedSquidEntity _datEntI ? _datEntI.getEntityData().get(PromotedSquidEntity.DATA_DURATION) : 0;
			if (dura > 0) {
				if (entity instanceof PromotedSquidEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedSquidEntity.DATA_DURATION, (int) (dura - 1));
			}
			if (transT > 0) {
				if (entity instanceof PromotedSquidEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedSquidEntity.DATA_TRANS_T, (int) (transT - 1));
			}
			if (skillP > 0) {
				if (entity instanceof PromotedSquidEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedSquidEntity.DATA_SKILL_P, (int) (skillP - 1));
			} else if (transT <= 0) {
				enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
				if (!(enemy == null) && enemy.isAlive() && (enemy != null ? entity.distanceTo(enemy) : -1) < 6) {
					if (entity instanceof PromotedSquidEntity _datEntSetI)
						_datEntSetI.getEntityData().set(PromotedSquidEntity.DATA_SKILL_P, 240);
					if (entity instanceof PromotedSquidEntity _datEntSetI)
						_datEntSetI.getEntityData().set(PromotedSquidEntity.DATA_DURATION, 25);
					if (entity instanceof PromotedSquidEntity) {
						((PromotedSquidEntity) entity).setAnimation("blast");
					}
					CaerulaArborMod.queueServerWork(13, () -> {
						if (entity.isAlive()) {
							SquidSquizProcedure.execute(world, x, y, z, entity, false);
						}
					});
				}
			}
		}
	}
}
