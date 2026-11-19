package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class IsNodesTerminateProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return IsNodeSet1TerminateProcedure.execute(entity) && ISNodeSet2TerminateProcedure.execute(entity) && IsNodeSet3TerminateProcedure.execute(entity) && IsNodeEunectesTo4Procedure.execute(entity) && IsNodeLessArmorTo4Procedure.execute(entity);
	}
}
