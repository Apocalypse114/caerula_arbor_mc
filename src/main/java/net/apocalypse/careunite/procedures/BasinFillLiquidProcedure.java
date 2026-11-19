package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class BasinFillLiquidProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		double bstat = 0;
		ItemStack mainhanditem = ItemStack.EMPTY;
		mainhanditem = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
		if (!!(mainhanditem.getItem() == ItemStack.EMPTY.getItem())) {
			return InteractionResult.PASS;
		}
		bstat = blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip5 ? blockstate.getValue(_getip5) : -1;
		if (bstat == 0) {
			if (mainhanditem.getItem() == CaerulaArborModItems.CANNED_WATER.get()) {
				{
					int _value = 1;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				mainhanditem.shrink(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.EMPTY_CAN.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				return InteractionResult.SUCCESS;
			} else if (mainhanditem.getItem() == CaerulaArborModItems.CANNED_LAVA.get()) {
				{
					int _value = 2;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				mainhanditem.shrink(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty_lava")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty_lava")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.EMPTY_CAN.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				return InteractionResult.SUCCESS;
			}
		} else if (mainhanditem.getItem() == CaerulaArborModItems.EMPTY_CAN.get()) {
			{
				int _value = 0;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			mainhanditem.shrink(1);
			if (bstat == 1) {
				if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:heat")))) {
					if (entity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(CaerulaArborModItems.CANNED_BOILED_WATER.get()).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
				} else {
					if (entity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(CaerulaArborModItems.CANNED_WATER.get()).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
			} else {
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.CANNED_LAVA.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill_lava")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill_lava")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
