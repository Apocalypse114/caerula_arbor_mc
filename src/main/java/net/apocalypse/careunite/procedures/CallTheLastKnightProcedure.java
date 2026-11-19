package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.CaerulaArborMod;

public class CallTheLastKnightProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (!world.getBiome(BlockPos.containing(x, y, z)).is(TagKey.create(Registries.BIOME, new ResourceLocation("caerula_arbor_reunite:danger_spawn_biome")))) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("spawn.last_knight.fail_1").getString())), true);
			CaerulaArborMod.queueServerWork(20, () -> {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("spawn.last_knight.fail_2").getString())), true);
			});
			return;
		}
		if (GetSeabornNumProcedure.execute(world, x, y, z) < 6) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("spawn.last_knight.fail_3").getString())), true);
			return;
		}
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal((Component.translatable("spawn.last_knight").getString())), false);
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.THE_LAST_KNIGHT.get().spawn(_level, BlockPos.containing(x + Mth.nextInt(RandomSource.create(), -5, 5), y + 3, z + Mth.nextInt(RandomSource.create(), -5, 5)), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
			}
		}
		itemstack.shrink(1);
	}
}
