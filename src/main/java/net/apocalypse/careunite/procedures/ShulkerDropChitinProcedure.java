package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.OceanizedShulkerEntity;

public class ShulkerDropChitinProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double v = 0;
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			v = entity instanceof OceanizedShulkerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedShulkerEntity.DATA_VARIANT) : 0;
			if (v == 2) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.COMPLEX_CHITIN.get()));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			} else if (v == 3) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Blocks.BEDROCK));
					entityToSpawn.setPickUpDelay(5);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			}
		}
	}
}
