package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class BanRElicFuncProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		ItemStack mainHandItem = ItemStack.EMPTY;
		if (CaerulaConfigsConfiguration.RELIC_BAN.get()) {
			mainHandItem = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
			if (mainHandItem.is(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:relic_advanced")))) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).setCount(0);
			}
			mainHandItem = (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).copy();
			if (mainHandItem.is(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:relic_advanced")))) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).setCount(0);
			}
			if (entity.tickCount % 20 == 10) {
				{
					double _setval = -1;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_hand_ENGRAVE = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					double _setval = -1;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_SURVIVOR = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_king_CROWN = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_king_ARMOR = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_king_SPEAR = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_king_EXTENSION = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_king_CRYSTAL = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_archfi_FLAG = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_archfi_BED = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_archifi_RYLFATE = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_archfi_ARTIFACT = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_hand_THORNS = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_hand_STRANGLE = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_hand_FERTILITY = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_hand_SPEED = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_hand_BARREN = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_hand_SWIPE = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_hand_FIREWORK = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_hand_SWORD = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_legend_CHITIN = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_YEARNING = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				{
					boolean _setval = false;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.relic_TREATY = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
	}
}
