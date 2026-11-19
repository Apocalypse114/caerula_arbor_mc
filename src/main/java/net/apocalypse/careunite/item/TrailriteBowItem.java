
package net.apocalypse.careunite.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.function.Predicate;

import net.apocalypse.careunite.procedures.BowTransformEnchantsProcedure;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;
import java.util.Set;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.Entity;

public class TrailriteBowItem extends BowItem {
	public TrailriteBowItem() {
		super(new Item.Properties().durability(10293).rarity(Rarity.RARE));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_bow.poem"));
		list.add(Component.translatable("item.caerula_arbor_reunite.trailrite_bow.desc"));
	}

	@Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS);
    }

    @Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		BowTransformEnchantsProcedure.execute(world, itemstack);
	}

	public static float getPowerForTime(int pCharge) {
        float f = (float) pCharge / 30.0F;
        f = (f * f + 3 * f) / 2.2857F;
        if (f > 1.75F) f = 1.75F;
        return f;
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow) {
        arrow.setBaseDamage(arrow.getBaseDamage() * 2.15);
        return arrow;
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return ALSO_OCEAN_ARROW;
    }
    
    public static final Predicate<ItemStack> ALSO_OCEAN_ARROW = (itemStack) -> {
        return ARROW_ONLY.test(itemStack) 
        || itemStack.getItem() == CaerulaArborModItems.OCEAN_ARROW.get()
        || itemStack.getItem() == CaerulaArborModItems.TRAILRITE_ARROW.get();
    };

    private double getRate(Player entity){
        AttributeInstance atk = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if(atk == null) return 0;
        Set<AttributeModifier> BASE = atk.getModifiers(AttributeModifier.Operation.MULTIPLY_BASE);
        Set<AttributeModifier> TOTAL = atk.getModifiers(AttributeModifier.Operation.MULTIPLY_TOTAL);
        double expo_base = 1 + BASE.stream().mapToDouble(AttributeModifier::getAmount).sum();
        double expo_total = TOTAL.stream().mapToDouble(m -> {return 1+m.getAmount();}).reduce(1, (a,b)->(a*b));
        return expo_base * expo_total;
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0;
            ItemStack itemstack = player.getProjectile(pStack);

            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, pStack, player));
                    if (!pLevel.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrow = arrowitem.createArrow(pLevel, itemstack, player);
                        abstractarrow = customArrow(abstractarrow);
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 0.2F);
                        if (f >= 1.0F) {
                            abstractarrow.setCritArrow(true);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, pStack);
                        if (j > 0) {
                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.8D + 1);
                        }

                        double r = getRate(player);
                        if(r>1){
                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() * r);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, pStack);
                        if (k > 0) {
                            abstractarrow.setKnockback(k);
                        }

                        AttributeInstance ist = player.getAttribute(Attributes.ATTACK_DAMAGE);

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, pStack) > 0) {
                            abstractarrow.setSecondsOnFire(200);
                        }

                        pStack.hurtAndBreak(1, player, (p_289501_) -> {
                            p_289501_.broadcastBreakEvent(player.getUsedItemHand());
                        });
                        if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        double link = 4;

                        if (itemstack.is(CaerulaArborModItems.OCEAN_ARROW.get())){
                        	abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() * 1.25);
                        	abstractarrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                        	link = 6;
                        } else if(itemstack.is(CaerulaArborModItems.TRAILRITE_ARROW.get())) {
                        	abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() * 2);
                        	abstractarrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                        	link = 12;
                        }
						 abstractarrow.getPersistentData().putDouble("TrailriteLink", link);

                        pLevel.addFreshEntity(abstractarrow);
                    }

                    pLevel.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }
}
