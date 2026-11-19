
package net.apocalypse.careunite.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.core.BlockPos;

public class HeteropicBlockBlock extends Block {
	public HeteropicBlockBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).sound(SoundType.GLASS).strength(4f, 6f).lightLevel(s -> 3));
	}

	@Override
	public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidstate) {
		return true;
	}

	@Override
	public float[] getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
		return new float[]{0.6549019608f, 0.9490196078f, 0.9607843137f};
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 8;
	}
}
