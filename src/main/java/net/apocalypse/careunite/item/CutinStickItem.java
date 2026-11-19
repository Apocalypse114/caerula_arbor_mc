
package net.apocalypse.careunite.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class CutinStickItem extends Item {
	public CutinStickItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
