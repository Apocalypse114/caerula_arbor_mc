
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;

import net.apocalypse.careunite.procedures.ShowThermoProcedure;
import net.apocalypse.careunite.procedures.CheckDefAndMagicProcedure;

public class ThermographItem extends Item {
	public ThermographItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		ShowThermoProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ());
		return ar;
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		CheckDefAndMagicProcedure.execute(entity, sourceentity);
		return retval;
	}
}
