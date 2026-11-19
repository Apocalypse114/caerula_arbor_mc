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
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class InjectorUseProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		ItemStack res = ItemStack.EMPTY;
		ItemStack input = ItemStack.EMPTY;
		double stats = 0;
		input = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		stats = blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip2 ? blockstate.getValue(_getip2) : -1;
		if (stats == 0) {
			if (input.getItem() == CaerulaArborModItems.TARGETED_BASE.get()) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.glow_item_frame.add_item")), SoundSource.BLOCKS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.glow_item_frame.add_item")), SoundSource.BLOCKS, 1, 1, false);
					}
				}
				{
					int _value = 1;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				return InteractionResult.SUCCESS;
			} else if (input.is(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:gene")))) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.injector.note").getString())), true);
			}
		} else if (stats == 1) {
			if (input.getItem() == CaerulaArborModItems.HUNTER_GENE_SKADI.get()) {
				res = new ItemStack(CaerulaArborModItems.TARGETED_TRANSMITTER_SKADI.get()).copy();
			} else if (input.getItem() == CaerulaArborModItems.HUNTER_GENE_ULPIANS.get()) {
				res = new ItemStack(CaerulaArborModItems.TARGETED_TRANSMITTER_ULPIANS.get()).copy();
			} else if (input.getItem() == CaerulaArborModItems.HUNTER_GENE_GLADIIA.get()) {
				res = new ItemStack(CaerulaArborModItems.TARGETED_TRANSMITTER_GLADIIA.get()).copy();
			} else if (input.getItem() == CaerulaArborModItems.TARGETED_BASE.get()) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.injector.have").getString())), true);
			} else if (input.getItem() == CaerulaArborModItems.HUNTER_GENE_SPECTER.get()) {
				res = new ItemStack(CaerulaArborModItems.TARGETED_TRANSMITTER_SPECTER.get()).copy();
			}
			if (!(res.getItem() == ItemStack.EMPTY.getItem())) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				{
					int _value = 0;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.BLOCKS, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.BLOCKS, 2, 1, false);
					}
				}
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), res);
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
