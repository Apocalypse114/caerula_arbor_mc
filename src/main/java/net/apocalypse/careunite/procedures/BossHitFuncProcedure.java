package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class BossHitFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
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
		double sklp = 0;
		if (event.isCanceled()) {
			return;
		}
		if (damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:never_trigger_boss_protection")))) {
			return;
		}
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
			if (entity.tickCount - (entity instanceof LivingEntity _livEnt ? _livEnt.getLastHurtByMobTimestamp() : 0) < 5) {
				if (Math.random() < 0.33) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 4, false, true));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 10, 0, false, true));
				}
			}
		}
	}
}
