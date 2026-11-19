
package net.apocalypse.careunite.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class ItemNetherseaWheatItem extends Item {
	public ItemNetherseaWheatItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
