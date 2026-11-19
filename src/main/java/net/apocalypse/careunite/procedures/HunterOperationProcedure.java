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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class HunterOperationProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity, double hitX, double hitY, double hitZ) {
		if (entity == null)
			return InteractionResult.PASS;
		double stats = 0;
		String res = "";
		ItemStack item = ItemStack.EMPTY;
		ItemStack output = ItemStack.EMPTY;
		stats = blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1;
		item = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		if (Math.abs(x - hitX) > 1) {
			return InteractionResult.PASS;
		}
		if (Math.abs(y - hitY) > 1) {
			return InteractionResult.PASS;
		}
		if (Math.abs(z - hitZ) > 1) {
			return InteractionResult.PASS;
		}
		if (stats == 0) {
			if (item.getItem() == CaerulaArborModItems.PERSONNEL_TRANSPORTER.get()) {
				if ((item.getOrCreateTag().getString("name")).equals("caerula_arbor_reunite:the_abandoned") || (item.getOrCreateTag().getString("name")).equals("caerula_arbor_reunite:lumen")) {
					{
						int _value = 1;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					return InteractionResult.SUCCESS;
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.operation_table.misuse").getString())), true);
			}
		} else if (stats == 1) {
			if (item.getItem() == CaerulaArborModItems.OPERATION_KIT_SKADI.get()) {
				res = "caerula_arbor_reunite:skadi";
			} else if (item.getItem() == CaerulaArborModItems.OPERATION_KIT_ULPIANS.get()) {
				res = "caerula_arbor_reunite:ulpians";
			} else if (item.getItem() == CaerulaArborModItems.OPERATION_KIT_GLADIIA.get()) {
				res = "caerula_arbor_reunite:gladiia";
			} else if (item.getItem() == CaerulaArborModItems.OPERATION_KIT_SPECTER.get()) {
				res = "caerula_arbor_reunite:specter";
			}
			if (!(res).isEmpty()) {
				if (entity instanceof ServerPlayer _player) {
					Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:to_slain_the_sea"));
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.converted_to_drowned")), SoundSource.BLOCKS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.converted_to_drowned")), SoundSource.BLOCKS, 1, 1, false);
					}
				}
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				output = new ItemStack(CaerulaArborModItems.PERSONNEL_TRANSPORTER.get()).copy();
				output.getOrCreateTag().putString("name", res);
				output.getOrCreateTag().putDouble("perc", 0.5);
				SetUUIDItemTagProcedure.execute(entity, output);
				{
					int _value = 0;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), output);
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			}
		}
		return InteractionResult.PASS;
	}
}
