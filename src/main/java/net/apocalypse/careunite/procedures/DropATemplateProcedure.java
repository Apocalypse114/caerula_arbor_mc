package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class DropATemplateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1), z, new ItemStack(CaerulaArborModItems.FLAMARINE_UPGRADE_TEMPLATE.get()));
				entityToSpawn.setPickUpDelay(5);
				_level.addFreshEntity(entityToSpawn);
			}
			for (int index0 = 0; index0 < 4; index0++) {
				if (!world.isClientSide() && world.getServer() != null) {
					for (ItemStack itemstackiterator : world.getServer().getLootData().getLootTable(new ResourceLocation("caerula_arbor_reunite:entities/flamarine_statue"))
							.getRandomItems(new LootParams.Builder((ServerLevel) world).create(LootContextParamSets.EMPTY))) {
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1), z, itemstackiterator);
							entityToSpawn.setPickUpDelay(5);
							_level.addFreshEntity(entityToSpawn);
						}
					}
				}
			}
		}
	}
}
