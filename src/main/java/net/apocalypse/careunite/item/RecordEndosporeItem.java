
package net.apocalypse.careunite.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class RecordEndosporeItem extends RecordItem {
	public RecordEndosporeItem() {
		super(0, () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endospore")), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 3940);
	}
}
