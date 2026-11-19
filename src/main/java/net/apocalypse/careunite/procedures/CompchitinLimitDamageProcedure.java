package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.IberiaMonstrosityEntity;
import net.apocalypse.careunite.entity.ComplexChitinGolemEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CompchitinLimitDamageProcedure {
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
		if (damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("minecraft:bypasses_invulnerability")))) {
			return;
		}
		if (damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:bypass_protection")))) {
			return;
		}
		if ((entity instanceof ComplexChitinGolemEntity || entity instanceof IberiaMonstrosityEntity) && amount > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.1) {
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.1));
			}
		}
	}
}
