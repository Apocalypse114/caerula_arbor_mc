package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class TrailriteAttackBonusProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		execute(null, damagesource, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		ItemStack chest = ItemStack.EMPTY;
		ItemStack legg = ItemStack.EMPTY;
		ItemStack boot = ItemStack.EMPTY;
		ItemStack helm = ItemStack.EMPTY;
		double gap = 0;
		double maxH = 0;
		helm = (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy();
		chest = (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy();
		legg = (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy();
		boot = (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy();
		if (helm.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_HELMET.get() && chest.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_CHESTPLATE.get() && legg.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_LEGGINGS.get()
				&& boot.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_BOOTS.get()) {
			GiveLessArmorProcedure.execute(entity, 24);
		}
		helm = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy();
		chest = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy();
		legg = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy();
		boot = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy();
		if (helm.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_HELMET.get() && chest.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_CHESTPLATE.get() && legg.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_LEGGINGS.get()
				&& boot.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_BOOTS.get()) {
			if (!damagesource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("minecraft:bypasses_invulnerability")))) {
				gap = entity.tickCount - (entity instanceof LivingEntity _livEnt ? _livEnt.getLastHurtByMobTimestamp() : 0);
				maxH = entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) Math.min(amount * Math.min(1, Math.max(gap * 0.1, 0.05)), Math.max(maxH * 0.33, 16)));
				}
			}
		}
	}
}
