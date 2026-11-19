package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.Map;

public class BurndownTrailProcedure {
	public static boolean execute(LevelAccessor world, BlockState toBeBurn, double px, double py, double pz) {
		BlockState output = Blocks.AIR.defaultBlockState();
		boolean success = false;
		boolean watered = false;
		if (toBeBurn.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_INIT.get() || toBeBurn.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWING.get()) {
			output = Blocks.AIR.defaultBlockState();
			success = true;
		} else if (toBeBurn.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWN.get() || toBeBurn.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_STOP.get()) {
			output = (new Object() {
				public BlockState with(BlockState _bs, String _property, int _newValue) {
					Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
					return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
				}
			}.with(CaerulaArborModBlocks.SEA_TRAIL_BURNT.get().defaultBlockState(), "longevity", toBeBurn.getBlock().getStateDefinition().getProperty("longevity") instanceof IntegerProperty _getip4 ? toBeBurn.getValue(_getip4) : -1));
			success = true;
		} else if (toBeBurn.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_SOLID.get() || toBeBurn.getBlock() == CaerulaArborModBlocks.TRAIL_PULSE.get()) {
			output = CaerulaArborModBlocks.SEA_TRAIL_BURNT_SOLID.get().defaultBlockState();
			success = true;
		}
		watered = toBeBurn.getBlock().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _getbp8 && toBeBurn.getValue(_getbp8);
		if (success) {
			if (output.getBlock() == Blocks.AIR) {
				if (watered) {
					{
						BlockPos _bp = BlockPos.containing(px, py, pz);
						BlockState _bs = Blocks.WATER.defaultBlockState();
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
				} else {
					world.setBlock(BlockPos.containing(px, py, pz), Blocks.AIR.defaultBlockState(), 3);
				}
			} else {
				{
					BlockPos _bp = BlockPos.containing(px, py, pz);
					BlockState _bs = output;
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
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(px, py, pz), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.BLOCKS, (float) 0.6, 1);
				} else {
					_level.playLocalSound(px, py, pz, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.BLOCKS, (float) 0.6, 1, false);
				}
			}
		}
		return success;
	}
}
