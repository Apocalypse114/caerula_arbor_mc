
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;

import net.minecraftforge.common.ForgeSpawnEggItem;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class QuintusSpawneggItem extends ForgeSpawnEggItem {
	public QuintusSpawneggItem() {
		super(CaerulaArborModEntities.BISHOP_FISH, -1, -1, new Item.Properties().stacksTo(64).rarity(Rarity.RARE));
	}
}
