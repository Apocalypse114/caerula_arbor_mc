package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.AegirMortarEntity;

public class InteractFireMortarProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		ItemStack mainHandItem = ItemStack.EMPTY;
		double itemSize = 0;
		double ammoCommon = 0;
		double ammoSuper = 0;
		double fMode = 0;
		String info = "";
		boolean toWorking = false;
		mainHandItem = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		itemSize = mainHandItem.getCount();
		info = Component.translatable("entity.caerula_arbor_reunite.aegir_mortar.fill").getString();
		if (mainHandItem.getItem() == Items.GUNPOWDER) {
			ammoCommon = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_COMMON_AMMO) : 0;
			if (sourceentity.isShiftKeyDown()) {
				for (int index0 = 0; index0 < (int) itemSize; index0++) {
					if (ammoCommon < 999 && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.GUNPOWDER) {
						if (entity instanceof AegirMortarEntity _datEntSetI)
							_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_COMMON_AMMO, (int) (ammoCommon + 1));
						ammoCommon = ammoCommon + 1;
						(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
					}
				}
			} else {
				if (ammoCommon < 999) {
					if (entity instanceof AegirMortarEntity _datEntSetI)
						_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_COMMON_AMMO, (int) (ammoCommon + 1));
					(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				}
			}
			info = info.replace("<name>", mainHandItem.getDisplayName().getString());
			info = info.replace("<num>", "" + (int) ammoCommon);
			if (sourceentity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(info), true);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_weaponsmith")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_weaponsmith")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			return InteractionResult.SUCCESS;
		} else if (mainHandItem.getItem() == CaerulaArborModItems.TRAIL_POWDER_CORE.get()) {
			ammoSuper = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_SUPER_AMMO) : 0;
			if (sourceentity.isShiftKeyDown()) {
				for (int index1 = 0; index1 < (int) itemSize; index1++) {
					if (ammoSuper < 72 && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.TRAIL_POWDER_CORE.get()) {
						if (entity instanceof AegirMortarEntity _datEntSetI)
							_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_SUPER_AMMO, (int) (ammoSuper + 1));
						ammoSuper = ammoSuper + 1;
						(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
					}
				}
			} else {
				if (ammoSuper < 72) {
					if (entity instanceof AegirMortarEntity _datEntSetI)
						_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_SUPER_AMMO, (int) (ammoSuper + 1));
					ammoSuper = ammoSuper + 1;
					(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				}
			}
			info = info.replace("<name>", mainHandItem.getDisplayName().getString());
			info = info.replace("<num>", "" + (int) ammoSuper);
			if (sourceentity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(info), true);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_weaponsmith")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_weaponsmith")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
			return InteractionResult.SUCCESS;
		} else if (mainHandItem.getItem() == Items.IRON_INGOT || mainHandItem.getItem() == Items.COPPER_INGOT) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.PLAYERS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.PLAYERS, 1, 1, false);
					}
				}
				LivingHeal1Procedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.25);
				(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				return InteractionResult.SUCCESS;
			}
		} else if (mainHandItem.getItem() == CaerulaArborModItems.AEGIR_MORTAR_GUNPOWDER_BOX.get()) {
			ammoCommon = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_COMMON_AMMO) : 0;
			ammoSuper = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_SUPER_AMMO) : 0;
			if (entity instanceof AegirMortarEntity _datEntSetI)
				_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_COMMON_AMMO, (int) Math.min(ammoSuper + mainHandItem.getOrCreateTag().getDouble("gunpowder"), 999));
			if (entity instanceof AegirMortarEntity _datEntSetI)
				_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_SUPER_AMMO, (int) Math.min(ammoSuper + mainHandItem.getOrCreateTag().getDouble("core"), 72));
			(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_weaponsmith")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_weaponsmith")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
			return InteractionResult.SUCCESS;
		} else if (mainHandItem.getItem() instanceof PickaxeItem) {
			if (sourceentity.isShiftKeyDown()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.BLOCKS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.BLOCKS, 1, 1, false);
					}
				}
				MortarOutputGunpowderProcedure.execute(world, x, y, z, entity);
				if (!entity.level().isClientSide())
					entity.discard();
				return InteractionResult.SUCCESS;
			}
		} else if (mainHandItem.getItem() == ItemStack.EMPTY.getItem()) {
			if (sourceentity.isShiftKeyDown()) {
				if ((entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_OPEN_TICK) : 0) <= 0
						&& (entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_CLOSE_TICK) : 0) <= 0
						&& (entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_START_TICK) : 0) <= 0) {
					toWorking = entity instanceof AegirMortarEntity _datEntL68 && _datEntL68.getEntityData().get(AegirMortarEntity.DATA_WORKING);
					if (entity instanceof AegirMortarEntity _datEntSetL)
						_datEntSetL.getEntityData().set(AegirMortarEntity.DATA_WORKING, (!toWorking));
					if (entity instanceof AegirMortarEntity _datEntSetI)
						_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_FIRE_COOLDOWN, 30);
					if (toWorking) {
						if (entity instanceof AegirMortarEntity _datEntSetI)
							_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_CLOSE_TICK, 30);
						if (entity instanceof AegirMortarEntity) {
							((AegirMortarEntity) entity).setAnimation("animation.aegir_mortar.idle_to_sleep");
						}
					} else {
						if (entity instanceof AegirMortarEntity _datEntSetI)
							_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_OPEN_TICK, 30);
						if (entity instanceof AegirMortarEntity) {
							((AegirMortarEntity) entity).setAnimation("animation.aegir_mortar.sleep_to_idle");
						}
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_toolsmith")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_toolsmith")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
					return InteractionResult.SUCCESS;
				}
			} else {
				fMode = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_FIRE_MODE) : 0;
				if (entity instanceof AegirMortarEntity _datEntSetI)
					_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_FIRE_MODE, (int) ((fMode + 1) % 3));
				fMode = (fMode + 1) % 3;
				if (fMode == 0) {
					info = Component.translatable("entity.caerula_arbor_reunite.aegir_mortar.switch_0").getString();
				} else if (fMode == 1) {
					info = Component.translatable("entity.caerula_arbor_reunite.aegir_mortar.switch_1").getString();
				} else if (fMode == 2) {
					info = Component.translatable("entity.caerula_arbor_reunite.aegir_mortar.switch_2").getString();
				}
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(info), true);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_toolsmith")), SoundSource.BLOCKS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.work_toolsmith")), SoundSource.BLOCKS, 1, 1, false);
					}
				}
				return InteractionResult.SUCCESS;
			}
		} else {
			if (mainHandItem.getItem() == Items.AMETHYST_SHARD) {
				if (entity instanceof AegirMortarEntity animatable)
					animatable.setTexture("aegir_mortar104");
				return InteractionResult.SUCCESS;
			} else if (mainHandItem.getItem() == Items.WHEAT_SEEDS) {
				if (entity instanceof AegirMortarEntity animatable)
					animatable.setTexture("aegir_mortar1000");
				return InteractionResult.SUCCESS;
			} else if (mainHandItem.getItem() == Items.WHITE_DYE) {
				if (entity instanceof AegirMortarEntity animatable)
					animatable.setTexture("aegir_mortar");
				return InteractionResult.SUCCESS;
			} else if (mainHandItem.getItem() == Blocks.TNT.asItem()) {
				if (entity instanceof AegirMortarEntity animatable)
					animatable.setTexture("aegir_mortar_ew");
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
