
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.init.CaerulaArborModItems;

import java.util.List;

public class ChipBoxItem extends Item {
	public ChipBoxItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.chip_box.description_0"));
		list.add(Component.literal("§a"+(8 - getFetchedChip(itemstack))+"/8"));
	}

	public static final String ID_CHIP = "chip";

    private int getFetchedChip(ItemStack stack){
        return stack.getOrCreateTag().getInt(ID_CHIP);
    }
    private void setFetchedChip(ItemStack stack, int n){
        stack.getOrCreateTag().putInt(ID_CHIP, n);
    }

    @Override
    public int getBarWidth(ItemStack stack){
        return Math.round(13 * (1 - (float)getFetchedChip(stack) / 8));
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return getFetchedChip(pStack) > 0;
    }

    @Override
    public int getBarColor(ItemStack stack){
        return 0xCCCCFF;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        super.use(world, entity, hand);
        ItemStack item = entity.getItemInHand(hand);
        int chip = getFetchedChip(item);
        boolean added = entity.getInventory().add(new ItemStack(CaerulaArborModItems.CHIP_PIECE.get()));
        if (added){
            chip++;
            if (chip >= 8) item.shrink(1);
            else setFetchedChip(item, chip);
            return InteractionResultHolder.success(item);
        }
        return InteractionResultHolder.pass(item);
    }
}
