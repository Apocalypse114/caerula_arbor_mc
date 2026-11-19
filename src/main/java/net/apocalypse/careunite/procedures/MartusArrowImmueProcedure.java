package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MartusArrowImmueProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		execute(null, damagesource, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.MARTUS_PROTECTION.get())) {
			if (damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("minecraft:is_projectile"))) && (entity != null ? sourceentity.distanceTo(entity) : -1) > 2
					&& amount <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 2) {
				if (event != null && event.isCancelable()) {
					event.setCanceled(true);
				}
			} else if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) > 3) {
				if (Math.random() < 0.5) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
				}
			}
		}
	}
}
