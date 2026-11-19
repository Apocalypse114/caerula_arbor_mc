package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.OceannizedWitheriaEntity;
import net.apocalypse.careunite.entity.OceanizedWitherEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class OceanWitherImmunityProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity) {
		execute(null, damagesource, entity);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity) {
		if (damagesource == null || entity == null)
			return;
		if ((entity instanceof OceanizedWitherEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedWitherEntity.DATA_shelled)
				|| entity instanceof OceannizedWitheriaEntity _datEntL1 && _datEntL1.getEntityData().get(OceannizedWitheriaEntity.DATA_shelled))
				&& damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("minecraft:is_projectile")))) {
			if (event != null && event.isCancelable()) {
				event.setCanceled(true);
			}
		}
	}
}
