
package net.apocalypse.careunite.item;

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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.CastDragonSpellProcedure;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.List;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;
import java.util.UUID;

public class DragonWandItem extends Item {
	public DragonWandItem() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 72000;
	}

	public final UUID MAGIC_UUID = new UUID(this.toString().hashCode(), 0);

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		if (equipmentSlot == EquipmentSlot.MAINHAND) {
			builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
			builder.put(Attributes.ATTACK_DAMAGE, 
				new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 9.5d, AttributeModifier.Operation.ADDITION));
			builder.put(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(), 
				new AttributeModifier(MAGIC_UUID, "Item modifier", 25d, AttributeModifier.Operation.ADDITION));
			return builder.build();
		}
		return super.getDefaultAttributeModifiers(equipmentSlot);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.dragon_wand.description_0"));
		list.add(Component.translatable("item.caerula_arbor_reunite.dragon_wand.description_1"));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		entity.startUsingItem(hand);
		return ar;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			CastDragonSpellProcedure.execute(world, entity.getX(), entity.getY()+1.25, entity.getZ(), entity, itemstack);
	}

	@Override
	public boolean isEnchantable(ItemStack pStack) {
      return true;
   }
}
