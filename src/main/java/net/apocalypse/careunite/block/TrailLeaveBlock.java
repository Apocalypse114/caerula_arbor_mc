
package net.apocalypse.careunite.block;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.LeaveGrowProcedure;

public class TrailLeaveBlock extends LeavesBlock {
	public static final IntegerProperty LONGEVITY = IntegerProperty.create("longevity", 0, 16);
	public static final IntegerProperty GROW_AGE = IntegerProperty.create("grow_age", 0, 8);

	public TrailLeaveBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.MOSS).strength(0.5f, 1f).speedFactor(0.8f).jumpFactor(0.9f).noOcclusion().randomTicks());
		this.registerDefaultState(this.stateDefinition.any().setValue(LONGEVITY, 16).setValue(GROW_AGE, 0));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 1;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LONGEVITY, GROW_AGE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(LONGEVITY, 16).setValue(GROW_AGE, 0);
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 20;
	}

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		LeaveGrowProcedure.execute(world, x, y, z, blockstate);
	}
}
