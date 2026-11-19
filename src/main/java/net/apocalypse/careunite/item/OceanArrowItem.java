
package net.apocalypse.careunite.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArrowItem;

public class OceanArrowItem extends ArrowItem {
	public OceanArrowItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
