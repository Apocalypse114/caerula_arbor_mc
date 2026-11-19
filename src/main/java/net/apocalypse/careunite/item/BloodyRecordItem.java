
package net.apocalypse.careunite.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class BloodyRecordItem extends RecordItem {
	public BloodyRecordItem() {
		super(9, () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:bloody_wolf")), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 3080);
	}
}
