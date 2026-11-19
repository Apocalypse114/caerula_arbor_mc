package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class HandleBalanceProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity) {
		if (direction == null || entity == null)
			return InteractionResult.PASS;
		InteractionResult res = InteractionResult.PASS;
		String output = "";
		double balance = 0;
		double amount = 0;
		balance = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).plauyer_balance;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem()) {
			if (entity.isShiftKeyDown()) {
				output = Component.translatable("block.golden_chalise.inquiry").getString() + "" + Math.round(balance) + " /131072";
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(output), true);
			} else {
				if (direction == Direction.UP) {
					if (balance >= 81) {
						balance = balance - 81;
						{
							double _setval = balance;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.plauyer_balance = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.75), (z + 0.5), new ItemStack(CaerulaArborModBlocks.REDSTONIUM_BLOCK.get()));
							entityToSpawn.setPickUpDelay(10);
							_level.addFreshEntity(entityToSpawn);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_out")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_out")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						res = InteractionResult.SUCCESS;
					} else if (balance >= 9) {
						balance = balance - 9;
						{
							double _setval = balance;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.plauyer_balance = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.75), (z + 0.5), new ItemStack(CaerulaArborModItems.REDSTONIUM.get()));
							entityToSpawn.setPickUpDelay(10);
							_level.addFreshEntity(entityToSpawn);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_out")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_out")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						res = InteractionResult.SUCCESS;
					} else if (balance >= 1) {
						for (int index0 = 0; index0 < (int) balance; index0++) {
							if (world instanceof ServerLevel _level) {
								ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.75), (z + 0.5), new ItemStack(CaerulaArborModItems.REDSTONE_INGOT.get()));
								entityToSpawn.setPickUpDelay(10);
								_level.addFreshEntity(entityToSpawn);
							}
						}
						balance = 0;
						{
							double _setval = balance;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.plauyer_balance = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_out")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_out")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						res = InteractionResult.SUCCESS;
					}
				} else if (!(direction == Direction.DOWN) && balance >= 1) {
					balance = balance - 1;
					{
						double _setval = balance;
						entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.plauyer_balance = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.75), (z + 0.5), new ItemStack(CaerulaArborModItems.REDSTONE_INGOT.get()));
						entityToSpawn.setPickUpDelay(10);
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_out")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_out")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					res = InteractionResult.SUCCESS;
				}
			}
		} else {
			if (balance >= 131072) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("block.golden_chalise.warn").getString())), true);
				res = InteractionResult.PASS;
			} else {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.REDSTONE_INGOT.get()) {
					if (direction == Direction.UP) {
						amount = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount();
						balance = Math.min(balance + amount, 131072);
						{
							double _setval = balance;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.plauyer_balance = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).setCount(0);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						res = InteractionResult.SUCCESS;
					} else {
						balance = balance + 1;
						{
							double _setval = balance;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.plauyer_balance = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						if (entity instanceof Player _player) {
							ItemStack _stktoremove = new ItemStack(CaerulaArborModItems.REDSTONE_INGOT.get());
							_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						res = InteractionResult.SUCCESS;
					}
				} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.REDSTONIUM.get()) {
					if (direction == Direction.UP) {
						amount = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount();
						balance = Math.min(balance + amount * 9, 131072);
						{
							double _setval = balance;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.plauyer_balance = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).setCount(0);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						res = InteractionResult.SUCCESS;
					} else {
						balance = Math.min(balance + 9, 131072);
						{
							double _setval = balance;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.plauyer_balance = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						if (entity instanceof Player _player) {
							ItemStack _stktoremove = new ItemStack(CaerulaArborModItems.REDSTONIUM.get());
							_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						res = InteractionResult.SUCCESS;
					}
				} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModBlocks.REDSTONIUM_BLOCK.get().asItem()) {
					if (direction == Direction.UP) {
						amount = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount();
						balance = Math.min(balance + amount * 81, 131072);
						{
							double _setval = balance;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.plauyer_balance = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).setCount(0);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						res = InteractionResult.SUCCESS;
					} else {
						balance = Math.min(balance + 81, 131072);
						{
							double _setval = balance;
							entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.plauyer_balance = _setval;
								capability.syncPlayerVariables(entity);
							});
						}
						if (entity instanceof Player _player) {
							ItemStack _stktoremove = new ItemStack(CaerulaArborModBlocks.REDSTONIUM_BLOCK.get());
							_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:money_in")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
						res = InteractionResult.SUCCESS;
					}
				}
			}
			if (balance > 128) {
				{
					int _value = 2;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
			} else if (balance > 64) {
				{
					int _value = 1;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
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
		return res;
	}
}
