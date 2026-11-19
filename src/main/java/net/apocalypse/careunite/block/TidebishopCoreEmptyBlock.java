
package net.apocalypse.careunite.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.RegainTideBishopCoreProcedure;

public class TidebishopCoreEmptyBlock extends Block {
	public TidebishopCoreEmptyBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.LODESTONE).strength(-1, 3600000).lightLevel(s -> 8).pushReaction(PushReaction.BLOCK));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}

	@Override
	public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
		super.use(blockstate, world, pos, entity, hand, hit);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		double hitX = hit.getLocation().x;
		double hitY = hit.getLocation().y;
		double hitZ = hit.getLocation().z;
		Direction direction = hit.getDirection();
		InteractionResult result = RegainTideBishopCoreProcedure.execute(world, x, y, z, entity);
		return result;
	}
}
