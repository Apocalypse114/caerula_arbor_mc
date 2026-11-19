
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;

import java.util.List;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import org.jetbrains.annotations.NotNull;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.Seaborn;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;

public class AlchemistReverterItem extends Item {
	public AlchemistReverterItem() {
		super(new Item.Properties().durability(9).fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
    public @NotNull InteractionResult interactLivingEntity(
            @NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pTarget, @NotNull InteractionHand pHand
    ) {
        if (isLoaded(pStack) && pTarget.isAlive() && pTarget instanceof Seaborn ss){
            if (Seaborn.revertToOriginal(ss, e -> {
	            	if (e instanceof LivingEntity ll){
	            		ll.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 4));
	            		ll.setHealth(ll.getMaxHealth() * 0.5f);
	            	}
	            }
	         )){
	         	setLoaded(pPlayer.getItemInHand(pHand), false);
	         	pTarget.level().playSound(null, pPlayer.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.HOSTILE, 
        				1, Mth.nextFloat(pPlayer.getRandom(), 0.8f, 1.2f));
	         	pTarget.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
	         	giveAdv(pPlayer);
	         	return InteractionResult.sidedSuccess(pPlayer.level().isClientSide());
	         }
        }
        return InteractionResult.PASS;
    }

	@Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand){
        if (pUsedHand == InteractionHand.MAIN_HAND){
            ItemStack curItem = pPlayer.getItemInHand(pUsedHand);
            ItemStack offhandItem = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
            if (!isLoaded(curItem) && offhandItem.is(CaerulaArborModItems.LINEAGE_REVERSE_COMPOUND.get())){
                setLoaded(curItem, true);
                offhandItem.shrink(1);
                return InteractionResultHolder.sidedSuccess(curItem, pLevel.isClientSide());
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public static final ResourceLocation adv = new ResourceLocation("caerula_arbor_reunite:for_the_tomorrow");

	public void giveAdv(Player entity){
    	if (entity instanceof ServerPlayer _player) {
			Advancement _adv = _player.server.getAdvancements().getAdvancement(adv);
			AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
			if (!_ap.isDone()) {
				for (String criteria : _ap.getRemainingCriteria())
					_player.getAdvancements().award(_adv, criteria);
			}
		}
	}

	public static String TAG_LOADED = "loaded";

	public boolean isLoaded(ItemStack pStack){
		return pStack.getOrCreateTag().getBoolean(TAG_LOADED);
	}

	public void setLoaded(ItemStack stack, boolean bool){
		stack.getOrCreateTag().putBoolean(TAG_LOADED, bool);
	}

	public boolean isFoil(ItemStack pStack){
		return isLoaded(pStack);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.alchemist_reverter.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.alchemist_reverter.description_2"));

		list.add(Component.translatable("item.caerula_arbor_reunite.alchemist_reverter.description_0"));
		if (!isLoaded(itemstack)) list.add(Component.translatable("item.caerula_arbor_reunite.alchemist_reverter.description_3"));
	}
}
