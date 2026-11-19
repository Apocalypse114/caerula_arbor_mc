package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class NoInTeamDamageProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:inquisition"))) && sourceentity instanceof Player) {
			if ((entity.getPersistentData().getString("recentCommander")).equals(sourceentity.getDisplayName().getString())) {
				if (event != null && event.isCancelable()) {
					event.setCanceled(true);
				}
			}
			if ((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.INTERPHONE.get()) : false) {
				entity.getPersistentData().putString("recentCommander", "");
			}
		}
	}
}
