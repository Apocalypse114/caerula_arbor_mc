package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class ReleaseMortarContainProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
		double core = 0;
		double gunpowder = 0;
		gunpowder = Math.min(itemstack.getOrCreateTag().getDouble("gunpowder"), 512);
		core = Math.min(itemstack.getOrCreateTag().getDouble("core"), 64);
		for (int index0 = 0; index0 < (int) gunpowder; index0++) {
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, (x + Mth.nextInt(RandomSource.create(), -2, 2)), y, (z + Mth.nextInt(RandomSource.create(), -2, 2)), new ItemStack(Items.GUNPOWDER));
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
		}
		for (int index1 = 0; index1 < (int) core; index1++) {
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, (x + Mth.nextInt(RandomSource.create(), -2, 2)), y, (z + Mth.nextInt(RandomSource.create(), -2, 2)), new ItemStack(CaerulaArborModItems.TRAIL_POWDER_CORE.get()));
				entityToSpawn.setPickUpDelay(10);
				entityToSpawn.setUnlimitedLifetime();
				_level.addFreshEntity(entityToSpawn);
			}
		}
		itemstack.shrink(1);
	}
}
