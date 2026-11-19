
package net.apocalypse.careunite.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class IsharmlaBrickGildedBlock extends Block {
	public IsharmlaBrickGildedBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(16f, 5000f).lightLevel(s -> 8).requiresCorrectToolForDrops().speedFactor(1.1f));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}
}
