package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.Map;

public class LeaveGrowProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		BlockState targetBlock = Blocks.AIR.defaultBlockState();
		Direction dire = Direction.NORTH;
		double expand = 0;
		double longev = 0;
		boolean drop = false;
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == CaerulaArborModBlocks.TRAIL_LEAVE.get()) {
			return;
		}
		if ((blockstate.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _getip3 ? blockstate.getValue(_getip3) : -1) < 8) {
			{
				int _value = (int) ((blockstate.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _getip5 ? blockstate.getValue(_getip5) : -1) + 1);
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		}
		if ((blockstate.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _getip8 ? blockstate.getValue(_getip8) : -1) < 8) {
			for (Direction directioniterator : Direction.values()) {
				if (Math.random() < 0.2) {
					longev = blockstate.getBlock().getStateDefinition().getProperty("longevity") instanceof IntegerProperty _getip10 ? blockstate.getValue(_getip10) : -1;
					if (longev > 0) {
						if (Math.random() < 0.25) {
							longev = longev - 1;
						}
						dire = directioniterator;
						targetBlock = (world.getBlockState(BlockPos.containing(x + dire.getStepX(), y + dire.getStepY(), z + dire.getStepZ())));
						if (targetBlock.is(BlockTags.create(new ResourceLocation("minecraft:leaves"))) && !(targetBlock.getBlock() == CaerulaArborModBlocks.TRAIL_LEAVE.get())) {
							{
								BlockPos _bp = BlockPos.containing(x + dire.getStepX(), y + dire.getStepY(), z + dire.getStepZ());
								BlockState _bs = (new Object() {
									public BlockState with(BlockState _bs, String _property, int _newValue) {
										Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
										return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
									}
								}.with(CaerulaArborModBlocks.TRAIL_LEAVE.get().defaultBlockState(), "longevity", (int) longev));
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
						}
					}
				}
			}
		} else if ((blockstate.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _getip25 ? blockstate.getValue(_getip25) : -1) >= 8) {
			drop = true;
			for (int index0 = 0; index0 < 64; index0++) {
				targetBlock = (world.getBlockState(BlockPos.containing(x, y - index0 - 1, z)));
				if (world.isEmptyBlock(BlockPos.containing(x, y - index0 - 1, z)) || targetBlock.canBeReplaced()) {
					if (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x, y - index0 - 1, z))) {
						drop = true;
						break;
					}
					continue;
				} else {
					if (targetBlock.getBlock() == CaerulaArborModBlocks.TRAIL_LOG.get() || targetBlock.getBlock() == CaerulaArborModBlocks.STRIPPED_TRAIL_LOG.get()) {
						drop = false;
					}
					if (targetBlock.getBlock() == CaerulaArborModBlocks.NETHERSEA_WOOD.get() || targetBlock.getBlock() == CaerulaArborModBlocks.STRIPPED_NETHERSEA_WOOD.get()) {
						drop = false;
					}
					if (targetBlock.getBlock() == CaerulaArborModBlocks.TRAIL_LEAVE.get()) {
						drop = false;
					}
					if (index0 > 0) {
						drop = false;
					}
					break;
				}
			}
			if (drop) {
				world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
				if (world instanceof ServerLevel _level)
					FallingBlockEntity.fall(_level, BlockPos.containing(x, y, z), CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
			}
		}
	}
}
