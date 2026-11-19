package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class ActivateAnchorByRedstoneProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (!(blockstate.getBlock().getStateDefinition().getProperty("activated") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1))) {
			if ((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == CaerulaArborModBlocks.ANCHOR_UPPER.get() && (new Object() {
				public Direction getDirection(BlockPos pos) {
					BlockState _bs = world.getBlockState(pos);
					Property<?> property = _bs.getBlock().getStateDefinition().getProperty("facing");
					if (property != null && _bs.getValue(property) instanceof Direction _dir)
						return _dir;
					else if (_bs.hasProperty(BlockStateProperties.AXIS))
						return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
					else if (_bs.hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
						return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE);
					return Direction.NORTH;
				}
			}.getDirection(BlockPos.containing(x, y + 1, z))) == (new Object() {
				public Direction getDirection(BlockState _bs) {
					Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
					if (_prop instanceof DirectionProperty _dp)
						return _bs.getValue(_dp);
					_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
					return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Direction.Axis ? Direction.fromAxisAndDirection((Direction.Axis) _bs.getValue(_ep), Direction.AxisDirection.POSITIVE) : Direction.NORTH;
				}
			}.getDirection(blockstate)) && (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == CaerulaArborModBlocks.ANCHOR_LOWER.get() && (new Object() {
				public Direction getDirection(BlockPos pos) {
					BlockState _bs = world.getBlockState(pos);
					Property<?> property = _bs.getBlock().getStateDefinition().getProperty("facing");
					if (property != null && _bs.getValue(property) instanceof Direction _dir)
						return _dir;
					else if (_bs.hasProperty(BlockStateProperties.AXIS))
						return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
					else if (_bs.hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
						return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE);
					return Direction.NORTH;
				}
			}.getDirection(BlockPos.containing(x, y - 1, z))) == (new Object() {
				public Direction getDirection(BlockState _bs) {
					Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
					if (_prop instanceof DirectionProperty _dp)
						return _bs.getValue(_dp);
					_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
					return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Direction.Axis ? Direction.fromAxisAndDirection((Direction.Axis) _bs.getValue(_ep), Direction.AxisDirection.POSITIVE) : Direction.NORTH;
				}
			}.getDirection(blockstate))) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.conduit.activate")), SoundSource.NEUTRAL, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.conduit.activate")), SoundSource.NEUTRAL, 2, 1, false);
					}
				}
				{
					int _value = 1;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("activated") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
				}
			}
		}
	}
}
