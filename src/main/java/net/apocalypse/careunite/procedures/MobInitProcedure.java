package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MobInitProcedure {
	@SubscribeEvent
	public static void onEntitySpawned(EntityJoinLevelEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get())
				? _livingEntity0.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).getBaseValue()
				: 0) == 1) {
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
				if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
					_livingEntity2.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.16);
			}
			if (entity instanceof LivingEntity _livEnt3 && _livEnt3.getMobType() == MobType.UNDEAD) {
				if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
					_livingEntity4.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.5);
			}
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:with_low_sanity_modifier")))) {
				if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
					_livingEntity6.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.5);
			}
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:with_lower_sanity_modifier")))) {
				if (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
					_livingEntity8.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.33);
			}
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:with_lowest_sanity_modifier")))) {
				if (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
					_livingEntity10.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.25);
			}
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:with_lowest_smaller_sanity_modifier")))) {
				if (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
					_livingEntity12.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.2);
			}
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:with_lowest_smallest_sanity_modifier")))) {
				if (entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
					_livingEntity14.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.1);
			}
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:with_zero_sanity_modifier")))) {
				if (entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
					_livingEntity16.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0);
			}
		}
	}
}
