
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.TridentItem;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.apocalypse.careunite.entity.ComplexChitinTridentThrownEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

public class ComplexChitinTridentItem extends TridentItem {

	public ComplexChitinTridentItem() {
		super(new Item.Properties().durability(3650).fireResistant().rarity(Rarity.UNCOMMON));
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 10D, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (double)-2.7F, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.complex_chitin_trident.desc_autoreturn"));
		list.add(Component.translatable("item.caerula_arbor_reunite.complex_chitin_trident.description_0"));
		if (EnchantmentHelper.hasChanneling(itemstack)){
			list.add(Component.translatable("item.caerula_arbor_reunite.complex_chitin_trident.description_1"));
		}
	}

	private final Multimap<Attribute, AttributeModifier> defaultModifiers;

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.SPEAR;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 72000;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
      return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
   }

	public void performThrow(Player player, Level pLevel, ItemStack pStack, int j){
		ComplexChitinTridentThrownEntity throwntrident = new ComplexChitinTridentThrownEntity(pLevel, player, pStack);
		throwntrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float)j * 0.5F, 1.0F);
		if (player.getAbilities().instabuild) {
			throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
		}
		
		pLevel.addFreshEntity(throwntrident);
		pLevel.playSound(null, throwntrident, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
		if (!player.getAbilities().instabuild) {
			player.getInventory().removeItem(pStack);
		}
	}

	public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
      if (pEntityLiving instanceof Player player) {
         int i = this.getUseDuration(pStack) - pTimeLeft;
         if (i >= 10) {
            int j = EnchantmentHelper.getRiptide(pStack);
            if (j <= 0 || player.isInWaterOrRain()) {
               if (!pLevel.isClientSide) {
                  pStack.hurtAndBreak(1, player, (p_43388_) -> {
                     p_43388_.broadcastBreakEvent(pEntityLiving.getUsedItemHand());
                  });
                  if (j == 0) {
                     performThrow(player, pLevel, pStack, j);
                  }
               }

               player.awardStat(Stats.ITEM_USED.get(this));
               if (j > 0) {
                  startRipTide(player, pLevel, j);
               }
            }
         }
      }
   }

   public void startRipTide(Player player, Level pLevel, int j){
   		float f7 = player.getYRot();
		float f = player.getXRot();
		float f1 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
		float f2 = -Mth.sin(f * ((float)Math.PI / 180F));
		float f3 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
		float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
		float f5 = 3.0F * ((1.0F + (float)j) / 4.0F);
		f1 *= f5 / f4;
		f2 *= f5 / f4;
		f3 *= f5 / f4;
		player.push((double)f1, (double)f2, (double)f3);
		player.startAutoSpinAttack(20);
		if (player.onGround()) {
			float f6 = 1.1999999F;
			player.move(MoverType.SELF, new Vec3(0.0D, (double)1.1999999F, 0.0D));
		}
		
		SoundEvent soundevent;
		if (j >= 3) {
			soundevent = SoundEvents.TRIDENT_RIPTIDE_3;
		} else if (j == 2) {
			soundevent = SoundEvents.TRIDENT_RIPTIDE_2;
		} else {
			soundevent = SoundEvents.TRIDENT_RIPTIDE_1;
		}
		
		pLevel.playSound(null, player, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
   }
}
