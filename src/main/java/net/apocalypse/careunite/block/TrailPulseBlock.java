
package net.apocalypse.careunite.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.common.IPlantable;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.SpreadTrailRoundProcedure;
import net.apocalypse.careunite.procedures.PokePlayerProcedure;
import net.apocalypse.careunite.procedures.DamagedByNethseabrandProcedure;

public class TrailPulseBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty NURTR = IntegerProperty.create("nurtr", 0, 16);
	public static final IntegerProperty GROW_AGE = IntegerProperty.create("grow_age", 0, 8);

	public TrailPulseBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).sound(SoundType.SCULK).strength(3f, 4f).lightLevel(s -> 2).friction(0.7f).speedFactor(0.6f).jumpFactor(0.875f).randomTicks());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(NURTR, 16).setValue(GROW_AGE, 8));
	}

	@Override
	public float[] getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
		return new float[]{0.2470588235f, 0.3960784314f, 0.5764705882f};
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 6;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, NURTR, GROW_AGE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(NURTR, 16).setValue(GROW_AGE, 8);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	public int getSignal(BlockState blockstate, BlockGetter blockAccess, BlockPos pos, Direction direction) {
		return 15;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 12;
	}

	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction direction, IPlantable plantable) {
		return true;
	}

	@Override
	public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
		return true;
	}

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		SpreadTrailRoundProcedure.execute(world, x, y, z, blockstate);
	}

	@Override
	public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
		boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
		PokePlayerProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), entity);
		return retval;
	}

	@Override
	public void stepOn(Level world, BlockPos pos, BlockState blockstate, Entity entity) {
		super.stepOn(world, pos, blockstate, entity);
		DamagedByNethseabrandProcedure.execute(world, entity);
	}
}
