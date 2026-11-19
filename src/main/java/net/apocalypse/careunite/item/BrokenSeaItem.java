
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.PullWithGladiiaWeaponProcedure;
import net.apocalypse.careunite.procedures.GiveFurthurBloodOozeProcedure;

import java.util.List;
import net.minecraft.world.item.Rarity;

public class BrokenSeaItem extends SwordItem {
	public BrokenSeaItem() {
		super(new Tier() {
			public int getUses() {
				return 0;
			}

			public float getSpeed() {
				return 9f;
			}

			public float getAttackDamageBonus() {
				return 17f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 12;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of();
			}
		}, 3, -3f, new Item.Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		GiveFurthurBloodOozeProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceentity);
		return retval;
	}

	@Override
	public boolean isEnchantable(ItemStack pStack) {
      return true;
   }

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		PullWithGladiiaWeaponProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, ar.getObject());
		return ar;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.broken_sea.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.broken_sea.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.broken_sea.description_2"));
		list.add(Component.translatable("item.caerula_arbor_reunite.broken_sea.description_3"));
	}
}
