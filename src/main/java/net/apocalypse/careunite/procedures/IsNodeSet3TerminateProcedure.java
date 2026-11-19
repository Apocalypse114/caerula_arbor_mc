package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class IsNodeSet3TerminateProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return IsNodeRealDamageTo4Procedure.execute(entity) && IsNodeDamageTo4Procedure.execute(entity) && IsNodeWorseBreakTo4Procedure.execute(entity);
	}
}
