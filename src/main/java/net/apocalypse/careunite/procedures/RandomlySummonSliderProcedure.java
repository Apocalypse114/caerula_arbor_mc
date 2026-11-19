package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.Map;

public class RandomlySummonSliderProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		boolean huge = false;
		huge = true;
		for (int dx = (int) (-1); dx <= (int) 1; dx++) {
			for (int dz = (int) (-1); dz <= (int) 1; dz++) {
				if (!((world.getBlockState(BlockPos.containing(x + dx, y, z + dz))).getBlock() == CaerulaArborModBlocks.VIVIPAROUS_LILY.get())) {
					huge = false;
					break;
				}
			}
			if (!huge) {
				break;
			}
		}
		if (huge) {
			for (int dx = (int) (-1); dx <= (int) 1; dx++) {
				for (int dz = (int) (-1); dz <= (int) 1; dz++) {
					if (!(dx == 0 && dz == 0)) {
						world.setBlock(BlockPos.containing(x + dx, y, z + dz), Blocks.AIR.defaultBlockState(), 3);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fungus.break")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fungus.break")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					}
				}
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, (x + 0.5), y, (z + 0.5), 2, 0.1, 0.1, 0.1, 0.1);
			{
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockState _bs = (new Object() {
					public BlockState with(BlockState _bs, Direction newValue) {
						Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
						if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
							return _bs.setValue(_dp, newValue);
						_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
						return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
					}
				}.with(CaerulaArborModBlocks.HUGE_LILY.get().defaultBlockState(), (new Object() {
					public Direction getDirection(BlockState _bs) {
						Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
						if (_prop instanceof DirectionProperty _dp)
							return _bs.getValue(_dp);
						_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
						return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Direction.Axis
								? Direction.fromAxisAndDirection((Direction.Axis) _bs.getValue(_ep), Direction.AxisDirection.POSITIVE)
								: Direction.NORTH;
					}
				}.getDirection(blockstate))));
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
			if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:trail")))) {
				if (Math.random() < 0.33) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = CaerulaArborModEntities.SLIDER_FISH.get().spawn(_level, BlockPos.containing(x + 0.5, y, z + 0.5), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.puffer_fish.blow_out")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.puffer_fish.blow_out")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
				}
			}
		}
	}
}
