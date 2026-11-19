package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TyphoeusEntity;
import net.apocalypse.careunite.entity.SaintCarmenEntity;
import net.apocalypse.careunite.entity.IreneEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class IreneAttackBonusProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(Entity entity, Entity sourceentity, double amount) {
		execute(null, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		double factor = 0;
		boolean isSeaborn = false;
		if (sourceentity instanceof IreneEntity) {
			isSeaborn = entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")));
			if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(MobEffects.SLOW_FALLING)) {
				factor = factor + 0.2;
				if (isSeaborn) {
					factor = factor + 0.2;
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ROCK_BREAK.get(), 80, 1));
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ROCK_BREAK.get(), 60, 0));
				}
			}
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * (1 + factor)));
			}
		} else if (entity instanceof IreneEntity
				&& (sourceentity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(MobEffects.SLOW_FALLING) || sourceentity instanceof LivingEntity _livEnt8 && _livEnt8.hasEffect(CaerulaArborModMobEffects.MUTE.get()))) {
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * 0.65));
			}
		}
		if (sourceentity instanceof SaintCarmenEntity || sourceentity instanceof TyphoeusEntity) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MUTE.get(), 100, 0));
		}
	}
}
