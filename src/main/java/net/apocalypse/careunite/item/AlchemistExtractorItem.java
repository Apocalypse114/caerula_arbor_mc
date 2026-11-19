
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;

import java.util.List;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionHand;
import net.apocalypse.careunite.AlchemyCreation;
import net.minecraftforge.items.ItemHandlerHelper;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.advancements.AdvancementProgress;

public class AlchemistExtractorItem extends Item {
	public AlchemistExtractorItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.alchemist_extractor.description_0"));
	}

	@Override
    public @NotNull InteractionResult interactLivingEntity(
            @NotNull ItemStack pStack, @NotNull Player pPlayer, @NotNull LivingEntity pTarget, @NotNull InteractionHand pHand
    ) {
    	if (pPlayer.getCooldowns().isOnCooldown(pStack.getItem())) return InteractionResult.PASS;;
        if (pTarget.isAlive() && pTarget instanceof AlchemyCreation cc){
        	if (cc.getOwner() != null && cc.getOwner() != pPlayer) return InteractionResult.PASS;
        	if (!pPlayer.level().isClientSide() && cc.getBaseHeld() > 0){
        		cc.lostBase(1, false);
        		if (cc.isStunned()) giveAdv(pPlayer);
        		ItemHandlerHelper.giveItemToPlayer(pPlayer, new ItemStack(CaerulaArborModItems.ALCHEMY_BASE_ITEM.get()));
        		if (!pPlayer.isCreative()) pPlayer.getCooldowns().addCooldown(pStack.getItem(), cc.extractionCooldown());
        		return InteractionResult.SUCCESS;
        	}
        }
        return InteractionResult.PASS;
    }

    public static final ResourceLocation adv = new ResourceLocation("caerula_arbor_reunite:drain_the_pond");

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
}
