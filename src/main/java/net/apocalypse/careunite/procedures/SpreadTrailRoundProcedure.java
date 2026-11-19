package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.Map;

public class SpreadTrailRoundProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		BlockState toPlace = Blocks.AIR.defaultBlockState();
		BlockState toReplace = Blocks.AIR.defaultBlockState();
		boolean put = false;
		double dltx = 0;
		double dltz = 0;
		for (Direction directioniterator : Direction.Plane.HORIZONTAL) {
			for (int dy = (int) 0; dy <= (int) 2; dy++) {
				for (int dist = (int) 1; dist <= (int) 2; dist++) {
					if (Math.random() < 0.25) {
						toReplace = (world.getBlockState(BlockPos.containing(x + directioniterator.getStepX() * dist, y + dy, z + directioniterator.getStepZ() * dist)));
						if (toReplace.canBeReplaced()
								&& CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x + directioniterator.getStepX() * dist, y + dy, z + directioniterator.getStepZ() * dist))) {
							if (toReplace.getBlock() == Blocks.WATER) {
								toPlace = (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp12
										? CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().setValue(_withbp12, true)
										: CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
							} else {
								toPlace = CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState();
							}
							{
								BlockPos _bp = BlockPos.containing(x + directioniterator.getStepX() * dist, y + dy, z + directioniterator.getStepZ() * dist);
								BlockState _bs = (new Object() {
									public BlockState with(BlockState _bs, Direction newValue) {
										Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
										if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
											return _bs.setValue(_dp, newValue);
										_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
										return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
									}
								}.with(toPlace, new Object() {
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
								}.getValue()));
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
							put = true;
							break;
						}
					}
				}
				if (put) {
					break;
				}
			}
			if (put) {
				break;
			}
			if (!put) {
				for (int dist = (int) 1; dist <= (int) 2; dist++) {
					if (Math.random() < 0.25 && CanDropTrailProcedure.execute(world, x + directioniterator.getStepX() * dist, y - 1, z + directioniterator.getStepZ() * dist)) {
						if (world instanceof ServerLevel _level)
							FallingBlockEntity.fall(_level, BlockPos.containing(x + directioniterator.getStepX() * dist, y - 1, z + directioniterator.getStepZ() * dist), CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
						put = true;
						break;
					}
				}
			}
		}
		if (!put) {
			dltx = 1;
			dltz = 1;
			for (int dy = (int) 0; dy >= (int) 2; dy--) {
				if (Math.random() < 0.25) {
					toReplace = (world.getBlockState(BlockPos.containing(x + dltx, y + dy, z + dltz)));
					if (toReplace.canBeReplaced() && CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x + dltx, y + dy, z + dltz))) {
						if (toReplace.getBlock() == Blocks.WATER) {
							toPlace = (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp37
									? CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().setValue(_withbp37, true)
									: CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
						} else {
							toPlace = CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState();
						}
						{
							BlockPos _bp = BlockPos.containing(x + dltx, y + dy, z + dltz);
							BlockState _bs = (new Object() {
								public BlockState with(BlockState _bs, Direction newValue) {
									Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
									if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
										return _bs.setValue(_dp, newValue);
									_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
									return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
								}
							}.with(toPlace, new Object() {
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
							}.getValue()));
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
						put = true;
						break;
					}
				}
			}
			if (Math.random() < 0.25 && CanDropTrailProcedure.execute(world, x + dltx, y - 1, z + dltz)) {
				if (world instanceof ServerLevel _level)
					FallingBlockEntity.fall(_level, BlockPos.containing(x + dltx, y - 1, z + dltz), CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
				put = true;
			}
		}
		if (!put) {
			dltx = 1;
			dltz = -1;
			for (int dy = (int) 0; dy >= (int) 2; dy--) {
				if (Math.random() < 0.25) {
					toReplace = (world.getBlockState(BlockPos.containing(x + dltx, y + dy, z + dltz)));
					if (toReplace.canBeReplaced() && CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x + dltx, y + dy, z + dltz))) {
						if (toReplace.getBlock() == Blocks.WATER) {
							toPlace = (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp48
									? CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().setValue(_withbp48, true)
									: CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
						} else {
							toPlace = CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState();
						}
						{
							BlockPos _bp = BlockPos.containing(x + dltx, y + dy, z + dltz);
							BlockState _bs = (new Object() {
								public BlockState with(BlockState _bs, Direction newValue) {
									Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
									if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
										return _bs.setValue(_dp, newValue);
									_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
									return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
								}
							}.with(toPlace, new Object() {
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
							}.getValue()));
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
						put = true;
						break;
					}
				}
			}
			if (Math.random() < 0.25 && CanDropTrailProcedure.execute(world, x + dltx, y - 1, z + dltz)) {
				if (world instanceof ServerLevel _level)
					FallingBlockEntity.fall(_level, BlockPos.containing(x + dltx, y - 1, z + dltz), CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
				put = true;
			}
		}
		if (!put) {
			dltx = -1;
			dltz = 1;
			for (int dy = (int) 0; dy >= (int) 2; dy--) {
				if (Math.random() < 0.25) {
					toReplace = (world.getBlockState(BlockPos.containing(x + dltx, y + dy, z + dltz)));
					if (toReplace.canBeReplaced() && CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x + dltx, y + dy, z + dltz))) {
						if (toReplace.getBlock() == Blocks.WATER) {
							toPlace = (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp59
									? CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().setValue(_withbp59, true)
									: CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
						} else {
							toPlace = CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState();
						}
						{
							BlockPos _bp = BlockPos.containing(x + dltx, y + dy, z + dltz);
							BlockState _bs = (new Object() {
								public BlockState with(BlockState _bs, Direction newValue) {
									Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
									if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
										return _bs.setValue(_dp, newValue);
									_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
									return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
								}
							}.with(toPlace, new Object() {
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
							}.getValue()));
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
						put = true;
						break;
					}
				}
			}
			if (Math.random() < 0.25 && CanDropTrailProcedure.execute(world, x + dltx, y - 1, z + dltz)) {
				if (world instanceof ServerLevel _level)
					FallingBlockEntity.fall(_level, BlockPos.containing(x + dltx, y - 1, z + dltz), CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
				put = true;
			}
		}
		if (!put) {
			dltx = -1;
			dltz = -1;
			for (int dy = (int) 0; dy >= (int) 2; dy--) {
				if (Math.random() < 0.25) {
					toReplace = (world.getBlockState(BlockPos.containing(x + dltx, y + dy, z + dltz)));
					if (toReplace.canBeReplaced() && CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x + dltx, y + dy, z + dltz))) {
						if (toReplace.getBlock() == Blocks.WATER) {
							toPlace = (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().getStateDefinition().getProperty("waterlogged") instanceof BooleanProperty _withbp70
									? CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().setValue(_withbp70, true)
									: CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
						} else {
							toPlace = CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState();
						}
						{
							BlockPos _bp = BlockPos.containing(x + dltx, y + dy, z + dltz);
							BlockState _bs = (new Object() {
								public BlockState with(BlockState _bs, Direction newValue) {
									Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
									if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
										return _bs.setValue(_dp, newValue);
									_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
									return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
								}
							}.with(toPlace, new Object() {
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
							}.getValue()));
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
						put = true;
						break;
					}
				}
			}
			if (Math.random() < 0.25 && CanDropTrailProcedure.execute(world, x + dltx, y - 1, z + dltz)) {
				if (world instanceof ServerLevel _level)
					FallingBlockEntity.fall(_level, BlockPos.containing(x + dltx, y - 1, z + dltz), CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState());
				put = true;
			}
		}
		if (!put) {
			for (Direction directioniterator : Direction.values()) {
				if (Math.random() < 0.25) {
					if (IsOrganicProcedure.execute(world.getBlockState(BlockPos.containing(x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ())))) {
						{
							BlockPos _bp = BlockPos.containing(x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ());
							BlockState _bs = CaerulaArborModBlocks.TRAIL_PULSE.get().defaultBlockState();
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
						put = true;
						break;
					}
				}
			}
		}
		if ((blockstate.getBlock().getStateDefinition().getProperty("nurtr") instanceof IntegerProperty _getip93 ? blockstate.getValue(_getip93) : -1) <= 0) {
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.break")), SoundSource.BLOCKS, (float) 0.33, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.break")), SoundSource.BLOCKS, (float) 0.33, 1, false);
				}
			}
			if (Math.random() < 0.025) {
				if (Math.random() < 0.012) {
					world.setBlock(BlockPos.containing(x, y, z), CaerulaArborModBlocks.RED_OVARY.get().defaultBlockState(), 3);
				} else {
					world.setBlock(BlockPos.containing(x, y, z), CaerulaArborModBlocks.OCEAN_OVARY.get().defaultBlockState(), 3);
				}
			}
		}
		if ((blockstate.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _getip99 ? blockstate.getValue(_getip99) : -1) <= 0 && Math.random() < 0.33) {
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.break")), SoundSource.BLOCKS, (float) 0.33, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.break")), SoundSource.BLOCKS, (float) 0.33, 1, false);
				}
			}
			if (Math.random() < 0.025) {
				if (Math.random() < 0.012) {
					world.setBlock(BlockPos.containing(x, y, z), CaerulaArborModBlocks.RED_OVARY.get().defaultBlockState(), 3);
				} else {
					world.setBlock(BlockPos.containing(x, y, z), CaerulaArborModBlocks.OCEAN_OVARY.get().defaultBlockState(), 3);
				}
			}
		}
		if (put) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.place")), SoundSource.BLOCKS, (float) 0.33, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.place")), SoundSource.BLOCKS, (float) 0.33, 1, false);
				}
			}
			{
				int _value = (int) ((blockstate.getBlock().getStateDefinition().getProperty("nurtr") instanceof IntegerProperty _getip106 ? blockstate.getValue(_getip106) : -1) - 1);
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("nurtr") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		}
		{
			int _value = (int) ((blockstate.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _getip109 ? blockstate.getValue(_getip109) : -1) - 1);
			BlockPos _pos = BlockPos.containing(x, y, z);
			BlockState _bs = world.getBlockState(_pos);
			if (_bs.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
				world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
		}
	}
}
