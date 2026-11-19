package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class SummonLittleHelperOnBlockProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, ItemStack itemstack) {
		if (direction == null)
			return InteractionResult.PASS;
		double tgtX = 0;
		double tgtY = 0;
		double tgtZ = 0;
		tgtX = x + direction.getStepX() + 0.5;
		tgtY = y + direction.getStepY() + 0.5;
		tgtZ = z + direction.getStepZ() + 0.5;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.LITTLE_HELPER.get().spawn(_level, BlockPos.containing(tgtX, tgtY, tgtZ), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:clean_bot_start")), SoundSource.BLOCKS, 3, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:clean_bot_start")), SoundSource.BLOCKS, 3, 1, false);
			}
		}
		itemstack.shrink(1);
		return InteractionResult.SUCCESS;
	}
}
