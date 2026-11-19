package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.JuniorWarriorPriestEntity;

public class WarriorSkillProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double sklp1 = 0;
		if (entity.isAlive()) {
			sklp1 = entity instanceof JuniorWarriorPriestEntity _datEntI ? _datEntI.getEntityData().get(JuniorWarriorPriestEntity.DATA_skill_p) : 0;
			if (sklp1 > 0) {
				if (entity instanceof JuniorWarriorPriestEntity _datEntSetI)
					_datEntSetI.getEntityData().set(JuniorWarriorPriestEntity.DATA_skill_p, (int) (sklp1 - 1));
			}
		}
	}
}
