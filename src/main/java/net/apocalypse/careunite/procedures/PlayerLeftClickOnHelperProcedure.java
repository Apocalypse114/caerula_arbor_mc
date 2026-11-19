package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.LittleHelperEntity;
import net.apocalypse.careunite.entity.Al1SHelperEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(value = {Dist.CLIENT})
public class PlayerLeftClickOnHelperProcedure {
	@SubscribeEvent
	public static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
		CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerLeftClickOnHelperMessage());
		execute(event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class PlayerLeftClickOnHelperMessage {
		public PlayerLeftClickOnHelperMessage() {
		}

		public PlayerLeftClickOnHelperMessage(FriendlyByteBuf buffer) {
		}

		public static void buffer(PlayerLeftClickOnHelperMessage message, FriendlyByteBuf buffer) {
		}

		public static void handler(PlayerLeftClickOnHelperMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
			CaerulaArborMod.addNetworkMessage(PlayerLeftClickOnHelperMessage.class, PlayerLeftClickOnHelperMessage::buffer, PlayerLeftClickOnHelperMessage::new, PlayerLeftClickOnHelperMessage::handler);
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity helper = null;
		if (entity.isPassenger()) {
			helper = entity.getVehicle();
			if (helper instanceof LittleHelperEntity || helper instanceof Al1SHelperEntity) {
				if (!world.isClientSide() && helper instanceof Al1SHelperEntity) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:al1s_work")), SoundSource.BLOCKS, 3, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:al1s_work")), SoundSource.BLOCKS, 3, 1, false);
						}
					}
				}
				ClearNetherseaAroundProcedure.execute(world, helper.getX(), helper.getY() - 1, helper.getZ(), helper);
			}
		}
	}
}
