
package net.apocalypse.careunite.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class RecordPathAheadItem extends RecordItem {
	public RecordPathAheadItem() {
		super(6, () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:only_path_ahead")), new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE), 3040);
	}
}
