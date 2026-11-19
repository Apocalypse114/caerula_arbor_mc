package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.init.CaerulaArborModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class AnvilDataSyncProcedure {
	@SubscribeEvent
	public static void onItemTakenFromAnvil(AnvilRepairEvent event) {
		execute(event, event.getLeft(), event.getOutput(), event.getRight());
	}

	public static void execute(ItemStack leftItem, ItemStack output, ItemStack rightItem) {
		execute(null, leftItem, output, rightItem);
	}

	private static void execute(@Nullable Event event, ItemStack leftItem, ItemStack output, ItemStack rightItem) {
		if (rightItem.getItem() == CaerulaArborModItems.KNIGHT_CORPSE.get() && leftItem.getItem() == Items.IRON_SWORD) {
			{
				CompoundTag _nbtTag = leftItem.getTag();
				if (_nbtTag != null)
					output.setTag(_nbtTag.copy());
			}
		}
	}
}
