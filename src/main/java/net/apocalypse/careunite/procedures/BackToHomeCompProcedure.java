package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.entity.ComplexChitinGolemEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class BackToHomeCompProcedure {
	public static void execute(double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean root = false;
		double rx = 0;
		double rz = 0;
		double dist = 0;
		double dist1 = 0;
		root = entity instanceof ComplexChitinGolemEntity _datEntL0 && _datEntL0.getEntityData().get(ComplexChitinGolemEntity.DATA_rooted);
		if (!root) {
			if (!(entity.getDisplayName().getString()).equals(entity.getType().getDescription().getString())) {
				if (entity instanceof ComplexChitinGolemEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ComplexChitinGolemEntity.DATA_rootX, (int) Math.round(x));
				if (entity instanceof ComplexChitinGolemEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ComplexChitinGolemEntity.DATA_rootZ, (int) Math.round(z));
				if (entity instanceof ComplexChitinGolemEntity _datEntSetL)
					_datEntSetL.getEntityData().set(ComplexChitinGolemEntity.DATA_rooted, true);
				CaerulaArborMod.LOGGER.info(("Complex Chitin Golem " + entity.getDisplayName().getString() + "has recognize x:" + Math.round(x) + " z:" + Math.round(z) + " as base"));
			}
		} else if (Math.random() < 0.01 && !(entity instanceof Mob _mobEnt8 && _mobEnt8.isAggressive())) {
			rx = x - (entity instanceof ComplexChitinGolemEntity _datEntI ? _datEntI.getEntityData().get(ComplexChitinGolemEntity.DATA_rootX) : 0);
			rz = z - (entity instanceof ComplexChitinGolemEntity _datEntI ? _datEntI.getEntityData().get(ComplexChitinGolemEntity.DATA_rootZ) : 0);
			dist = new Vec3(0, 0, 0).distanceTo(new Vec3(rx, 0, rz));
			if (dist >= 24) {
				dist1 = Mth.nextDouble(RandomSource.create(), 4, 16);
				if (entity instanceof Mob _entity)
					_entity.getNavigation().moveTo(x - rx * dist1 / dist, y, z - rz * dist1 / dist, 1);
			}
		}
	}
}
