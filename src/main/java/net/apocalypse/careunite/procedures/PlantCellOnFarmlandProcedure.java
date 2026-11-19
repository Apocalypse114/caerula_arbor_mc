package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class PlantCellOnFarmlandProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Direction direction, ItemStack itemstack) {
		if (direction == null)
			return InteractionResult.PASS;
		if (blockstate.getBlock() == CaerulaArborModBlocks.OCEAN_FARMLAND.get() && direction == Direction.UP) {
			if (world.isEmptyBlock(BlockPos.containing(x, y + 1, z))) {
				world.setBlock(BlockPos.containing(x, y + 1, z), CaerulaArborModBlocks.PLANTED_CELL.get().defaultBlockState(), 3);
				itemstack.shrink(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crop.plant")), SoundSource.PLAYERS, 1, Mth.nextInt(RandomSource.create(), (int) 0.85, (int) 1.15));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crop.plant")), SoundSource.PLAYERS, 1, Mth.nextInt(RandomSource.create(), (int) 0.85, (int) 1.15), false);
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
