
package net.apocalypse.careunite.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.EnderinaCoreFuncProcedure;

public class EnderinaCoreBlock extends Block {
	public static final BooleanProperty CAN_SUMMON = BlockStateProperties.CAN_SUMMON;

	public EnderinaCoreBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.METAL).strength(8f, 256f).lightLevel(s -> 12).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK));
		this.registerDefaultState(this.stateDefinition.any().setValue(CAN_SUMMON, false));
	}

	@Override
	public float[] getBeaconColorMultiplier(BlockState state, LevelReader world, BlockPos pos, BlockPos beaconPos) {
		return new float[]{0.7254901961f, 0.2509803922f, 0.9058823529f};
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(CAN_SUMMON);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(CAN_SUMMON, false);
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		world.scheduleTick(pos, this, 40);
	}

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		EnderinaCoreFuncProcedure.execute(world, x, y, z, blockstate);
		world.scheduleTick(pos, this, 40);
	}
}
