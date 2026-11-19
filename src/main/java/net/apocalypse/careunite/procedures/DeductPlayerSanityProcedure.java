package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.NetherseaBoatEntity;

public class DeductPlayerSanityProcedure {
	public static void execute(Entity player, double num) {
		if (player == null)
			return;
		double snt = 0;
		double deletion = 0;
		double resis = 0;
		if ((player instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()) ? _livingEntity0.getAttribute(CaerulaArborModAttributes.SANITY.get()).getBaseValue() : 0) < 0) {
			return;
		}
		if (player instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.UNDER_BREAK.get())) {
			return;
		}
		if (player.level().isClientSide()) {
			return;
		}
		if (!(player instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.SANITY_IMMUE.get()))) {
			resis = 0.01 * (100 - (player instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get())
					? _livingEntity3.getAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()).getValue()
					: 0));
			deletion = num * (player instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get())
					? _livingEntity4.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).getValue()
					: 0) * resis;
			if ((player.getVehicle()) instanceof NetherseaBoatEntity) {
				deletion = deletion * 0.5;
			}
			snt = (player instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()) ? _livingEntity7.getAttribute(CaerulaArborModAttributes.SANITY.get()).getBaseValue() : 0)
					- deletion;
			if (snt < -1) {
				snt = -1;
			}
			if (snt > 1000) {
				snt = 1000;
			}
			if (new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(player)) {
				snt = player instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()) ? _livingEntity9.getAttribute(CaerulaArborModAttributes.SANITY.get()).getBaseValue() : 0;
			}
			if (new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
					}
					return false;
				}
			}.checkGamemode(player)) {
				snt = player instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()) ? _livingEntity11.getAttribute(CaerulaArborModAttributes.SANITY.get()).getBaseValue() : 0;
			}
			if (player instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()))
				_livingEntity12.getAttribute(CaerulaArborModAttributes.SANITY.get()).setBaseValue(snt);
			if (player instanceof ServerPlayer _player) {
				Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:terror_of_knowing"));
				AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
				if (!_ap.isDone()) {
					for (String criteria : _ap.getRemainingCriteria())
						_player.getAdvancements().award(_adv, criteria);
				}
			}
		}
	}
}
