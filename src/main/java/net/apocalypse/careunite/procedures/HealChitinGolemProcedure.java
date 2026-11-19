package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class HealChitinGolemProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		ItemStack mainHand = ItemStack.EMPTY;
		boolean isLowHealth = false;
		boolean isCreative = false;
		mainHand = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		isLowHealth = (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
		isCreative = new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(sourceentity);
		if (mainHand.getItem() == CaerulaArborModItems.OCEAN_CHITIN.get() && isLowHealth) {
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.25));
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
			if (!isCreative) {
				(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
			}
			return InteractionResult.SUCCESS;
		} else if (mainHand.getItem() == CaerulaArborModItems.CHITIN_INGOT.get() && isLowHealth) {
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5));
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
			if (!isCreative) {
				(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
			}
			return InteractionResult.SUCCESS;
		} else if (mainHand.getItem() == CaerulaArborModBlocks.COMPLEX_CHITIN_BLOCK.get().asItem() && mainHand.getCount() >= 3) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.PLAYERS, (float) 1.5, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.smithing_table.use")), SoundSource.PLAYERS, (float) 1.5, 1, false);
				}
			}
			if (!entity.level().isClientSide())
				entity.discard();
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.COMPLEX_CHITIN_GOLEM.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(entity.getYRot());
					entityToSpawn.setYBodyRot(entity.getYRot());
					entityToSpawn.setYHeadRot(entity.getYRot());
					entityToSpawn.setXRot(entity.getXRot());
				}
			}
			if (!isCreative) {
				(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(3);
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
