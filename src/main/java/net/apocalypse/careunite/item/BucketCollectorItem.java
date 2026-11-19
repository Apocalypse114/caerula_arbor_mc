
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

public class BucketCollectorItem extends MobBucketItem {
	public BucketCollectorItem() {
		super(CaerulaArborModEntities.COLLECTOR_PROKARYOTE, 
			() -> Fluids.WATER, 
			() -> SoundEvents.BUCKET_EMPTY_FISH,
			new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}
}
