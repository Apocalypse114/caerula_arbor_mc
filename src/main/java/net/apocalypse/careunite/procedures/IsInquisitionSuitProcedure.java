package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class IsInquisitionSuitProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		ItemStack chest = ItemStack.EMPTY;
		ItemStack legg = ItemStack.EMPTY;
		ItemStack boot = ItemStack.EMPTY;
		ItemStack helm = ItemStack.EMPTY;
		helm = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy();
		chest = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy();
		legg = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy();
		boot = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy();
		if (helm.getItem() == CaerulaArborModItems.INQUISITION_ARMOR_HELMET.get() && chest.getItem() == CaerulaArborModItems.INQUISITION_ARMOR_CHESTPLATE.get() && legg.getItem() == CaerulaArborModItems.INQUISITION_ARMOR_LEGGINGS.get()
				&& boot.getItem() == CaerulaArborModItems.INQUISITION_ARMOR_BOOTS.get()) {
			return 2;
		} else if (chest.getItem() == CaerulaArborModItems.INQUISITION_SUIT_CHESTPLATE.get() && legg.getItem() == CaerulaArborModItems.INQUISITION_SUIT_LEGGINGS.get() && boot.getItem() == CaerulaArborModItems.INQUISITION_SUIT_BOOTS.get()) {
			return 1;
		}
		return 0;
	}
}
