package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class ReaperHatchProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if (blockstate.getBlock().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1)) {
			if (Math.random() < 0.2) {
				{
					int _value = (int) ((blockstate.getBlock().getStateDefinition().getProperty("process") instanceof IntegerProperty _getip3 ? blockstate.getValue(_getip3) : -1) + 1);
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("process") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
			}
		}
		if ((blockstate.getBlock().getStateDefinition().getProperty("process") instanceof IntegerProperty _getip6 ? blockstate.getValue(_getip6) : -1) >= 10) {
			{
				int _value = (int) ((blockstate.getBlock().getStateDefinition().getProperty("hatch") instanceof IntegerProperty _getip8 ? blockstate.getValue(_getip8) : -1) + 1);
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("hatch") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			{
				int _value = 0;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("process") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.egg_crack")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.egg_crack")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
		}
		if ((blockstate.getBlock().getStateDefinition().getProperty("hatch") instanceof IntegerProperty _getip13 ? blockstate.getValue(_getip13) : -1) >= 2
				&& (blockstate.getBlock().getStateDefinition().getProperty("process") instanceof IntegerProperty _getip15 ? blockstate.getValue(_getip15) : -1) >= 10) {
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.REAPER_PET.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.egg_hatch")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.egg_hatch")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
		}
		{
			int _value = blockstate.getBlock().getStateDefinition().getProperty("hatch") instanceof IntegerProperty _getip20 ? blockstate.getValue(_getip20) : -1;
			BlockPos _pos = BlockPos.containing(x, y, z);
			BlockState _bs = world.getBlockState(_pos);
			if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
				world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
		}
	}
}
