package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class IsNodeSet1DoneProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return IsNodeAddDefTo4Procedure.execute(entity) || IsNodeAddResisTo4Procedure.execute(entity) || IsNodeAddSpeedTo4Procedure.execute(entity) || IsNodeAddSanityTo4Procedure.execute(entity);
	}
}
