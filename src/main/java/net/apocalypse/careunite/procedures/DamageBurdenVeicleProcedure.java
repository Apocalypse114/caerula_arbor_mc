package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.OceanizedRavagerEntity;
import net.apocalypse.careunite.entity.OceanizedPolarBearEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class DamageBurdenVeicleProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity(), event.getAmount());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity, double amount) {
		execute(null, damagesource, entity, amount);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity, double amount) {
		if (damagesource == null || entity == null)
			return;
		Entity vehicle = null;
		if (entity.isAlive() && entity.isPassenger()) {
			vehicle = entity.getVehicle();
			if (!(vehicle == null) && vehicle.isAlive()) {
				if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					if (vehicle instanceof OceanizedRavagerEntity) {
						if (event instanceof LivingHurtEvent _hurt) {
							_hurt.setAmount((float) (amount * 0.5));
						}
						vehicle.hurt(damagesource, (float) (amount * 0.5));
					} else if (vehicle instanceof OceanizedPolarBearEntity) {
						if (event instanceof LivingHurtEvent _hurt) {
							_hurt.setAmount((float) (amount * 0.65));
						}
						vehicle.hurt(damagesource, (float) (amount * 0.35));
					}
				}
			}
		}
	}
}
