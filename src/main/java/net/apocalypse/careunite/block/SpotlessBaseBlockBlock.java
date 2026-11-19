
package net.apocalypse.careunite.block;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class SpotlessBaseBlockBlock extends Block {
	public SpotlessBaseBlockBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.SLIME_BLOCK).strength(0.5f, 32f).jumpFactor(1.4f));
	}

	@Override
	public float[] getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
		return new float[]{0.9960784314f, 0.9803921569f, 0.8039215686f};
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}
}
