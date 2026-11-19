
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.apocalypse.careunite.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;

import net.apocalypse.careunite.CaerulaArborMod;

import java.util.stream.Collectors;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaerulaArborModAttributes {
	public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, CaerulaArborMod.MODID);
	public static final RegistryObject<Attribute> SANITY = REGISTRY.register("sanity", () -> new RangedAttribute("attribute.caerula_arbor_reunite.sanity", 1000, -1, 1000).setSyncable(true));
	public static final RegistryObject<Attribute> SANITY_MODIFIER = REGISTRY.register("sanity_modifier", () -> new RangedAttribute("attribute.caerula_arbor_reunite.sanity_modifier", 1, 0, 999).setSyncable(true));
	public static final RegistryObject<Attribute> SANITY_RATE = REGISTRY.register("sanity_rate", () -> new RangedAttribute("attribute.caerula_arbor_reunite.sanity_rate", 0, 0, 999).setSyncable(true));
	public static final RegistryObject<Attribute> EVOLVED = REGISTRY.register("evolved", () -> new RangedAttribute("attribute.caerula_arbor_reunite.evolved", 0, 0, 1).setSyncable(true));
	public static final RegistryObject<Attribute> SUMMONABLE = REGISTRY.register("summonable", () -> new RangedAttribute("attribute.caerula_arbor_reunite.summonable", 1, 0, 1).setSyncable(true));
	public static final RegistryObject<Attribute> MISSRATE = REGISTRY.register("missrate", () -> new RangedAttribute("attribute.caerula_arbor_reunite.missrate", 0, 0, 95).setSyncable(true));
	public static final RegistryObject<Attribute> MAGIC_RESISTANCE = REGISTRY.register("magic_resistance", () -> new RangedAttribute("attribute.caerula_arbor_reunite.magic_resistance", 0, 0, 100).setSyncable(true));
	public static final RegistryObject<Attribute> GENERAL_DEFENSE = REGISTRY.register("general_defense", () -> new RangedAttribute("attribute.caerula_arbor_reunite.general_defense", 0, 0, 131071).setSyncable(true));
	public static final RegistryObject<Attribute> NUMB = REGISTRY.register("numb", () -> new RangedAttribute("attribute.caerula_arbor_reunite.numb", 0, 0, 9).setSyncable(true));
	public static final RegistryObject<Attribute> SANITY_RESISTANCE = REGISTRY.register("sanity_resistance", () -> new RangedAttribute("attribute.caerula_arbor_reunite.sanity_resistance", 0, 0, 100).setSyncable(true));
	public static final RegistryObject<Attribute> LIVING_BARRIER = REGISTRY.register("living_barrier", () -> new RangedAttribute("attribute.caerula_arbor_reunite.living_barrier", 0, 0, 214748364).setSyncable(true));
	public static final RegistryObject<Attribute> LAYERED_BARRIER = REGISTRY.register("layered_barrier", () -> new RangedAttribute("attribute.caerula_arbor_reunite.layered_barrier", 0, 0, 16).setSyncable(true));

	@SubscribeEvent
	public static void addAttributes(EntityAttributeModificationEvent event) {
		event.getTypes().forEach(entity -> event.add(entity, SANITY.get()));
		event.getTypes().forEach(entity -> event.add(entity, SANITY_MODIFIER.get()));
		event.getTypes().forEach(entity -> event.add(entity, SANITY_RATE.get()));
		List.of(CaerulaArborModEntities.APOCATA.get()).stream().filter(DefaultAttributes::hasSupplier).map(entityType -> (EntityType<? extends LivingEntity>) entityType).collect(Collectors.toList())
				.forEach(entity -> event.add(entity, EVOLVED.get()));
		List.of(CaerulaArborModEntities.BISHOP_FISH.get(), CaerulaArborModEntities.BONE_FISH.get(), CaerulaArborModEntities.CHEST_FISH.get(), CaerulaArborModEntities.CHISELER_FISH.get(), CaerulaArborModEntities.CREEPER_FISH.get(),
				CaerulaArborModEntities.FLEE_FISH.get(), CaerulaArborModEntities.FLY_FISH.get(), CaerulaArborModEntities.PREGNANT_FISH.get(), CaerulaArborModEntities.PUNCTURE_FISH.get(), CaerulaArborModEntities.REAPER_FISH.get(),
				CaerulaArborModEntities.RUN_FISH.get(), CaerulaArborModEntities.SCREAM_CHEST_FISH.get(), CaerulaArborModEntities.SHOOTER_FISH.get(), CaerulaArborModEntities.SLIDER_FISH.get(), CaerulaArborModEntities.BASELAYER_ABYSSAL.get(),
				CaerulaArborModEntities.CRACKER_ABYSSAL.get(), CaerulaArborModEntities.GUIDE_ABYSSAL.get(), CaerulaArborModEntities.PREDATOR_ABYSSAL.get(), CaerulaArborModEntities.SPLASHER_ABYSSAL.get(),
				CaerulaArborModEntities.UMBRELLA_ABYSSAL.get(), CaerulaArborModEntities.ACCUMULATOR_PROKARYOTE.get(), CaerulaArborModEntities.APOSTLE_PROKARYOTE.get(), CaerulaArborModEntities.COLLECTOR_PROKARYOTE.get(),
				CaerulaArborModEntities.DEPOSITER_PROKARYOTE.get(), CaerulaArborModEntities.FEEDER_PROKARYOTE.get(), CaerulaArborModEntities.FLOATER_PROKARYOTE.get(), CaerulaArborModEntities.NUCLEIC_MALEFICENT.get()).stream()
				.filter(DefaultAttributes::hasSupplier).map(entityType -> (EntityType<? extends LivingEntity>) entityType).collect(Collectors.toList()).forEach(entity -> event.add(entity, SUMMONABLE.get()));
		event.getTypes().forEach(entity -> event.add(entity, MISSRATE.get()));
		event.getTypes().forEach(entity -> event.add(entity, MAGIC_RESISTANCE.get()));
		event.getTypes().forEach(entity -> event.add(entity, GENERAL_DEFENSE.get()));
		event.getTypes().forEach(entity -> event.add(entity, NUMB.get()));
		event.getTypes().forEach(entity -> event.add(entity, SANITY_RESISTANCE.get()));
		event.getTypes().forEach(entity -> event.add(entity, LIVING_BARRIER.get()));
		event.getTypes().forEach(entity -> event.add(entity, LAYERED_BARRIER.get()));
	}

	@Mod.EventBusSubscriber
	public static class PlayerAttributesSync {
		@SubscribeEvent
		public static void playerClone(PlayerEvent.Clone event) {
			Player oldPlayer = event.getOriginal();
			Player newPlayer = event.getEntity();
			newPlayer.getAttribute(SANITY.get()).setBaseValue(oldPlayer.getAttribute(SANITY.get()).getBaseValue());
			newPlayer.getAttribute(SANITY_MODIFIER.get()).setBaseValue(oldPlayer.getAttribute(SANITY_MODIFIER.get()).getBaseValue());
			newPlayer.getAttribute(SANITY_RATE.get()).setBaseValue(oldPlayer.getAttribute(SANITY_RATE.get()).getBaseValue());
			newPlayer.getAttribute(MISSRATE.get()).setBaseValue(oldPlayer.getAttribute(MISSRATE.get()).getBaseValue());
			newPlayer.getAttribute(MAGIC_RESISTANCE.get()).setBaseValue(oldPlayer.getAttribute(MAGIC_RESISTANCE.get()).getBaseValue());
			newPlayer.getAttribute(GENERAL_DEFENSE.get()).setBaseValue(oldPlayer.getAttribute(GENERAL_DEFENSE.get()).getBaseValue());
			newPlayer.getAttribute(NUMB.get()).setBaseValue(oldPlayer.getAttribute(NUMB.get()).getBaseValue());
			newPlayer.getAttribute(SANITY_RESISTANCE.get()).setBaseValue(oldPlayer.getAttribute(SANITY_RESISTANCE.get()).getBaseValue());
			newPlayer.getAttribute(LIVING_BARRIER.get()).setBaseValue(oldPlayer.getAttribute(LIVING_BARRIER.get()).getBaseValue());
			newPlayer.getAttribute(LAYERED_BARRIER.get()).setBaseValue(oldPlayer.getAttribute(LAYERED_BARRIER.get()).getBaseValue());
		}
	}
}
