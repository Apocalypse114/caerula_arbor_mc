package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.Map;

public class RespawnBishopProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == CaerulaArborModBlocks.OCEAN_OVARY.get() || (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == CaerulaArborModBlocks.RED_OVARY.get()) {
			world.destroyBlock(BlockPos.containing(x, y + 1, z), false);
			{
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockState _bs = CaerulaArborModBlocks.UNDERTIDE_TABLE.get().defaultBlockState();
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
