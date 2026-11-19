package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class OpenBoilProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		if (!(blockstate.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _getbp1 && blockstate.getValue(_getbp1))) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.CANNED_WATER.get()) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
				}
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.EMPTY_CAN.get()).copy();
					_setstack.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount());
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.A_CUP_OF_WATER.get()) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
				}
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.OCEANGLASS_CUP.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			}
		} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.CANNED_WATER.get()
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.A_CUP_OF_WATER.get()) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.kettle.filled").getString())), true);
		}
		if (blockstate.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _getbp25 && blockstate.getValue(_getbp25)
				&& blockstate.getBlock().getStateDefinition().getProperty("boiling") instanceof BooleanProperty _getbp27 && blockstate.getValue(_getbp27)) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.EMPTY_CAN.get()
					&& blockstate.getBlock().getStateDefinition().getProperty("noodled") instanceof BooleanProperty _getbp31 && blockstate.getValue(_getbp31)) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("noodled") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.CANNED_NOODLE.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				return InteractionResult.SUCCESS;
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.EMPTY_CAN.get()
					&& !(blockstate.getBlock().getStateDefinition().getProperty("noodled") instanceof BooleanProperty _getbp41 && blockstate.getValue(_getbp41))) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.CANNED_BOILED_WATER.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				return InteractionResult.SUCCESS;
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.INSTANT_NOODLE.get()
					&& !(blockstate.getBlock().getStateDefinition().getProperty("noodled") instanceof BooleanProperty _getbp50 && blockstate.getValue(_getbp50))) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("noodled") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, true), 3);
				}
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.redstone_torch.burnout")), SoundSource.NEUTRAL, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.redstone_torch.burnout")), SoundSource.NEUTRAL, 2, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.INSTANT_NOODLE.get()) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.kettle.noodled").getString())), true);
			}
		}
		if (blockstate.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _getbp61 && blockstate.getValue(_getbp61)
				&& !(blockstate.getBlock().getStateDefinition().getProperty("boiling") instanceof BooleanProperty _getbp63 && blockstate.getValue(_getbp63))) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.EMPTY_CAN.get()) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.CANNED_WATER.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bottle.fill")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bottle.fill")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.OCEANGLASS_CUP.get()) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.A_CUP_OF_WATER.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bottle.fill")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bottle.fill")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
		if (blockstate.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _getbp81 && blockstate.getValue(_getbp81)
				&& !(blockstate.getBlock().getStateDefinition().getProperty("noodled") instanceof BooleanProperty _getbp83 && blockstate.getValue(_getbp83))) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.SPONGE.asItem()) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(Blocks.WET_SPONGE).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("intentionally_empty")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("intentionally_empty")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.CANNED_LAVA.get()) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.OBISIDIAN_BALL.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.EMPTY_CAN.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.lava.extinguish")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.lava.extinguish")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.REAL_EGG.get()
					&& blockstate.getBlock().getStateDefinition().getProperty("boiling") instanceof BooleanProperty _getbp104 && blockstate.getValue(_getbp104)) {
				{
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("watered") instanceof BooleanProperty _booleanProp)
						world.setBlock(_pos, _bs.setValue(_booleanProp, false), 3);
				}
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(CaerulaArborModItems.BOILED_EGG.get()).copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.brewing_stand.brew")), SoundSource.NEUTRAL, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.brewing_stand.brew")), SoundSource.NEUTRAL, 2, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
