
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;

import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;

public class TrailIngotItem extends Item {
	public TrailIngotItem() {
		super(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.trailrite.description_0"));
	}

	@Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_EFFECTS);
    }
}
