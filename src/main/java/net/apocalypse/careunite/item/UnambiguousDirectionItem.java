
package net.apocalypse.careunite.item;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.PlayAnchorSoundProcedure;
import net.apocalypse.careunite.procedures.GainLessSpeedProcedure;
import net.apocalypse.careunite.procedures.AnchorSKillreleaseProcedure;
import net.apocalypse.careunite.CaerulaTiers;

import java.util.List;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.PickaxeItem;

public class UnambiguousDirectionItem extends PickaxeItem {
	public UnambiguousDirectionItem() {
		super(CaerulaTiers.AEGIR, 21, -3.15f, new Item.Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.SPEAR;
	}

	@Override
	public int getEnchantmentValue() {
		return 16;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 72000;
	}

	@Override
	public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
		return 6f;
	}

	@Override
	public boolean isEnchantable(ItemStack pStack) {
      return true;
   }

//	@Override
//	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
//		if (equipmentSlot == EquipmentSlot.MAINHAND) {
//			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
//			builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
//			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 26d, AttributeModifier.Operation.ADDITION));
//			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -3.15, AttributeModifier.Operation.ADDITION));
//			return builder.build();
//		}
//		return super.getDefaultAttributeModifiers(equipmentSlot);
//	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.unambiguous_direction.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.unambiguous_direction.description_1"));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		entity.startUsingItem(hand);
		return ar;
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		PlayAnchorSoundProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), sourceentity);
		return retval;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			GainLessSpeedProcedure.execute(entity);
	}

	@Override
	public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entity, int time) {
		AnchorSKillreleaseProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
	}

	@Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS);
    }
}
