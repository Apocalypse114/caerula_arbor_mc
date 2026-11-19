package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.ArrayList;

public class GrandEndspeakerAdvProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			if (EndspeakerEvoCountProcedure.execute(world) >= 3) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.RECORD_UNDERDAWN.get()));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			}
			if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				if ((world.getBlockState(BlockPos.containing(x, y, z))).canBeReplaced()) {
					world.setBlock(BlockPos.containing(x, y, z), CaerulaArborModBlocks.ENDSPEAKER_NEST.get().defaultBlockState(), 3);
				} else if ((world.getBlockState(BlockPos.containing(x, y + 1, z))).canBeReplaced() && !((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == CaerulaArborModBlocks.ENDSPEAKER_NEST.get())) {
					world.setBlock(BlockPos.containing(x, y + 1, z), CaerulaArborModBlocks.ENDSPEAKER_NEST.get().defaultBlockState(), 3);
				}
			}
		}
		CaerulaArborModVariables.MapVariables.get(world).endspeaker_abolities = 0;
		CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if ((entity.level().dimension()) == (entityiterator.level().dimension()) && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 64) {
				if (entityiterator instanceof ServerPlayer _player) {
					Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:silent_interruption"));
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
			}
		}
	}
}
