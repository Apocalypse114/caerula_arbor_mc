package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraft.world.damagesource.DamageType;

@Mod.EventBusSubscriber
public class InvFuncProcedure {
	@SubscribeEvent
(priority = EventPriority.LOWEST)
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity) {
		execute(null, damagesource, entity);
	}

	public static final ResourceKey<DamageType> INV_KILLER = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:inv_killer"));

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity) {
		if (damagesource == null || entity == null)
			return;
		if (damagesource.is(INV_KILLER)) {
			return;
		}
		if (entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get())) {
			if (event != null && event.isCancelable()) {
				event.setCanceled(true);
			}
		}
	}
}
