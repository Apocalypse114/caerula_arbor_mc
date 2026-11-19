
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.LanternBurnTrailsProcedure;
import net.apocalypse.careunite.procedures.FlameSeabornsProcedure;
import net.apocalypse.careunite.procedures.CeaseDizinessProcedure;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.List;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;
import java.util.UUID;

public class LanternJudgementItem extends Item {
	public LanternJudgementItem() {
		super(new Item.Properties().durability(799).fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.BOW;
	}

	@Override
	public boolean hasCraftingRemainingItem() {
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
	public int getUseDuration(ItemStack itemstack) {
		return 40;
	}

	public static final UUID SANITY_UUID = new UUID("lantern_sanity".hashCode(), 0);
	public static final UUID MAGIC_UUID = new UUID("lantern_magic".hashCode(), 0);

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
		if (equipmentSlot == EquipmentSlot.MAINHAND) {
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 3d, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
			builder.put(CaerulaArborModAttributes.SANITY_RESISTANCE.get(), 
				new AttributeModifier(SANITY_UUID, "Item modifier", 35, AttributeModifier.Operation.ADDITION));
			builder.put(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(), 
				new AttributeModifier(MAGIC_UUID, "Item modifier", 7.5, AttributeModifier.Operation.ADDITION));
		}
		if (equipmentSlot == EquipmentSlot.OFFHAND){
			builder.put(CaerulaArborModAttributes.SANITY_RESISTANCE.get(), 
				new AttributeModifier(SANITY_UUID, "Item modifier", 35, AttributeModifier.Operation.ADDITION));
			builder.put(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(), 
				new AttributeModifier(MAGIC_UUID, "Item modifier", 7.5, AttributeModifier.Operation.ADDITION));
		}
		return builder.build();
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.lantern_judgement.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.lantern_judgement.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.lantern_judgement.description_2"));
		list.add(Component.translatable("item.caerula_arbor_reunite.lantern_judgement.description_3"));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		entity.startUsingItem(hand);
		return ar;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		FlameSeabornsProcedure.execute(world, x, y, z, entity, itemstack);
		return retval;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return LanternBurnTrailsProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()), context.getPlayer(),
				context.getItemInHand());
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		itemstack.hurtAndBreak(1, entity, i -> i.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			CeaseDizinessProcedure.execute(entity);
	}
}
