package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class MartusProtectPlayerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		boolean isCreative = false;
		if (entity instanceof Player _plrCldCheck1 && _plrCldCheck1.getCooldowns().isOnCooldown(itemstack.getItem())) {
			return;
		}
		if (!entity.isAlive()) {
			return;
		}
		isCreative = new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(entity);
		if ((entity instanceof Player _plr ? _plr.experienceLevel : 0) < 5 && !isCreative) {
			return;
		}
		if (entity.tickCount % 5 == 0) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.4) {
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack.getItem(), 1200);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.enchantment_table.use")), SoundSource.PLAYERS, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.enchantment_table.use")), SoundSource.PLAYERS, 2, 1, false);
					}
				}
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5 + 1));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MARTUS_PROTECTION.get(), 400, 0, false, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 40, 9, false, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 500, 5, false, false));
				if (!isCreative) {
					if (entity instanceof Player _player)
						_player.giveExperienceLevels(-(5));
				}
			}
		}
	}
}
