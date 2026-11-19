
package net.apocalypse.careunite.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class ChitinIngotItem extends Item {
	public ChitinIngotItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
