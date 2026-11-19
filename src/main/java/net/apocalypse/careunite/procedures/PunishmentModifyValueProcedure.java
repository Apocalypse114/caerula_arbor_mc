package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.eventbus.api.EventPriority;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.apocalypse.careunite.AlchemyCreation;

@Mod.EventBusSubscriber
public class PunishmentModifyValueProcedure {

 	public static final ResourceKey<DamageType> PUNISH = ResourceKey.create(
		Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:immortal_punishment")
		);
	public static final ResourceKey<DamageType> CHOKER = ResourceKey.create(
		Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hand_of_choker")
		);
	
	@SubscribeEvent
(priority = EventPriority.LOWEST)
	public static void entityTakesDamage(LivingDamageEvent event) {
		LivingEntity entity = event.getEntity();
		DamageSource damagesource = event.getSource();
		float amount = event.getAmount();
		if (damagesource == null || entity == null)
			return;
		if (shouldParalyzeProtect(entity) && entity.hasEffect(CaerulaArborModMobEffects.PARALYZED.get())){
			event.setAmount(1);
		}
		if (damagesource.is(PUNISH)) {
			event.setAmount(Math.max(amount, (float) (entity.getMaxHealth() * 2)));
		} else if (damagesource.is(CHOKER)){
			event.setAmount(Math.max(amount, (float) (entity.getMaxHealth() * 99)));
		}
	}

	public static boolean shouldParalyzeProtect(Entity entity){
		return entity instanceof AlchemyCreation cc && cc.shouldProtectWhenStunned();
	}

	public static void execute(DamageSource damagesource, Entity entity) {
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity) {
	}
}
