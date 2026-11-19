package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ExpFuncProcedure {
	@SubscribeEvent
	public static void onLivingDropXp(LivingExperienceDropEvent event) {
		if (event != null && event.getEntity() != null) {
			Player sourceentity = event.getAttackingPlayer();
			if (sourceentity instanceof Player 
				&& (sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new CaerulaArborModVariables.PlayerVariables())).relic_king_EXTENSION) {
				if ((sourceentity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new CaerulaArborModVariables.PlayerVariables())).player_lives <= 1) {
					event.setDroppedExperience((int)(event.getDroppedExperience() * 1.5));
				}
			}
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity, double originalexperience) {
		execute(null, world, x, y, z, sourceentity, originalexperience);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity sourceentity, double originalexperience) {
	}
}
