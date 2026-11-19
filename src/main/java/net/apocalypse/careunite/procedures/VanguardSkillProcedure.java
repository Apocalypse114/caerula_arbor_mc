package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.entity.CorrectinalPhalaxVanguardEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class VanguardSkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sklp1 = 0;
		double less = 0;
		Entity enemy = null;
		if (entity.isAlive()) {
			sklp1 = entity instanceof CorrectinalPhalaxVanguardEntity _datEntI ? _datEntI.getEntityData().get(CorrectinalPhalaxVanguardEntity.DATA_skillp) : 0;
			if (sklp1 > 0) {
				if (entity instanceof CorrectinalPhalaxVanguardEntity _datEntSetI)
					_datEntSetI.getEntityData().set(CorrectinalPhalaxVanguardEntity.DATA_skillp, (int) (sklp1 - 1));
			} else {
				enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
				if (!(enemy == null)) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 5 && enemy.isAlive()) {
						if (entity instanceof CorrectinalPhalaxVanguardEntity _datEntSetI)
							_datEntSetI.getEntityData().set(CorrectinalPhalaxVanguardEntity.DATA_skillp, 300);
						if (entity instanceof CorrectinalPhalaxVanguardEntity) {
							((CorrectinalPhalaxVanguardEntity) entity).setAnimation("animation.correctional_phalanx _vanguard.swing");
						}
						entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY()), (enemy.getZ())));
						CaerulaArborMod.queueServerWork(11, () -> {
							if (entity.isAlive()) {
								VanguardSwingProcedure.execute(world, x, y, z, entity, 1.8);
							}
						});
						CaerulaArborMod.queueServerWork(21, () -> {
							if (entity.isAlive()) {
								VanguardSwingProcedure.execute(world, x, y, z, entity, 2.1);
							}
						});
					}
				}
			}
			VanguardBuffProcedure.execute(world, x, y, z, entity);
		}
	}
}
