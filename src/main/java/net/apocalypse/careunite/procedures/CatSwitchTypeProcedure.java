package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedCatEntity;

public class CatSwitchTypeProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double time_stamp = 0;
		boolean sneak = false;
		if (entity.isAlive() && entity.tickCount % 10 == 0) {
			time_stamp = entity instanceof OceanizedCatEntity _datEntI ? _datEntI.getEntityData().get(OceanizedCatEntity.DATA_action_time) : 0;
			sneak = entity instanceof OceanizedCatEntity _datEntL3 && _datEntL3.getEntityData().get(OceanizedCatEntity.DATA_stateSneaking);
			if (time_stamp > 0) {
				if (entity instanceof OceanizedCatEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedCatEntity.DATA_action_time, (int) (time_stamp - 1));
			} else if (Math.random() < 0.02) {
				if (sneak) {
					if (entity instanceof OceanizedCatEntity _datEntSetL)
						_datEntSetL.getEntityData().set(OceanizedCatEntity.DATA_stateSneaking, false);
					if (entity instanceof OceanizedCatEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedCatEntity.DATA_action_time, 10);
				} else if (!(entity instanceof Mob _mobEnt7 && _mobEnt7.isAggressive())) {
					if (entity instanceof OceanizedCatEntity _datEntSetL)
						_datEntSetL.getEntityData().set(OceanizedCatEntity.DATA_stateSneaking, true);
					if (entity instanceof OceanizedCatEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedCatEntity.DATA_action_time, 10);
				}
			}
			entity.setShiftKeyDown(sneak);
		}
		if (entity instanceof Mob _mobEnt11 && _mobEnt11.isAggressive()) {
			entity.setShiftKeyDown(false);
			if (entity instanceof OceanizedCatEntity _datEntSetL)
				_datEntSetL.getEntityData().set(OceanizedCatEntity.DATA_stateSneaking, false);
		}
	}
}
