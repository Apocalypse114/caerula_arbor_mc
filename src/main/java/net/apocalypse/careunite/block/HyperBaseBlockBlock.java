
package net.apocalypse.careunite.block;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class HyperBaseBlockBlock extends Block {
	public HyperBaseBlockBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_MAGENTA).sound(SoundType.SLIME_BLOCK).strength(0.5f, 24f).jumpFactor(1.3f));
	}

	@Override
	public float[] getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
		return new float[]{1f, 0.9058823529f, 1f};
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}
}
