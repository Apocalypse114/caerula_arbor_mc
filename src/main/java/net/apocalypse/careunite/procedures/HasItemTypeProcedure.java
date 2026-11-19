package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;

public class HasItemTypeProcedure {

	public static final TagKey<Item> WHIRL = ItemTags.create(new ResourceLocation("caerula_arbor_reunite:whirl_eye"));

	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity instanceof Player _playerHasItem
 ? _playerHasItem.getInventory().contains(WHIRL)
: false;
	}
}
