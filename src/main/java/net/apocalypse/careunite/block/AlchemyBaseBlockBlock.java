
package net.apocalypse.careunite.block;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class AlchemyBaseBlockBlock extends Block {
	public AlchemyBaseBlockBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).sound(SoundType.SLIME_BLOCK).strength(0.5f, 9f).jumpFactor(1.2f));
	}

	@Override
	public float[] getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
		return new float[]{0.8862745098f, 0.9607843137f, 0.9411764706f};
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}
}
