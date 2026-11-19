
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.Cause128SanityProcedure;
import net.apocalypse.careunite.procedures.AdvancedFarmProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;

import net.apocalypse.careunite.CaerulaTiers;

import java.util.List;
import net.minecraft.world.item.Rarity;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;

public class TrailriteHoeItem extends HoeItem {
	public TrailriteHoeItem() {
		super(CaerulaTiers.TRAILRITE, -8, 0.5f, new Item.Properties().fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		Cause128SanityProcedure.execute(entity);
		return retval;
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
		ItemStack retval = new ItemStack(this);
		retval.setDamageValue(itemstack.getDamageValue() + 1);
		if (retval.getDamageValue() >= retval.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return retval;
	}

	@Override
	public boolean isRepairable(ItemStack itemstack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_hoe.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_hoe.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_hoe.description_2"));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return AdvancedFarmProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()), context.getPlayer());
	}

	@Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS);
    }
}
