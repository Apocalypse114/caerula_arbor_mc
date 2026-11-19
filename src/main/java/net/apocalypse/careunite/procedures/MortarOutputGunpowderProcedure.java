package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.AegirMortarEntity;

public class MortarOutputGunpowderProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double gunpowder = 0;
		double core = 0;
		ItemStack toDrop = ItemStack.EMPTY;
		gunpowder = Math.min(entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_COMMON_AMMO) : 0, 999);
		core = Math.min(entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_SUPER_AMMO) : 0, 72);
		for (int index0 = 0; index0 < 3; index0++) {
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, (x + Mth.nextInt(RandomSource.create(), -1, 1)), y, (z + Mth.nextInt(RandomSource.create(), -1, 1)), new ItemStack(CaerulaArborModBlocks.MORTAR_MAIN_BLOCK.get()));
				entityToSpawn.setPickUpDelay(10);
				entityToSpawn.setUnlimitedLifetime();
				_level.addFreshEntity(entityToSpawn);
			}
		}
		for (int index1 = 0; index1 < 9; index1++) {
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, (x + Mth.nextInt(RandomSource.create(), -1, 1)), y, (z + Mth.nextInt(RandomSource.create(), -1, 1)), new ItemStack(CaerulaArborModBlocks.MOTAR_BASE_BLOCK.get()));
				entityToSpawn.setPickUpDelay(10);
				entityToSpawn.setUnlimitedLifetime();
				_level.addFreshEntity(entityToSpawn);
			}
		}
		if (gunpowder > 0 || core > 0) {
			toDrop = new ItemStack(CaerulaArborModItems.AEGIR_MORTAR_GUNPOWDER_BOX.get()).copy();
			toDrop.getOrCreateTag().putDouble("gunpowder", gunpowder);
			toDrop.getOrCreateTag().putDouble("core", core);
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, toDrop);
				entityToSpawn.setPickUpDelay(10);
				entityToSpawn.setUnlimitedLifetime();
				_level.addFreshEntity(entityToSpawn);
			}
		}
	}
}
