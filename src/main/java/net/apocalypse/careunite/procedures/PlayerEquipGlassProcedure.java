package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.minecraft.client.Minecraft;

public class PlayerEquipGlassProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) return false;
		return (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == CaerulaArborModBlocks.OCEAN_GLASS.get().asItem();
	}
}
