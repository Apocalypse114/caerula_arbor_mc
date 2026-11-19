
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;

import java.util.List;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.ChatFormatting;
import net.apocalypse.careunite.AlchemyCreation;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;

public class AlchemistInjectorItem extends Item {
	private boolean onBlock;
	
	public AlchemistInjectorItem() {
		this(new Item.Properties(), false);
	}

	public AlchemistInjectorItem(Item.Properties prop, boolean useOnBlock) {
		super(prop.durability(useOnBlock ? 75: 50));
		this.onBlock = useOnBlock;
	}

	@Override
    public int getBarColor(ItemStack stack){
    	if (stack.getDamageValue() >= stack.getMaxDamage() - 1) return super.getBarColor(stack);
        return getLoadedBaseCount(stack) > 0 ? 0x33ffff: super.getBarColor(stack);
    }

    @Override
    public int getBarWidth(ItemStack stack){
    	if (stack.getDamageValue() >= stack.getMaxDamage() - 1) return super.getBarWidth(stack);
        int t = getLoadedBaseCount(stack);
        return t > 0 ? t: super.getBarWidth(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
    	return getLoadedBaseCount(pStack) > 0 || pStack.getDamageValue() > 0;
    }

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.alchemist_injector.description_0"));
		if (onBlock) list.add(Component.translatable("item.caerula_arbor_reunite.reinforced_injector.desp"));
		int loaded = getLoadedBaseCount(itemstack);
		if (loaded <= 0) list.add(Component.translatable("item.caerula_arbor_reunite.alchemist_injector.description_1"));
		list.add(Component.translatable("item.caerula_arbor_reunite.alchemist_injector.description_2")
			.append(Component.literal(""+loaded+" / 13")));
		int c0 = getBaseNormal(itemstack);
		int c1 = getBasePurified(itemstack);
		int c2 = getBaseSpotless(itemstack);
		if (c0 > 0) list.add(Component.translatable("item.caerula_arbor_reunite.alchemy_base_item")
			.append(Component.literal("+"+c0)).withStyle(ChatFormatting.YELLOW));
		if (c1 > 0) list.add(Component.translatable("item.caerula_arbor_reunite.hyper_alchemy_base")
			.append(Component.literal("+"+c1)).withStyle(ChatFormatting.YELLOW));
		if (c2 > 0) list.add(Component.translatable("item.caerula_arbor_reunite.spotless_alchemy_base")
			.append(Component.literal("+"+c2)).withStyle(ChatFormatting.YELLOW));
	}

	public static String TAG_BASE_1 = "alchemyBaseNormal";
    public static String TAG_BASE_2 = "alchemyBasePurified";
    public static String TAG_BASE_3 = "alchemyBaseSpotless";
    
    private int getBaseType(ItemStack stack){
        if (stack.isEmpty()) return -1;
        if (stack.is(CaerulaArborModItems.ALCHEMY_BASE_ITEM.get())) return 0;
        if (stack.is(CaerulaArborModItems.HYPER_ALCHEMY_BASE.get())) return 1;
        if (stack.is(CaerulaArborModItems.SPOTLESS_ALCHEMY_BASE.get())) return 2;
        return -1;
    }

   @Override
    public @NotNull InteractionResult interactLivingEntity(
            @NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pTarget, @NotNull InteractionHand pHand
    ) {
        if (pTarget.isAlive() && pTarget instanceof AlchemyCreation cc){
            if (cc.getOwner() != null && cc.getOwner() != pPlayer) return InteractionResult.PASS;
            if (cc.canInject() && pPlayer instanceof ServerPlayer serverPlayer 
            	&& pStack.getDamageValue() < pStack.getMaxDamage()){
            	int type = cc.injectionType();
            	if (type < 0) return InteractionResult.PASS;
            	int n = getLoadedBaseCount(pStack, type);
            	if (n > 0){
            		setLoadedBaseCount(pPlayer.getItemInHand(pHand), type, n - 1);
            		cc.gainBase(1);
            		if (cc.isStunned()){
            			giveAdv(pPlayer);
            		}
            		pPlayer.level().playSound(null, pPlayer.blockPosition(), SoundEvents.BEE_STING, SoundSource.HOSTILE, 
        				1, Mth.nextFloat(pPlayer.getRandom(), 0.8f, 1.2f));
            		if (pStack.hurt(type + 1, pPlayer.getRandom(), serverPlayer)){
                    	pStack.setDamageValue(pStack.getMaxDamage()-1);
	                }
	                return InteractionResult.SUCCESS;
            	}
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand){
        if (pUsedHand == InteractionHand.MAIN_HAND){
            ItemStack curItem = pPlayer.getItemInHand(pUsedHand);
            ItemStack offhandItem = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
            int type = getBaseType(offhandItem);
            if (type >= 0 && loadAlchemyBase(curItem, type)){
                offhandItem.shrink(1);
                return InteractionResultHolder.sidedSuccess(curItem, pLevel.isClientSide());
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public static final ResourceLocation adv = new ResourceLocation("caerula_arbor_reunite:too_much_tobe_good");

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

    public int getLoadedBaseCount(ItemStack pStack){
        return getBaseNormal(pStack) + getBasePurified(pStack) + getBaseSpotless(pStack);
    }
    public int getLoadedBaseCount(ItemStack pStack, int type){
        if (type <= 0) return getBaseNormal(pStack);
        if (type == 1) return getBasePurified(pStack);
        return getBaseSpotless(pStack);
    }
    public void setLoadedBaseCount(ItemStack pStack, int type, int n){
        if (type <= 0) setBaseNormal(pStack, n);
        else if (type == 1) setBasePurified(pStack, n);
        else setBaseSpotless(pStack, n);
    }
    public boolean loadAlchemyBase(ItemStack pStack, int type){
        if (getLoadedBaseCount(pStack) < 13) {
            setLoadedBaseCount(pStack, type, getLoadedBaseCount(pStack, type) + 1);
            return true;
        }
        return  false;
    }

    public int getBaseNormal(ItemStack pStack){
        return pStack.getOrCreateTag().getInt(TAG_BASE_1);
    }
    public void setBaseNormal(ItemStack pStack, int n){
        pStack.getOrCreateTag().putInt(TAG_BASE_1, n);
    }
    public int getBasePurified(ItemStack pStack){
        return pStack.getOrCreateTag().getInt(TAG_BASE_2);
    }
    public void setBasePurified(ItemStack pStack, int n){
        pStack.getOrCreateTag().putInt(TAG_BASE_2, n);
    }
    public int getBaseSpotless(ItemStack pStack){
        return pStack.getOrCreateTag().getInt(TAG_BASE_3);
    }
    public void setBaseSpotless(ItemStack pStack, int n){
        pStack.getOrCreateTag().putInt(TAG_BASE_3, n);
    }
}
