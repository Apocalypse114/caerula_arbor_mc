package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModItems;

public class GivePlayerReserveProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double r = 0;
		double exp = 0;
		double r_a = 0;
		boolean creative = false;
		creative = new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(entity);
		if (itemstack.getItem() == CaerulaArborModItems.GENE_SAMPLE_NORMAL.get()) {
			r = 1;
			exp = 1;
		} else if (itemstack.getItem() == CaerulaArborModItems.GENE_SAMPLE_UPGRADED.get()) {
			r = 4;
			exp = 2;
		} else if (itemstack.getItem() == CaerulaArborModItems.GENE_SAMPLE_SUPERB.get()) {
			r_a = 1;
			exp = 3;
		}
		if (exp > 0) {
			if (IsNodesTerminateProcedure.execute(entity)) {
				if (entity instanceof Player _player)
					_player.giveExperienceLevels((int) Math.round(exp * 0.5));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup")), SoundSource.PLAYERS, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup")), SoundSource.PLAYERS, 2, 1, false);
					}
				}
				itemstack.shrink(1);
			} else {
				if ((entity instanceof Player _plr ? _plr.experienceLevel : 0) >= exp || creative) {
					{
						double _setval = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).reserve_quantity + r;
						entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.reserve_quantity = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).reserve_quality + r_a;
						entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.reserve_quality = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (!creative) {
						if (entity instanceof Player _player)
							_player.giveExperienceLevels(-((int) exp));
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup")), SoundSource.PLAYERS, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.experience_orb.pickup")), SoundSource.PLAYERS, 2, 1, false);
						}
					}
					itemstack.shrink(1);
				} else {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.gene_sample.no_exp").getString())), true);
				}
			}
		}
	}
}
