package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class TrailriteArmorSelfMendProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource().getEntity());
		}
	}

	public static void execute(Entity sourceentity) {
		execute(null, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double gap = 0;
		ItemStack chest = ItemStack.EMPTY;
		ItemStack legg = ItemStack.EMPTY;
		ItemStack boot = ItemStack.EMPTY;
		ItemStack helm = ItemStack.EMPTY;
		if (!event.isCanceled()) {
			helm = (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy();
			chest = (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy();
			legg = (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy();
			boot = (sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy();
			if (helm.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_HELMET.get() && chest.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_CHESTPLATE.get() && legg.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_LEGGINGS.get()
					&& boot.getItem() == CaerulaArborModItems.TRAILRITE_ARMOR_BOOTS.get()) {
				(sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).setDamageValue((int) (helm.getDamageValue() - 3));
				(sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).setDamageValue((int) (chest.getDamageValue() - 3));
				(sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).setDamageValue((int) (legg.getDamageValue() - 3));
				(sourceentity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).setDamageValue((int) (boot.getDamageValue() - 3));
			}
		}
	}
}
