package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class RandomDireProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		{
			Direction _dir = new Object() {
				public Direction getValue() {
					Direction _dir = Direction.NORTH;
					int _num = Mth.nextInt(RandomSource.create(), 1, 4);
					if (_num == 1) {
						_dir = Direction.EAST;
					} else if (_num == 2) {
						_dir = Direction.SOUTH;
					} else if (_num == 3) {
						_dir = Direction.WEST;
					}
					return _dir;
				}
			}.getValue();
			BlockPos _pos = BlockPos.containing(x, y, z);
			BlockState _bs = world.getBlockState(_pos);
			Property<?> _property = _bs.getBlock().getStateDefinition().getProperty("facing");
			if (_property instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(_dir)) {
				world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
			} else {
				_property = _bs.getBlock().getStateDefinition().getProperty("axis");
				if (_property instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis()))
					world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
			}
		}
	}
}
