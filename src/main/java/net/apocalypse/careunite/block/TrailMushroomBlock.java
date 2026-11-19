
package net.apocalypse.careunite.block;

import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;

public class TrailMushroomBlock extends MushroomBlock {
	public TrailMushroomBlock() {
        super(BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_BLUE)
                        .noCollission()
                        .instabreak()
                        .sound(SoundType.GRASS)
                        .lightLevel(bs->{return 1;})
                        .pushReaction(PushReaction.DESTROY)
                , TreeFeatures.HUGE_RED_MUSHROOM);
    }

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(world, pos);
		return box(5, 0, 5, 11, 6, 11).move(offset.x, offset.y, offset.z);
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 100;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 60;
	}

	@Override
	public boolean canSurvive(BlockState blockstate, LevelReader worldIn, BlockPos pos) {
		if (worldIn instanceof LevelAccessor world) {
			return world.getBlockState(pos.below()).isFaceSturdy(world, pos.below(), Direction.UP);
		}
		return super.canSurvive(blockstate, worldIn, pos);
	}

}
