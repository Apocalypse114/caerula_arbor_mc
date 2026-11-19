
package net.apocalypse.careunite.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class RecordOceanwishItem extends RecordItem {
	public RecordOceanwishItem() {
		super(8, () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:oceanwish_fullset")), new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE), 2720);
	}
}
