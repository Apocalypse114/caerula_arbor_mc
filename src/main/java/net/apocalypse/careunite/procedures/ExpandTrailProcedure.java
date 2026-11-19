package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.Map;
import java.util.List;
import java.util.Comparator;

public class ExpandTrailProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		Direction dire = Direction.NORTH;
		BlockState blocktoplace = Blocks.AIR.defaultBlockState();
		BlockState targetB = Blocks.AIR.defaultBlockState();
		BlockState liquid = Blocks.AIR.defaultBlockState();
		boolean valid = false;
		boolean change = false;
		boolean watered = false;
		boolean shoulReplace = false;
		double direc = 0;
		double dx = 0;
		double dz = 0;
		double longev = 0;
		double expand = 0;
		double rand = 0;
		double aaa = 0;
		aaa = blockstate.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1;
		if (aaa < 8) {
			expand = 1;
			if (aaa < 5) {
				if (world.getLevelData().isThundering() || IfSilenceProcedure.execute(world)) {
					expand = 2;
				}
			}
			valid = true;
			{
				final Vec3 _center = new Vec3((x + 0.5), (y + 0.5), (z + 0.5));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(0.6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) > 5) {
						valid = false;
						break;
					}
				}
			}
			if (Math.random() * 100 < (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.SPREAD_RATE))) {
				longev = blockstate.getBlock().getStateDefinition().getProperty("longevity") instanceof IntegerProperty _getip7 ? blockstate.getValue(_getip7) : -1;
				if (valid && aaa > 0 && longev > 0) {
					if (IfSilenceProcedure.execute(world)) {
						expand = 1;
					}
					direc = Mth.nextInt(RandomSource.create(), 0, 3);
					if (direc == 0) {
						dx = 0;
						dz = 1;
					} else if (direc == 1) {
						dx = 0;
						dz = -1;
					} else if (direc == 2) {
						dx = 1;
						dz = 0;
					} else {
						dx = -1;
						dz = 0;
					}
					if (Math.random() < 0.33) {
						longev = longev - 1;
					}
					targetB = (world.getBlockState(BlockPos.containing(x + dx, y, z + dz)));
					if (targetB.is(BlockTags.create(new ResourceLocation("minecraft:logs"))) && !(targetB.getBlock() == CaerulaArborModBlocks.TRAIL_LOG.get() || targetB.getBlock() == CaerulaArborModBlocks.STRIPPED_TRAIL_LOG.get())) {
						{
							BlockPos _bp = BlockPos.containing(x + dx, y, z + dz);
							BlockState _bs = (new Object() {
								public BlockState with(BlockState _bs, Direction newValue) {
									Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
									if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
										return _bs.setValue(_dp, newValue);
									_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
									return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
								}
							}.with(CaerulaArborModBlocks.TRAIL_LOG.get().defaultBlockState(), (new Object() {
								public Direction getDirection(BlockState _bs) {
									Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
									if (_prop instanceof DirectionProperty _dp)
										return _bs.getValue(_dp);
									_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
									return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Direction.Axis
											? Direction.fromAxisAndDirection((Direction.Axis) _bs.getValue(_ep), Direction.AxisDirection.POSITIVE)
											: Direction.NORTH;
								}
							}.getDirection((world.getBlockState(BlockPos.containing(x + dx, y, z + dz)))))));
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
					} else if (targetB.is(BlockTags.create(new ResourceLocation("minecraft:leaves"))) && !(targetB.getBlock() == CaerulaArborModBlocks.TRAIL_LEAVE.get())) {
						world.setBlock(BlockPos.containing(x + dx, y, z + dz),
								(CaerulaArborModBlocks.TRAIL_LEAVE.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp19
										? CaerulaArborModBlocks.TRAIL_LEAVE.get().defaultBlockState().setValue(_withbp19, false)
										: CaerulaArborModBlocks.TRAIL_LEAVE.get().defaultBlockState()),
								3);
						world.levelEvent(2001, BlockPos.containing(x + dx, y, z + dz), Block.getId(CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState()));
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.place")), SoundSource.NEUTRAL, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.place")), SoundSource.NEUTRAL, 1, 1, false);
							}
						}
					} else if (IsOrganicProcedure.execute(targetB) && !(targetB.getBlock() == CaerulaArborModBlocks.TRAIL_PULSE.get())) {
						world.setBlock(BlockPos.containing(x + dx, y, z + dz), CaerulaArborModBlocks.TRAIL_PULSE.get().defaultBlockState(), 3);
						world.levelEvent(2001, BlockPos.containing(x + dx, y, z + dz), Block.getId(CaerulaArborModBlocks.TRAIL_PULSE.get().defaultBlockState()));
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.place")), SoundSource.NEUTRAL, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.place")), SoundSource.NEUTRAL, 1, 1, false);
							}
						}
					} else {
						blocktoplace = (new Object() {
							public BlockState with(BlockState _bs, String _property, int _newValue) {
								Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
								return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
							}
						}.with((new Object() {
							public BlockState with(BlockState _bs, Direction newValue) {
								Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
								if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
									return _bs.setValue(_dp, newValue);
								_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
								return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
							}
						}.with(CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState(), new Object() {
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
						}.getValue())), "longevity", (int) longev));
						for (int index0 = 0; index0 < 3; index0++) {
							if (!(world.getBlockFloorHeight(BlockPos.containing(x + dx, y - 1 + index0, z + dz)) > 0)
									&& !(world.getBlockState(BlockPos.containing(x + dx, y - 1 + index0, z + dz))).is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:cannot_cover")))
									&& CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x + dx, y - 1 + index0, z + dz))) {
								liquid = (world.getFluidState(BlockPos.containing(x + dx, y - 1 + index0, z + dz)).createLegacyBlock());
								shoulReplace = true;
								if (liquid.getBlock() == Blocks.WATER || liquid.getBlock() == Blocks.BUBBLE_COLUMN) {
									watered = true;
								} else if (liquid.getBlock() instanceof LiquidBlock) {
									shoulReplace = false;
								}
								if (shoulReplace) {
									TrailReplaceProcedure.execute(world, blocktoplace, watered, x + dx, y - 1 + index0, z + dz);
								}
							}
						}
					}
				}
			}
			{
				int _value = (int) (aaa + expand);
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		} else {
			targetB = (world.getBlockState(BlockPos.containing(x, y - 1, z)));
			dire = new Object() {
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
			}.getDirection(BlockPos.containing(x, y - 1, z));
			if (!targetB.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:cannot_cover")))) {
				if (targetB.is(BlockTags.create(new ResourceLocation("minecraft:logs")))) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					world.setBlock(BlockPos.containing(x, y - 1, z), (new Object() {
						public BlockState with(BlockState _bs, Direction newValue) {
							Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
							if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
								return _bs.setValue(_dp, newValue);
							_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
							return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
						}
					}.with(CaerulaArborModBlocks.TRAIL_LOG.get().defaultBlockState(), dire)), 3);
					change = true;
				} else if (targetB.getBlock() == Blocks.SOUL_SAND || targetB.getBlock() == Blocks.SOUL_SOIL) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					world.setBlock(BlockPos.containing(x, y - 1, z), CaerulaArborModBlocks.NETHERSEA_SOUL_SAND.get().defaultBlockState(), 3);
					change = true;
				} else if (targetB.is(BlockTags.create(new ResourceLocation("minecraft:base_stone_overworld"))) || targetB.is(BlockTags.create(new ResourceLocation("forge:stone")))) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					if (Math.random() < 0.05) {
						world.setBlock(BlockPos.containing(x, y - 1, z), CaerulaArborModBlocks.NETHERSEA_BUGGED_STONE.get().defaultBlockState(), 3);
					} else {
						world.setBlock(BlockPos.containing(x, y - 1, z), CaerulaArborModBlocks.TRAIL_STONE.get().defaultBlockState(), 3);
					}
					change = true;
				} else if (targetB.is(BlockTags.create(new ResourceLocation("minecraft:planks")))) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					world.setBlock(BlockPos.containing(x, y - 1, z), (new Object() {
						public BlockState with(BlockState _bs, Direction newValue) {
							Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
							if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
								return _bs.setValue(_dp, newValue);
							_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
							return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
						}
					}.with(CaerulaArborModBlocks.TRAIL_PLANK.get().defaultBlockState(), dire)), 3);
					change = true;
				} else if (targetB.getBlock() == Blocks.CARVED_PUMPKIN || targetB.getBlock() == Blocks.JACK_O_LANTERN) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					world.setBlock(BlockPos.containing(x, y - 1, z), (new Object() {
						public BlockState with(BlockState _bs, Direction newValue) {
							Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
							if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
								return _bs.setValue(_dp, newValue);
							_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
							return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
						}
					}.with(CaerulaArborModBlocks.TRAIL_PUMPKING.get().defaultBlockState(), dire)), 3);
					change = true;
				} else if (targetB.is(BlockTags.create(new ResourceLocation("minecraft:leaves")))) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					world.setBlock(BlockPos.containing(x, y - 1, z), (new Object() {
						public BlockState with(BlockState _bs, Direction newValue) {
							Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
							if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
								return _bs.setValue(_dp, newValue);
							_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
							return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
						}
					}.with(CaerulaArborModBlocks.TRAIL_LEAVE.get().defaultBlockState(), dire)), 3);
					change = true;
				} else if (targetB.getBlock() == Blocks.ANCIENT_DEBRIS) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					world.setBlock(BlockPos.containing(x, y - 1, z), (new Object() {
						public BlockState with(BlockState _bs, Direction newValue) {
							Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
							if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
								return _bs.setValue(_dp, newValue);
							_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
							return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
						}
					}.with(CaerulaArborModBlocks.TRAIL_DEBRIS.get().defaultBlockState(), dire)), 3);
					change = true;
				} else if (IsOrganicProcedure.execute(targetB)) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					world.setBlock(BlockPos.containing(x, y - 1, z), (new Object() {
						public BlockState with(BlockState _bs, Direction newValue) {
							Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
							if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
								return _bs.setValue(_dp, newValue);
							_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
							return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
						}
					}.with(CaerulaArborModBlocks.TRAIL_PULSE.get().defaultBlockState(), new Object() {
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
					}.getValue())), 3);
					change = true;
				} else if (targetB.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:errodable")))) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					world.setBlock(BlockPos.containing(x, y - 1, z), (new Object() {
						public BlockState with(BlockState _bs, Direction newValue) {
							Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
							if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
								return _bs.setValue(_dp, newValue);
							_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
							return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
						}
					}.with(CaerulaArborModBlocks.SEA_TRAIL_SOLID.get().defaultBlockState(), new Object() {
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
					}.getValue())), 3);
					change = true;
				}
				if (change) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.break")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.break")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
				}
				blocktoplace = Blocks.AIR.defaultBlockState();
				rand = Math.random();
				if (rand < 0.02) {
					if (Math.random() < 0.12) {
						blocktoplace = CaerulaArborModBlocks.RED_OVARY.get().defaultBlockState();
					} else {
						blocktoplace = CaerulaArborModBlocks.OCEAN_OVARY.get().defaultBlockState();
					}
				} else if (rand < 0.1) {
					if (blockstate.getBlock().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _getbp83 && blockstate.getValue(_getbp83)) {
						blocktoplace = (CaerulaArborModBlocks.DEEP_SEAGRASS.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp84
								? CaerulaArborModBlocks.DEEP_SEAGRASS.get().defaultBlockState().setValue(_withbp84, true)
								: CaerulaArborModBlocks.DEEP_SEAGRASS.get().defaultBlockState());
					} else {
						blocktoplace = CaerulaArborModBlocks.TRAIL_MUSHROOM.get().defaultBlockState();
					}
				} else if (rand < 0.013) {
					blocktoplace = CaerulaArborModBlocks.VIVIPAROUS_LILY.get().defaultBlockState();
				} else {
					blocktoplace = CaerulaArborModBlocks.SEA_TRAIL_STOP.get().defaultBlockState();
				}
				if (!(blocktoplace.getBlock() == Blocks.AIR) && blocktoplace.canSurvive(world, BlockPos.containing(x, y, z))) {
					world.setBlock(BlockPos.containing(x, y, z), blocktoplace, 3);
					if (blocktoplace.getBlock() == CaerulaArborModBlocks.DEEP_SEAGRASS.get()) {
						if (Math.random() < 0.5 && (world.getFluidState(BlockPos.containing(x, y + 1, z)).createLegacyBlock()).getBlock() == Blocks.WATER) {
							world.setBlock(BlockPos.containing(x, y + 1, z),
									(CaerulaArborModBlocks.DEEP_SEAGRASS.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp91
											? CaerulaArborModBlocks.DEEP_SEAGRASS.get().defaultBlockState().setValue(_withbp91, true)
											: CaerulaArborModBlocks.DEEP_SEAGRASS.get().defaultBlockState()),
									3);
						}
					}
				}
			}
		}
	}
}
