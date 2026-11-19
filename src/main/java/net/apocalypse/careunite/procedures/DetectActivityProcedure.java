package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class DetectActivityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		double dx = 0;
		double dy = 0;
		double dz = 0;
		double attr = 0;
		BlockState target = Blocks.AIR.defaultBlockState();
		BlockState beReplaced = Blocks.AIR.defaultBlockState();
		if (blockstate.getBlock().getStateDefinition().getProperty("activated") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1)) {
			{
				int _value = 1;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			if (!((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == CaerulaArborModBlocks.ANCHOR_UPPER.get() && (new Object() {
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
			}.getDirection(blockstate)))) {
				{
					int _value = 0;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("activated") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.conduit.deactivate")), SoundSource.NEUTRAL, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.conduit.deactivate")), SoundSource.NEUTRAL, 2, 1, false);
					}
				}
			}
			for (int index0 = 0; index0 < 24; index0++) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.NAUTILUS, (x + Mth.nextDouble(RandomSource.create(), -36, 37)), y, (z + -36), 4, 0.5, 4, 0.5, 0.1);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.NAUTILUS, (x + Mth.nextDouble(RandomSource.create(), -36, 37)), y, (z + 37), 4, 0.5, 4, 0.5, 0.1);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.NAUTILUS, (x + -36), y, (z + Mth.nextDouble(RandomSource.create(), -36, 25)), 4, 0.5, 4, 0.5, 0.1);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.NAUTILUS, (x + 37), y, (z + Mth.nextDouble(RandomSource.create(), -36, 37)), 4, 0.5, 4, 0.5, 0.1);
			}
			dy = -21 + (blockstate.getBlock().getStateDefinition().getProperty("detect_y") instanceof IntegerProperty _getip27 ? blockstate.getValue(_getip27) : -1);
			for (int index1 = 0; index1 < 73; index1++) {
				for (int index2 = 0; index2 < 73; index2++) {
					dx = index1 - 36;
					dz = index2 - 36;
					target = (world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz)));
					beReplaced = Blocks.BEDROCK.defaultBlockState();
					if (target.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWN.get()) {
						beReplaced = CaerulaArborModBlocks.SEA_TRAIL_STOP.get().defaultBlockState();
					} else if (target.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_INIT.get() || target.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWING.get()) {
						beReplaced = Blocks.AIR.defaultBlockState();
					} else if (target.getBlock() == CaerulaArborModBlocks.TRAIL_PULSE.get()) {
						beReplaced = CaerulaArborModBlocks.SEA_TRAIL_SOLID.get().defaultBlockState();
					}
					if (!(beReplaced.getBlock() == Blocks.BEDROCK)) {
						world.setBlock(BlockPos.containing(x + dx, y + dy, z + dz), beReplaced, 3);
					}
				}
			}
			if (dy >= 22) {
				dy = -22;
			}
			{
				int _value = (int) (dy + 22);
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("detect_y") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			for (Entity entityiterator : world.getEntities(null, new AABB((x + 37), (y + 22), (z + 37), (x - 36), (y - 21), (z - 36)))) {
				if (!(entityiterator instanceof LivingEntity _livEnt36 && _livEnt36.hasEffect(CaerulaArborModMobEffects.POWER_OF_ANCHOR.get()))) {
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.POWER_OF_ANCHOR.get(), 30, 0, false, false));
				}
			}
		} else {
			{
				int _value = 0;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		}
	}
}
