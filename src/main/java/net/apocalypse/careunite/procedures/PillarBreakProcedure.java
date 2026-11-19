package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.Map;

public class PillarBreakProcedure {
	public static void execute(LevelAccessor world, double xx, double yy, double zz) {
		double hdns = 0;
		for (int dx = (int) (-1); dx <= (int) 1; dx++) {
			for (int dy = (int) (-1); dy <= (int) 5; dy++) {
				for (int dz = (int) (-1); dz <= (int) 1; dz++) {
					hdns = (world.getBlockState(BlockPos.containing(xx + dx, yy + dy, zz + dz))).getDestroySpeed(world, BlockPos.containing(0, 0, 0));
					if (hdns <= 6 && hdns > 0) {
						world.destroyBlock(BlockPos.containing(xx + dx, yy + dy, zz + dz), false);
					}
				}
			}
		}
		{
			BlockPos _bp = BlockPos.containing(xx, yy - 2, zz);
			BlockState _bs = CaerulaArborModBlocks.TIDEBISHOP_CORE_EMPTY.get().defaultBlockState();
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
