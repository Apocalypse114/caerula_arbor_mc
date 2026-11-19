
package net.apocalypse.careunite.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class RedstoniumItem extends Item {
	public RedstoniumItem() {
		super(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE));
	}
}
