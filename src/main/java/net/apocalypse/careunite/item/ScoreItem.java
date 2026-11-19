
package net.apocalypse.careunite.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.procedures.OneUseItemDescProcedure;
import net.apocalypse.careunite.procedures.GainRelicSCOREProcedure;

import java.util.List;

public class ScoreItem extends RecordItem {
	public ScoreItem() {
		super(8, () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:bloodywolf_openmouth")), new Item.Properties().stacksTo(1).rarity(Rarity.COMMON), 1180);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		Entity entity = itemstack.getEntityRepresentation();
		String hoverText = OneUseItemDescProcedure.execute(itemstack);
		if (hoverText != null) {
			for (String line : hoverText.split("\n")) {
				list.add(Component.literal(line));
			}
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		GainRelicSCOREProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, ar.getObject());
		return ar;
	}
}
