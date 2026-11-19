package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class DistributeAbandonedSculptureProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double r = 0;
		double d = 0;
		double tx = 0;
		double tz = 0;
		double ty = 0;
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
			for (int index0 = 0; index0 < 8; index0++) {
				r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
				d = Mth.nextDouble(RandomSource.create(), 6, 12);
				tx = x + d * Math.cos(r);
				ty = y + Mth.nextDouble(RandomSource.create(), 4, 9);
				tz = z + d * Math.sin(r);
				if ((world.getBlockState(BlockPos.containing(tx, ty, tz))).canBeReplaced()) {
					if (world instanceof ServerLevel _level)
						FallingBlockEntity.fall(_level, BlockPos.containing(tx, ty, tz), (new Object() {
							public BlockState with(BlockState _bs, Direction newValue) {
								Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
								if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
									return _bs.setValue(_dp, newValue);
								_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
								return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
							}
						}.with(CaerulaArborModBlocks.ABANDONED_SULPTURE.get().defaultBlockState(), new Object() {
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
						}.getValue())));
					break;
				}
			}
		}
	}
}
