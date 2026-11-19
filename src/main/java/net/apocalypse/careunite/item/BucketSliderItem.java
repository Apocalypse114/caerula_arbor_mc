
package net.apocalypse.careunite.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.procedures.LaydownRunFishProcedure;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.sounds.SoundEvents;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class BucketSliderItem extends MobBucketItem {
	public BucketSliderItem() {
		super(CaerulaArborModEntities.SLIDER_FISH, 
			() -> Fluids.WATER, 
			() -> SoundEvents.BUCKET_EMPTY_FISH,
			new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}
}
