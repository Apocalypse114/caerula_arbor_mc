
package net.apocalypse.careunite.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.minecraft.world.item.Items;

public class NetherseaMilkItem extends MilkBucketItem {
	public NetherseaMilkItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	public static final MobEffectInstance MILK_DRUNK = new MobEffectInstance(CaerulaArborModMobEffects.DEDUCT_ONE_SANITY.get(), 200, 0, false, false);

	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living){
		ItemStack ret = super.finishUsingItem(stack, level, living);
		
		if (!level.isClientSide()){
			living.addEffect(MILK_DRUNK);
		}
		
		return ret;
	}

	@Override
	public boolean hasCraftingRemainingItem() {
		return true;
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
		return new ItemStack(Items.BUCKET);
	}

}
