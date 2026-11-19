
package net.apocalypse.careunite.block;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.HarvetBugStoneProcedure;
import net.apocalypse.careunite.procedures.ExpandBugStoneProcedure;

public class NetherseaBuggedStoneBlock extends Block {
	public static final IntegerProperty DURABILITY = IntegerProperty.create("durability", 0, 4);

	public NetherseaBuggedStoneBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sound(SoundType.STONE).strength(1.5f, 3f).requiresCorrectToolForDrops().speedFactor(0.85f).randomTicks());
		this.registerDefaultState(this.stateDefinition.any().setValue(DURABILITY, 4));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DURABILITY);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(DURABILITY, 4);
	}

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		ExpandBugStoneProcedure.execute(world, x, y, z, blockstate);
	}

	@Override
	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
		boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
		HarvetBugStoneProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), entity);
		return retval;
	}
}
