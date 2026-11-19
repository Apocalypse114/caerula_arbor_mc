
package net.apocalypse.careunite.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class RecordMareNatusItem extends RecordItem {
	public RecordMareNatusItem() {
		super(8, () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mare_natus")), new Item.Properties().stacksTo(64).rarity(Rarity.RARE), 4000);
	}
}
