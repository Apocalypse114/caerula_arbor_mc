package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class DigChestFishProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		double bs = 0;
		bs = blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1;
		if (entity instanceof Player _plr ? _plr.getAbilities().instabuild : false) {
			return;
		}
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
			if (bs == 0) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModBlocks.CHEST_FISH_FOOD.get()));
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
			} else {
				if (bs == 1) {
					for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 3, 4); index0++) {
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.75), (z + 0.5), new ItemStack(CaerulaArborModItems.OCEAN_FIBRE.get()));
							entityToSpawn.setPickUpDelay(5);
							_level.addFreshEntity(entityToSpawn);
						}
					}
					for (int index1 = 0; index1 < Mth.nextInt(RandomSource.create(), 2, 4); index1++) {
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.75), (z + 0.5), new ItemStack(CaerulaArborModItems.CHITIN_COOKIE_RAW.get()));
							entityToSpawn.setPickUpDelay(5);
							_level.addFreshEntity(entityToSpawn);
						}
					}
				} else if (bs == 2) {
					for (int index2 = 0; index2 < Mth.nextInt(RandomSource.create(), 2, 4); index2++) {
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.75), (z + 0.5), new ItemStack(CaerulaArborModItems.CHITIN_COOKIE_RAW.get()));
							entityToSpawn.setPickUpDelay(5);
							_level.addFreshEntity(entityToSpawn);
						}
					}
				}
			}
		}
	}
}
