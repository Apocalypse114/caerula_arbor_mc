package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class GeneSampleDropProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		String name = "";
		if (!world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			return;
		}
		if (sourceentity instanceof Player && CanPlayerEvoProcedure.execute(sourceentity)) {
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
				name = "caerula_arbor_reunite:gameplay/gene_sample_drop_boss";
			} else if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanelite")))) {
				name = "caerula_arbor_reunite:gameplay/gene_sample_drop_elite";
			} else if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				name = "caerula_arbor_reunite:gameplay/gene_sample_drop_normal";
			}
			if (!(name).isEmpty()) {
				if (!world.isClientSide() && world.getServer() != null) {
					for (ItemStack itemstackiterator : world.getServer().getLootData().getLootTable(new ResourceLocation((name).toLowerCase(java.util.Locale.ENGLISH)))
							.getRandomItems(new LootParams.Builder((ServerLevel) world).create(LootContextParamSets.EMPTY))) {
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
							entityToSpawn.setPickUpDelay(10);
							entityToSpawn.setUnlimitedLifetime();
							_level.addFreshEntity(entityToSpawn);
						}
					}
				}
			}
		}
	}
}
