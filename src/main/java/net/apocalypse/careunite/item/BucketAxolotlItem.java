
package net.apocalypse.careunite.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;

import net.minecraft.world.level.material.Fluids;
import net.minecraft.sounds.SoundEvents;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class BucketAxolotlItem extends MobBucketItem {
	public BucketAxolotlItem() {
		super(CaerulaArborModEntities.PROMOTED_AXOLOTL, 
			() -> Fluids.WATER, 
			() -> SoundEvents.BUCKET_EMPTY_AXOLOTL,
			new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}
}
