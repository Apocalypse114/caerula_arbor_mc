package net.apocalypse.careunite.procedures;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.FriendlyByteBuf;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(value = {Dist.CLIENT})
public class HighmoreScytheAirAttackProcedure {
	@SubscribeEvent
	public static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
		CaerulaArborMod.PACKET_HANDLER.sendToServer(new HighmoreScytheAirAttackMessage());
		execute(event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class HighmoreScytheAirAttackMessage {
		public HighmoreScytheAirAttackMessage() {
		}

		public HighmoreScytheAirAttackMessage(FriendlyByteBuf buffer) {
		}

		public static void buffer(HighmoreScytheAirAttackMessage message, FriendlyByteBuf buffer) {
		}

		public static void handler(HighmoreScytheAirAttackMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getSender().level().hasChunkAt(context.getSender().blockPosition()))
					return;
				execute(context.getSender().level(), context.getSender().getX(), context.getSender().getY(), context.getSender().getZ(), context.getSender());
			});
			context.setPacketHandled(true);
		}

		@SubscribeEvent
		public static void registerMessage(FMLCommonSetupEvent event) {
			CaerulaArborMod.addNetworkMessage(HighmoreScytheAirAttackMessage.class, HighmoreScytheAirAttackMessage::buffer, HighmoreScytheAirAttackMessage::new, HighmoreScytheAirAttackMessage::handler);
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.HIGHMORE_SCYTHE.get()) {
			HighmoreScytheReapProcedure.execute(world, x, y, z, entity);
		}
	}
}
