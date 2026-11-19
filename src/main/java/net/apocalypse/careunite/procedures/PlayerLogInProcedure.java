package net.apocalypse.careunite.procedures;

import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlayerLogInProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double nowLvl = 0;
		String notice = "";
		if (entity.getPersistentData().getBoolean("caerula_firstLoginDone")) {
			entity.getPersistentData().putBoolean("caerula_firstLoginDone", true);
			if (CaerulaConfigsConfiguration.RELIC_BAN.get()) {
				if (!(entity instanceof ServerPlayer _plr3 && _plr3.level() instanceof ServerLevel
						&& _plr3.getAdvancements().getOrStartProgress(_plr3.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:ban_relic_notice"))).isDone())) {
					if (entity instanceof ServerPlayer _player) {
						Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:ban_relic_notice"));
						AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								_player.getAdvancements().award(_adv, criteria);
						}
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("gameplay.relic_ban.notice.0").getString())), false);
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("gameplay.relic_ban.notice.1").getString())), false);
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("gameplay.relic_ban.notice.2").getString())), false);
				}
			}
			if (!(entity instanceof ServerPlayer _plr11 && _plr11.level() instanceof ServerLevel
					&& _plr11.getAdvancements().getOrStartProgress(_plr11.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:surging_waves_notice"))).isDone())) {
				if (entity instanceof ServerPlayer _player) {
					Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:surging_waves_notice"));
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
				if (((!world.isClientSide() && world.getServer() != null) ? ((ServerLifecycleHooks.getCurrentServer().getDefaultGameType()) == (GameType.SURVIVAL)) : false)
						|| ((!world.isClientSide() && world.getServer() != null) ? ((ServerLifecycleHooks.getCurrentServer().getDefaultGameType()) == (GameType.ADVENTURE)) : false)) {
					nowLvl = (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.SURGING_WAVES));
					if (nowLvl >= 12) {
						notice = Component.translatable("gameplay.caerula_arbor_reunite.n_warn_12").getString();
					} else if (nowLvl >= 6) {
						notice = Component.translatable("gameplay.caerula_arbor_reunite.n_warn_6").getString();
					}
					if (!(notice).isEmpty()) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(notice), false);
					}
				} else {
					{
						boolean _setval = false;
						entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.show_stats = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				}
				nowLvl = Math.min(Math.max(1, (double) CaerulaConfigsConfiguration.LP_LIMIT.get()), Math.max((double) CaerulaConfigsConfiguration.LP_INIT.get(), 1));
				{
					double _setval = nowLvl;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.player_maxlive = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					double _setval = nowLvl;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.player_lives = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
	}
}
