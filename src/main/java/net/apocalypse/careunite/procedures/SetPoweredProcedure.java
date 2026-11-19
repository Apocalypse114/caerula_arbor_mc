package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class SetPoweredProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (!(blockstate.getBlock().getStateDefinition().getProperty("powered") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1))) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:bell_ring")), SoundSource.BLOCKS, (float) 1.25, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:bell_ring")), SoundSource.BLOCKS, (float) 1.25, 1, false);
				}
			}
		}
		{
			BlockPos _pos = BlockPos.containing(x, y, z);
			BlockState _bs = world.getBlockState(_pos);
			if (_bs.getBlock().getStateDefinition().getProperty("powered") instanceof BooleanProperty _booleanProp)
				world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
		}
	}
}
