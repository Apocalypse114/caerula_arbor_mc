
package net.apocalypse.careunite.block;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class SaltwindSmoothBrickBlock extends Block {
	public SaltwindSmoothBrickBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.STONE).strength(1f, 2f).requiresCorrectToolForDrops());
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}
}
