package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class RewardEchoRelicProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		ItemStack rrr = ItemStack.EMPTY;
		double lll = 0;
		lll = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).reward_limit;
		if (lll > 0) {
			for (int index0 = 0; index0 < 9; index0++) {
				rrr = new ItemStack((ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:relic_generic"))).getRandomElement(RandomSource.create()).orElseGet(() -> Items.AIR))).copy();
				if (!rrr.is(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:relic_advanced")))) {
					break;
				}
			}
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, rrr);
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
			{
				double _setval = lll - 1;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.reward_limit = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
