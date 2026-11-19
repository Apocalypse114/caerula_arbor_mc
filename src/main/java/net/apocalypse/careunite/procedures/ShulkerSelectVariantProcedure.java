package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.entity.OceanizedShulkerEntity;

public class ShulkerSelectVariantProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof OceanizedShulkerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedShulkerEntity.DATA_VARIANT) : 0) == 0) {
			if (entity instanceof OceanizedShulkerEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedShulkerEntity.DATA_VARIANT, Mth.nextInt(RandomSource.create(), 0, 1));
		}
	}
}
