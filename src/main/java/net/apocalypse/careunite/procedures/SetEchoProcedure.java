package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class SetEchoProcedure {
	public static void execute(Entity entity, double duration, double id) {
		if (entity == null)
			return;
		double index = 0;
		double time = 0;
		index = (int) id;
		time = (int) duration;
		if (time != 0) {
			if (entity instanceof ServerPlayer _player) {
				Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:gain_echo"));
				AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
				if (!_ap.isDone()) {
					for (String criteria : _ap.getRemainingCriteria())
						_player.getAdvancements().award(_adv, criteria);
				}
			}
		}
		if (index == 0) {
			{
				double _setval = time;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.ECHO_EXPLORE = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if (index == 1) {
			{
				double _setval = time;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.ECHO_FIGHT = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if (index == 2) {
			{
				double _setval = time;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.ECHO_REMOULD = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if (index == 3) {
			{
				double _setval = time;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.ECHO_VISSIS = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if (index == 4) {
			{
				double _setval = time;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.ECHO_FEED = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if (index == 5) {
			{
				double _setval = time;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.ECHO_CROWD = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if (index == 6) {
			{
				double _setval = time;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.ECHO_SPINDLING = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if (index == 7) {
			{
				double _setval = time;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.ECHO_ADAPT = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
