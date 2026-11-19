package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class IsPlayerBatalionSuitProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		ItemStack chest = ItemStack.EMPTY;
		ItemStack legg = ItemStack.EMPTY;
		ItemStack boot = ItemStack.EMPTY;
		ItemStack helm = ItemStack.EMPTY;
		helm = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).copy();
		chest = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).copy();
		legg = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).copy();
		boot = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).copy();
		return helm.getItem() == CaerulaArborModItems.BATALION_ARMOR_HELMET.get() && chest.getItem() == CaerulaArborModItems.BATALION_ARMOR_CHESTPLATE.get() && legg.getItem() == CaerulaArborModItems.BATALION_ARMOR_LEGGINGS.get()
				&& boot.getItem() == CaerulaArborModItems.BATALION_ARMOR_BOOTS.get();
	}
}
