
package net.apocalypse.careunite.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class RecordWhisperItem extends RecordItem {
	public RecordWhisperItem() {
		super(6, () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:whisper_illusion")), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 4480);
	}
}
