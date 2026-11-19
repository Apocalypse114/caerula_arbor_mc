
package net.apocalypse.careunite.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.PlantGrowUpProcedure;
import net.apocalypse.careunite.procedures.CanContinueToGrowProcedure;
import net.apocalypse.careunite.procedures.BoneBoostPlantProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;

public class NetherseaPotatoPlantBlock extends Block implements BonemealableBlock {
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 2);

	public NetherseaPotatoPlantBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.CROP).strength(0f, 5f).lightLevel(s -> (new Object() {
			public int getLightLevel() {
				if (s.getValue(BLOCKSTATE) == 1)
					return 4;
				if (s.getValue(BLOCKSTATE) == 2)
					return 6;
				return 2;
			}
		}.getLightLevel())).noCollission().noOcclusion().randomTicks().pushReaction(PushReaction.BLOCK).isRedstoneConductor((bs, br, bp) -> false));
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
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		if (state.getValue(BLOCKSTATE) == 1) {
			return box(3, 0, 3, 13, 6, 13);
		}
		if (state.getValue(BLOCKSTATE) == 2) {
			return box(2, 0, 2, 14, 12, 14);
		}
		return box(4, 0, 4, 12, 4, 12);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BLOCKSTATE);
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		return new ItemStack(CaerulaArborModItems.NETHERSEA_POTATO.get());
	}

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		PlantGrowUpProcedure.execute(world, x, y, z, blockstate);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState blockstate, boolean clientSide) {
		if (worldIn instanceof LevelAccessor world) {
			return CanContinueToGrowProcedure.execute(blockstate);
		}
		return false;
	}

	@Override
	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState blockstate) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState blockstate) {
		BoneBoostPlantProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
	}
}
