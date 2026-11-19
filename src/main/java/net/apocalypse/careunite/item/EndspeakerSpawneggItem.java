
package net.apocalypse.careunite.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

import net.apocalypse.careunite.procedures.SummonEndspeakerOnBlockProcedure;
import net.apocalypse.careunite.procedures.SummonEndspeakerInLiquidProcedure;

import java.util.List;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.nbt.CompoundTag;

public class EndspeakerSpawneggItem extends ForgeSpawnEggItem {
	public EndspeakerSpawneggItem() {
		super(CaerulaArborModEntities.ENDSPEAKER_3, -1, -1, new Item.Properties().stacksTo(64).rarity(Rarity.RARE));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.translatable("item.caerula_arbor_reunite.endspeaker_spawnegg.description_0"));
		list.add(Component.literal("Phase: "+phaseIndic(itemstack)));
	}

	private String phaseIndic(ItemStack pStack){
		if (!pStack.getOrCreateTag().contains("phase")) return "IV";
		int p = (int)pStack.getOrCreateTag().getDouble("phase");
		if (p==0) return "I";
		if (p==1) return "II";
		if (p==2) return "III";
		return "IV";
	}

	public EntityType<?> getType(CompoundTag tag){
		if (tag == null) return super.getType(tag);
		if (!tag.contains("phase")) return super.getType(tag);
		int p = (int)tag.getDouble("phase");
		if (p==0) return CaerulaArborModEntities.ENDSPEAKER_0.get();
		if (p==1) return CaerulaArborModEntities.ENDSPEAKER_1.get();
		if (p==2) return CaerulaArborModEntities.ENDSPEAKER_2.get();
		return CaerulaArborModEntities.ENDSPEAKER_3.get();
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		ItemStack item = entity.getItemInHand(hand);
		SummonEndspeakerInLiquidProcedure.execute(entity, item);
		return super.use(world, entity, hand);
	}

//	@Override
//	public InteractionResult useOn(UseOnContext context) {
//		Player player = context.getPlayer();
//		if(player != null && player.isShiftKeyDown()) return super.useOn(context);
//		return SummonEndspeakerOnBlockProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getClickedFace(), context.getItemInHand());
//	}
}
