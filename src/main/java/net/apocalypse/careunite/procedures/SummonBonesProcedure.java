package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.Map;

public class SummonBonesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (ModGriefSettingsProcedure.execute(world)) {
			if ((world.getBlockState(BlockPos.containing(x, y, z))).canBeReplaced()) {
				{
					BlockPos _bp = BlockPos.containing(x, y, z);
					BlockState _bs = (CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp4
							? CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get().defaultBlockState().setValue(_withbp4, ((world.getFluidState(BlockPos.containing(x, y, z)).createLegacyBlock()).getBlock() == Blocks.WATER))
							: CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get().defaultBlockState());
					BlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
						Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
						if (_property != null && _bs.getValue(_property) != null)
							try {
								_bs = _bs.setValue(_property, (Comparable) entry.getValue());
							} catch (Exception e) {
							}
					}
					world.setBlock(_bp, _bs, 3);
				}
				world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get().defaultBlockState()));
			}
			for (Direction directioniterator : Direction.values()) {
				if (Math.random() < 0.5) {
					if ((world.getBlockState(BlockPos.containing(x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ()))).canBeReplaced()) {
						{
							BlockPos _bp = BlockPos.containing(x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ());
							BlockState _bs = (CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp29
									? CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get().defaultBlockState().setValue(_withbp29,
											((world.getFluidState(BlockPos.containing(x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ())).createLegacyBlock()).getBlock() == Blocks.WATER))
									: CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get().defaultBlockState());
							BlockState _bso = world.getBlockState(_bp);
							for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
								Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
								if (_property != null && _bs.getValue(_property) != null)
									try {
										_bs = _bs.setValue(_property, (Comparable) entry.getValue());
									} catch (Exception e) {
									}
							}
							world.setBlock(_bp, _bs, 3);
						}
						world.levelEvent(2001, BlockPos.containing(x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ()),
								Block.getId(CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get().defaultBlockState()));
					}
				}
			}
		} else if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			if ((world.getBlockState(BlockPos.containing(x, y, z))).canBeReplaced()) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get()));
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
			}
			for (Direction directioniterator : Direction.values()) {
				if (Math.random() < 0.5) {
					if ((world.getBlockState(BlockPos.containing(x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ()))).canBeReplaced()) {
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, (x + directioniterator.getStepX()), (y + directioniterator.getStepY()), (z + directioniterator.getStepZ()), new ItemStack(CaerulaArborModBlocks.WHITE_CHITIN_BLOCK.get()));
							entityToSpawn.setPickUpDelay(10);
							_level.addFreshEntity(entityToSpawn);
						}
					}
				}
			}
		}
		MarineDropTombProcedure.execute(world, x, y, z, entity);
	}
}
