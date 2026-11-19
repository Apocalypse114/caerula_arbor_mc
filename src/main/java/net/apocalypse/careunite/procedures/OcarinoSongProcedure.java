package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.Difficulty;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import java.util.Map;

public class OcarinoSongProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		BlockState cradle = Blocks.AIR.defaultBlockState();
		boolean found = false;
		double px = 0;
		double py = 0;
		double pz = 0;
		double bs = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ocarino")), SoundSource.PLAYERS, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ocarino")), SoundSource.PLAYERS, 1, 1, false);
			}
		}
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(itemstack.getItem(), 200);
		if (world.getDifficulty() == Difficulty.PEACEFUL) {
			return;
		}
		if (HasItemTypeProcedure.execute(entity)) {
			for (int index0 = 0; index0 < 64; index0++) {
				for (int index1 = 0; index1 < 24; index1++) {
					for (int index2 = 0; index2 < 64; index2++) {
						px = x + -32 + index0;
						py = y + -12 + index1;
						pz = z + -32 + index2;
						cradle = (world.getBlockState(BlockPos.containing(px, py, pz)));
						bs = cradle.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip5 ? cradle.getValue(_getip5) : -1;
						if (cradle.getBlock() == CaerulaArborModBlocks.TIDEWAY_CRADLE.get()) {
							if (bs == 0) {
								{
									int _value = 1;
									BlockPos _pos = BlockPos.containing(px, py, pz);
									BlockState _bs = world.getBlockState(_pos);
									if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
										world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
								}
								if (Math.random() < 0.05) {
									if (world instanceof ServerLevel _level) {
										Entity entityToSpawn = CaerulaArborModEntities.LINGERING_PATHSHAPER.get().spawn(_level, BlockPos.containing(px, py + 1, pz), MobSpawnType.MOB_SUMMONED);
										if (entityToSpawn != null) {
											entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
										}
									}
								} else {
									if (world instanceof ServerLevel _level) {
										Entity entityToSpawn = CaerulaArborModEntities.ROUTE_SHAPER.get().spawn(_level, BlockPos.containing(px, py + 1, pz), MobSpawnType.MOB_SUMMONED);
										if (entityToSpawn != null) {
											entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
										}
									}
								}
								{
									ItemStack _ist = itemstack;
									if (_ist.hurt(1, RandomSource.create(), null)) {
										_ist.shrink(1);
										_ist.setDamageValue(0);
									}
								}
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.language_key.description_15").getString())), false);
								if (entity instanceof Player _player) {
									ItemStack _stktoremove = new ItemStack(CaerulaArborModItems.WHIRL_EYE.get());
									_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
								}
								found = true;
							}
						} else if (cradle.getBlock() == CaerulaArborModBlocks.ENDSPEAKER_NEST.get()) {
							{
								ItemStack _ist = itemstack;
								if (_ist.hurt(1, RandomSource.create(), null)) {
									_ist.shrink(1);
									_ist.setDamageValue(0);
								}
							}
							world.destroyBlock(BlockPos.containing(px, py, pz), false);
							if (entity instanceof Player _player && !_player.level().isClientSide())
								_player.displayClientMessage(Component.literal((Component.translatable("spawn.endspeaker").getString())), false);
							if (entity instanceof Player _player) {
								ItemStack _stktoremove = new ItemStack(CaerulaArborModItems.WHIRL_EYE.get());
								_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
							}
							if (world instanceof ServerLevel _level) {
								Entity entityToSpawn = CaerulaArborModEntities.ENDSPEAKER_0.get().spawn(_level, BlockPos.containing(px, py, pz), MobSpawnType.MOB_SUMMONED);
								if (entityToSpawn != null) {
									entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
								}
							}
						} else if (cradle.getBlock() == CaerulaArborModBlocks.MIZUKI_STATUE.get()) {
							if (bs == 0) {
								{
									int _value = 1;
									BlockPos _pos = BlockPos.containing(px, py, pz);
									BlockState _bs = world.getBlockState(_pos);
									if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
										world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
								}
								{
									ItemStack _ist = itemstack;
									if (_ist.hurt(1, RandomSource.create(), null)) {
										_ist.shrink(1);
										_ist.setDamageValue(0);
									}
								}
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = CaerulaArborModEntities.IZUMIK.get().spawn(_level, BlockPos.containing(px, py, pz), MobSpawnType.MOB_SUMMONED);
									if (entityToSpawn != null) {
										entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
									}
								}
								if (entity instanceof Player _player && !_player.level().isClientSide())
									_player.displayClientMessage(Component.literal((Component.translatable("spawn.izumik.init").getString())), false);
								if (entity instanceof Player _player) {
									ItemStack _stktoremove = new ItemStack(CaerulaArborModItems.WHIRL_EYE.get());
									_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
								}
								found = true;
							}
						} else if (cradle.getBlock() == CaerulaArborModBlocks.HIGHMORE_SPAWNBLOCK.get()) {
							world.setBlock(BlockPos.containing(px, py, pz), CaerulaArborModBlocks.HIGHMORE_SPAWNING_BLOCK.get().defaultBlockState(), 3);
							{
								ItemStack _ist = itemstack;
								if (_ist.hurt(1, RandomSource.create(), null)) {
									_ist.shrink(1);
									_ist.setDamageValue(0);
								}
							}
							if (entity instanceof Player _player && !_player.level().isClientSide())
								_player.displayClientMessage(Component.literal((Component.translatable("spawn.highmore").getString())), false);
							if (entity instanceof Player _player) {
								ItemStack _stktoremove = new ItemStack(CaerulaArborModItems.WHIRL_EYE.get());
								_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
							}
							found = true;
						} else if (cradle.getBlock() == CaerulaArborModBlocks.TIDE_BISHOP_CORE.get()) {
							PillarBreakProcedure.execute(world, px, py, pz);
							if (world instanceof ServerLevel _level) {
								Entity entityToSpawn = CaerulaArborModEntities.TIDE_BISHOP.get().spawn(_level, BlockPos.containing(px + 0.5, py, pz + 0.5), MobSpawnType.MOB_SUMMONED);
								if (entityToSpawn != null) {
									entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
								}
							}
							{
								ItemStack _ist = itemstack;
								if (_ist.hurt(1, RandomSource.create(), null)) {
									_ist.shrink(1);
									_ist.setDamageValue(0);
								}
							}
							if (entity instanceof Player _player && !_player.level().isClientSide())
								_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.language_key.description_16").getString())), false);
							if (entity instanceof Player _player) {
								ItemStack _stktoremove = new ItemStack(CaerulaArborModItems.WHIRL_EYE.get());
								_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
							}
							world.destroyBlock(BlockPos.containing(px, py, pz), false);
							found = true;
						} else if (cradle.getBlock() == CaerulaArborModBlocks.UNDERTIDE_TABLE.get()) {
							if (entity instanceof Player _player && !_player.level().isClientSide())
								_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.a_second_key.description_3").getString())), false);
							if (entity instanceof Player _player) {
								ItemStack _stktoremove = new ItemStack(CaerulaArborModItems.WHIRL_EYE.get());
								_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
							}
							{
								BlockPos _bp = BlockPos.containing(px, py, pz);
								BlockState _bs = CaerulaArborModBlocks.UNDERTIDE_SPAWN.get().defaultBlockState();
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
							found = true;
							if (world instanceof ServerLevel _level) {
								Entity entityToSpawn = CaerulaArborModEntities.BISHOP_FISH.get().spawn(_level, BlockPos.containing(px + 0.5, py, pz + 0.5), MobSpawnType.MOB_SUMMONED);
								if (entityToSpawn != null) {
									entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
								}
							}
						}
					}
					if (found) {
						break;
					}
				}
				if (found) {
					break;
				}
			}
		}
	}
}
