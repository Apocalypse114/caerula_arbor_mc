package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.entity.CorrectionalPhalanxyInfantryEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class InfantrySkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sklp1 = 0;
		double sklp2 = 0;
		Entity enemy = null;
		if (entity.isAlive()) {
			sklp1 = entity instanceof CorrectionalPhalanxyInfantryEntity _datEntI ? _datEntI.getEntityData().get(CorrectionalPhalanxyInfantryEntity.DATA_skillp1) : 0;
			sklp2 = entity instanceof CorrectionalPhalanxyInfantryEntity _datEntI ? _datEntI.getEntityData().get(CorrectionalPhalanxyInfantryEntity.DATA_skillp2) : 0;
			if (sklp1 > 0) {
				if (entity instanceof CorrectionalPhalanxyInfantryEntity _datEntSetI)
					_datEntSetI.getEntityData().set(CorrectionalPhalanxyInfantryEntity.DATA_skillp1, (int) (sklp1 - 1));
			}
			if (sklp2 > 0) {
				if (entity instanceof CorrectionalPhalanxyInfantryEntity _datEntSetI)
					_datEntSetI.getEntityData().set(CorrectionalPhalanxyInfantryEntity.DATA_skillp2, (int) (sklp2 - 1));
			} else {
				enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
				if (!(enemy == null)) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 5 && enemy.isAlive()) {
						if (entity instanceof CorrectionalPhalanxyInfantryEntity _datEntSetI)
							_datEntSetI.getEntityData().set(CorrectionalPhalanxyInfantryEntity.DATA_skillp2, 200);
						if (entity instanceof CorrectionalPhalanxyInfantryEntity) {
							((CorrectionalPhalanxyInfantryEntity) entity).setAnimation("animation.correctional_phalanx _infantry.swing");
						}
						entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY()), (enemy.getZ())));
						CaerulaArborMod.queueServerWork(16, () -> {
							if (entity.isAlive()) {
								InfantSwingProcedure.execute(world, x, y, z, entity);
							}
						});
					}
				}
			}
		}
	}
}
