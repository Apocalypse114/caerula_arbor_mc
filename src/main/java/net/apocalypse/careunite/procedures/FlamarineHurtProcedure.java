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

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.FlamarineStatueEntity;
import net.apocalypse.careunite.entity.FlamarineGolemEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class FlamarineHurtProcedure {
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
		double gap = 0;
		double ratie = 0;
		double add = 0;
		gap = entity.tickCount - (entity instanceof LivingEntity _livEnt ? _livEnt.getLastHurtByMobTimestamp() : 0);
		ratie = 1;
		if (damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("minecraft:bypasses_invulnerability")))) {
			return;
		}
		if (entity instanceof FlamarineStatueEntity || entity instanceof FlamarineGolemEntity) {
			if (gap < 10) {
				ratie = Math.max(gap * 0.1, 0.5);
			}
			if (entity instanceof FlamarineStatueEntity) {
				if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
					_livingEntity6.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
							.setBaseValue(Math.max((entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
									? _livingEntity5.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
									: 0) - 0.5, 1));
				if (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
					_livingEntity8.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
							.setBaseValue(Math.max((entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
									? _livingEntity7.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
									: 0) - 2, 15));
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) Math.min(amount * ratie, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5));
				}
			} else {
				add = entity instanceof FlamarineGolemEntity _datEntI ? _datEntI.getEntityData().get(FlamarineGolemEntity.DATA_addition) : 0;
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) Math.min(amount * ratie, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.34));
				}
				if (add > 0) {
					if (entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
						_livingEntity15.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
								.setBaseValue(((entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
										? _livingEntity14.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
										: 0) + 1));
					if (entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
						_livingEntity17.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
								.setBaseValue(((entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
										? _livingEntity16.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
										: 0) + 1.5));
					if (entity instanceof FlamarineGolemEntity _datEntSetI)
						_datEntSetI.getEntityData().set(FlamarineGolemEntity.DATA_addition, (int) (add - 1));
				}
			}
		}
	}
}
