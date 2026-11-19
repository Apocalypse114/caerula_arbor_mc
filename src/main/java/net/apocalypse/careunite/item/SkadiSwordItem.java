
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.SkadiSwordRangedProcedure;
import net.apocalypse.careunite.procedures.SkadiSwordBonusProcedure;

import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;

public class SkadiSwordItem extends SwordItem {
	public SkadiSwordItem() {
		super(new Tier() {
			public int getUses() {
				return 0;
			}

			public float getSpeed() {
				return 16f;
			}

			public float getAttackDamageBonus() {
				return 20f;
			}

			public int getLevel() {
				return 4;
			}

			public int getEnchantmentValue() {
				return 12;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of();
			}
		}, 3, -2.8f, new Item.Properties().fireResistant());
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		SkadiSwordRangedProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceentity);
		return retval;
	}

	@Override
	public boolean isEnchantable(ItemStack pStack) {
      return true;
   }

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.skadi_sword.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.skadi_sword.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.skadi_sword.description_2"));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			SkadiSwordBonusProcedure.execute(entity);
	}

	@Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS);
    }
}
