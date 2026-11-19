
package net.apocalypse.careunite.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.world.inventory.CentrifugerSelectMenu;
import net.apocalypse.careunite.procedures.DoneCentrifugeProcedure;
import net.apocalypse.careunite.procedures.CentriSelectiontoSpecterProcedure;
import net.apocalypse.careunite.procedures.CentriSelectionToGladiiaProcedure;
import net.apocalypse.careunite.procedures.CentriSelectToUlpiansProcedure;
import net.apocalypse.careunite.procedures.CentriSelectToSkadiProcedure;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CentrifugerSelectButtonMessage {
	private final int buttonID, x, y, z;

	public CentrifugerSelectButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public CentrifugerSelectButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(CentrifugerSelectButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(CentrifugerSelectButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			Player entity = context.getSender();
			int buttonID = message.buttonID;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			handleButtonAction(entity, buttonID, x, y, z);
		});
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = CentrifugerSelectMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			CentriSelectToSkadiProcedure.execute(entity);
		}
		if (buttonID == 1) {

			CentriSelectToUlpiansProcedure.execute(entity);
		}
		if (buttonID == 2) {

			CentriSelectionToGladiiaProcedure.execute(entity);
		}
		if (buttonID == 3) {

			DoneCentrifugeProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 4) {

			CentriSelectiontoSpecterProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		CaerulaArborMod.addNetworkMessage(CentrifugerSelectButtonMessage.class, CentrifugerSelectButtonMessage::buffer, CentrifugerSelectButtonMessage::new, CentrifugerSelectButtonMessage::handler);
	}
}
