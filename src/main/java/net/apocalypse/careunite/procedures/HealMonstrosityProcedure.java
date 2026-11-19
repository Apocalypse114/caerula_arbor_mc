package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class HealMonstrosityProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		double scale = 0;
		ItemStack itemmain = ItemStack.EMPTY;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
			return InteractionResult.PASS;
		}
		itemmain = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		if (itemmain.getItem() == Items.COPPER_INGOT || itemmain.getItem() == Items.IRON_INGOT) {
			scale = 0.05;
		} else if (itemmain.getItem() == CaerulaArborModItems.COMPLEX_CHITIN.get()) {
			scale = 0.25;
		} else if (itemmain.getItem() == CaerulaArborModItems.TRAILRITE_NUGGET.get()) {
			scale = 0.25;
		} else if (itemmain.getItem() == CaerulaArborModItems.TRAILRITE.get()) {
			scale = 1;
		}
		if (scale > 0) {
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * scale));
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
			if (!(new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(sourceentity))) {
				(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
