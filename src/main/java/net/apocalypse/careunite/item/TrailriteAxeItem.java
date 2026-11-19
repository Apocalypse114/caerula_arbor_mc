
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.PercentageDamageProcedure;
import net.apocalypse.careunite.procedures.Add60ReachProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;

import net.apocalypse.careunite.CaerulaTiers;

import java.util.List;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;

public class TrailriteAxeItem extends AxeItem {
	public TrailriteAxeItem() {
		super(CaerulaTiers.TRAILRITE, 15, -3.2f, new Item.Properties().fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		PercentageDamageProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceentity, itemstack);
		return retval;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_axe.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_axe.description_1"));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			Add60ReachProcedure.execute(entity);
	}

	@Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS);
    }
}
