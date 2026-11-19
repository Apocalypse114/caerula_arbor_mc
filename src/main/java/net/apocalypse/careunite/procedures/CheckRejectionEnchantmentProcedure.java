package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModEnchantments;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CheckRejectionEnchantmentProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player.level(), event.player);
		}
	}

	public static boolean execute(LevelAccessor world, Entity entity) {
		return execute(null, world, entity);
	}

	private static boolean execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return false;
		ItemStack armor = ItemStack.EMPTY;
		for (int index0 = 0; index0 < 4; index0++) {
			armor = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, (int) index0)) : ItemStack.EMPTY).copy();
			if (EnchantmentHelper.getItemEnchantmentLevel(CaerulaArborModEnchantments.REJECTION_CURSE.get(), armor) != 0) {
				return true;
			}
		}
		return false;
	}
}
