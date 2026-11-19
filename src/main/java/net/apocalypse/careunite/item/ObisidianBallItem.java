
package net.apocalypse.careunite.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class ObisidianBallItem extends Item {
	public ObisidianBallItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
