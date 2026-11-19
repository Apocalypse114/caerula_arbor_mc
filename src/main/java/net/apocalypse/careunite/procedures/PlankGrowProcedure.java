package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.Map;

public class PlankGrowProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		BlockState targetBlock = Blocks.AIR.defaultBlockState();
		Direction dire = Direction.NORTH;
		double expand = 0;
		double longev = 0;
		if (Math.random() < 0.5) {
			for (Direction directioniterator : Direction.values()) {
				if (Math.random() < 0.2) {
					longev = blockstate.getBlock().getStateDefinition().getProperty("longevity") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1;
					if (longev > 0) {
						if (Math.random() < 0.5) {
							longev = longev - 1;
						}
						dire = directioniterator;
						targetBlock = (world.getBlockState(BlockPos.containing(x + dire.getStepX(), y + dire.getStepY(), z + dire.getStepZ())));
						if (targetBlock.is(BlockTags.create(new ResourceLocation("minecraft:logs"))) && !targetBlock.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:cannot_cover")))) {
							{
								BlockPos _bp = BlockPos.containing(x + dire.getStepX(), y + dire.getStepY(), z + dire.getStepZ());
								BlockState _bs = (new Object() {
									public BlockState with(BlockState _bs, Direction newValue) {
										Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
										if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
											return _bs.setValue(_dp, newValue);
										_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
										return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
									}
								}.with((new Object() {
									public BlockState with(BlockState _bs, String _property, int _newValue) {
										Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
										return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
									}
								}.with(CaerulaArborModBlocks.TRAIL_LOG.get().defaultBlockState(), "longevity", (int) longev)), dire));
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
						} else if (targetBlock.is(BlockTags.create(new ResourceLocation("minecraft:leaves"))) && !(targetBlock.getBlock() == CaerulaArborModBlocks.TRAIL_LEAVE.get())) {
							{
								BlockPos _bp = BlockPos.containing(x + dire.getStepX(), y + dire.getStepY(), z + dire.getStepZ());
								BlockState _bs = CaerulaArborModBlocks.TRAIL_LEAVE.get().defaultBlockState();
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
						} else if (targetBlock.getBlock() == Blocks.VINE) {
							world.destroyBlock(BlockPos.containing(x + dire.getStepX(), y + dire.getStepY(), z + dire.getStepZ()), false);
						}
					}
				}
			}
		}
	}
}
