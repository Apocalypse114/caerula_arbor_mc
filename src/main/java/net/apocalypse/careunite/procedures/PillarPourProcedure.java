package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedPillagerEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class PillarPourProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sklp = 0;
		Entity enemy = null;
		if (!entity.isAlive()) {
			return;
		}
		sklp = entity instanceof OceanizedPillagerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedPillagerEntity.DATA_skillp) : 0;
		if (sklp <= 0) {
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(enemy == null) && enemy.isAlive()) {
				if ((entity instanceof LivingEntity _liveEnt && enemy != null ? _liveEnt.hasLineOfSight(enemy) : false) && (enemy != null ? entity.distanceTo(enemy) : -1) <= 12) {
					if (entity instanceof OceanizedPillagerEntity) {
						((OceanizedPillagerEntity) entity).setAnimation("animation.oceanized_pillager.pour");
					}
					if (entity instanceof OceanizedPillagerEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedPillagerEntity.DATA_skillp, 300);
					new Object() {
						void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
							PillagerShootProcedure.execute(world, x, y, z, entity);
							final int tick2 = ticks;
							CaerulaArborMod.queueServerWork(tick2, () -> {
								if (timedlooptotal > timedloopiterator + 1) {
									timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
								}
							});
						}
					}.timedLoop(0, 10, 4);
				}
			}
		} else {
			if (entity instanceof OceanizedPillagerEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedPillagerEntity.DATA_skillp, (int) (sklp - 1));
		}
		RaiderRideRavagerProcedure.execute(world, x, y, z, entity);
	}
}
