package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class IsNodeSet2DoneProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return IsNodeAddDamageTo4Procedure.execute(entity) || IsNodeLessDamageTo4Procedure.execute(entity) || IsNodeLivingBarrier4Procedure.execute(entity) || IsNodeAddMissTo4Procedure.execute(entity);
	}
}
