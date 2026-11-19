
package net.apocalypse.careunite.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.DedonateTrialProcedure;

import java.util.List;

public class BombTrailerBlock extends Block {
	public BombTrailerBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(4f, 32f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("block.caerula_arbor_reunite.bomb_trailer.description_0"));
		list.add(Component.translatable("block.caerula_arbor_reunite.bomb_trailer.description_1"));
	}

	@Override
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return adjacentBlockState.getBlock() == this ? true : super.skipRendering(state, adjacentBlockState, side);
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
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, fromPos, moving);
		if (world.getBestNeighborSignal(pos) > 0) {
			DedonateTrialProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
		}
	}
}
