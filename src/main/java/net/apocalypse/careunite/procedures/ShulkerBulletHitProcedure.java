package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ShulkerBulletHitProcedure {
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
		Entity bullet = null;
		double lvl = 0;
		bullet = damagesource.getDirectEntity();
		if (bullet instanceof ShulkerBullet && bullet.getPersistentData().getBoolean("oceanized")) {
			GiveLessArmorProcedure.execute(entity, 8);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MORE_FALL_DAMAGE.get(), 300, 0));
		}
		if (damagesource.is(DamageTypes.FALL) && entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(CaerulaArborModMobEffects.MORE_FALL_DAMAGE.get())) {
			lvl = (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.MORE_FALL_DAMAGE.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.MORE_FALL_DAMAGE.get()).getAmplifier() : 0) + 1;
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * (1 + 0.25 * lvl)));
			}
		}
	}
}
