
package net.apocalypse.careunite.block;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.DamagedByNethseabrandProcedure;

public class NetherseaSoulSandBlock extends Block {
	public NetherseaSoulSandBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.SOUL_SAND).strength(1f, 10f).speedFactor(0.65f).jumpFactor(0.9f));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public void stepOn(Level world, BlockPos pos, BlockState blockstate, Entity entity) {
		super.stepOn(world, pos, blockstate, entity);
		DamagedByNethseabrandProcedure.execute(world, entity);
	}
}
