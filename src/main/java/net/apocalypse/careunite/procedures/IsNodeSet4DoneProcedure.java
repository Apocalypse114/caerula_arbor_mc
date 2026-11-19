package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class IsNodeSet4DoneProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return IsNodeLessArmorTo4Procedure.execute(entity) || IsNodeLessArmorTo4Procedure.execute(entity);
	}
}
