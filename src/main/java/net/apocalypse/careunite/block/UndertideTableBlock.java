
package net.apocalypse.careunite.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

public class UndertideTableBlock extends Block {
	public UndertideTableBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.LODESTONE).strength(-1, 3600000).lightLevel(s -> 3).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}
}
