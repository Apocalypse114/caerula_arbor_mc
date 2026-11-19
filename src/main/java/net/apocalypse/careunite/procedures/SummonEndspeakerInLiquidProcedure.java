package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class SummonEndspeakerInLiquidProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double phase = 0;
		double tgtX = 0;
		double tgtY = 0;
		double tgtZ = 0;
		phase = itemstack.getOrCreateTag().getDouble("phase");
		if (entity.isShiftKeyDown()) {
			itemstack.getOrCreateTag().putDouble("phase", ((phase + 1) % 4));
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(((Component.translatable("item.caerula_arbor_reunite.endspeaker_spawnegg.use").getString()).replace("{p}", "" + Math.round(itemstack.getOrCreateTag().getDouble("phase") + 1)))), true);
		}
	}
}
