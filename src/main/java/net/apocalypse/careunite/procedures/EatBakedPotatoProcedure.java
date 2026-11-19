package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class EatBakedPotatoProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		RevivesPlayerLightsProcedure.execute(entity, Mth.nextInt(RandomSource.create(), 1, 7));
		DeductPlayerSanityProcedure.execute(entity, Mth.nextInt(RandomSource.create(), 2, 6));
	}
}
