package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.CaerulaArborMod;

public class SummonMegachestProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		if ((blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1) == 0) {
			{
				int _value = 1;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			{
				int _value = 1;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("animation") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			if ((new Object() {
				public Direction getDirection(BlockState _bs) {
					Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
					if (_prop instanceof DirectionProperty _dp)
						return _bs.getValue(_dp);
					_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
					return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Direction.Axis ? Direction.fromAxisAndDirection((Direction.Axis) _bs.getValue(_ep), Direction.AxisDirection.POSITIVE) : Direction.NORTH;
				}
			}.getDirection(blockstate)) == Direction.NORTH) {
				CaerulaArborMod.queueServerWork(15, () -> {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.ender_chest.close")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.ender_chest.close")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = CaerulaArborModEntities.MEGA_CHEST.get().spawn(_level, BlockPos.containing(x + 0.5, y, z + 0.5), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(-180);
							entityToSpawn.setYBodyRot(-180);
							entityToSpawn.setYHeadRot(-180);
						}
					}
				});
			} else if ((new Object() {
				public Direction getDirection(BlockState _bs) {
					Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
					if (_prop instanceof DirectionProperty _dp)
						return _bs.getValue(_dp);
					_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
					return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Direction.Axis ? Direction.fromAxisAndDirection((Direction.Axis) _bs.getValue(_ep), Direction.AxisDirection.POSITIVE) : Direction.NORTH;
				}
			}.getDirection(blockstate)) == Direction.SOUTH) {
				CaerulaArborMod.queueServerWork(15, () -> {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.ender_chest.close")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.ender_chest.close")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = CaerulaArborModEntities.MEGA_CHEST.get().spawn(_level, BlockPos.containing(x + 0.5, y, z + 0.5), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
						}
					}
				});
			} else if ((new Object() {
				public Direction getDirection(BlockState _bs) {
					Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
					if (_prop instanceof DirectionProperty _dp)
						return _bs.getValue(_dp);
					_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
					return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Direction.Axis ? Direction.fromAxisAndDirection((Direction.Axis) _bs.getValue(_ep), Direction.AxisDirection.POSITIVE) : Direction.NORTH;
				}
			}.getDirection(blockstate)) == Direction.WEST) {
				CaerulaArborMod.queueServerWork(15, () -> {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.ender_chest.close")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.ender_chest.close")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = CaerulaArborModEntities.MEGA_CHEST.get().spawn(_level, BlockPos.containing(x + 0.5, y, z + 0.5), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(90);
							entityToSpawn.setYBodyRot(90);
							entityToSpawn.setYHeadRot(90);
						}
					}
				});
			} else if (true) {
				CaerulaArborMod.queueServerWork(15, () -> {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.ender_chest.close")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.ender_chest.close")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = CaerulaArborModEntities.MEGA_CHEST.get().spawn(_level, BlockPos.containing(x + 0.5, y, z + 0.5), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(-90);
							entityToSpawn.setYBodyRot(-90);
							entityToSpawn.setYHeadRot(-90);
						}
					}
				});
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
