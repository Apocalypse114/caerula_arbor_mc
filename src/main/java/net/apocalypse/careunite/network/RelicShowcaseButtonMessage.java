
package net.apocalypse.careunite.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.world.inventory.RelicShowcaseMenu;
import net.apocalypse.careunite.procedures.UngainRelicYearningProcedure;
import net.apocalypse.careunite.procedures.UngainRelicTreatyProcedure;
import net.apocalypse.careunite.procedures.UngainRelicSurvProcedure;
import net.apocalypse.careunite.procedures.UngainRelicSPEARProcedure;
import net.apocalypse.careunite.procedures.UngainRelicHemostProcedure;
import net.apocalypse.careunite.procedures.UngainRelicFlagProcedure;
import net.apocalypse.careunite.procedures.UngainRelicFireworkProcedure;
import net.apocalypse.careunite.procedures.UngainRelicEngraveProcedure;
import net.apocalypse.careunite.procedures.UngainRelicEXTENProcedure;
import net.apocalypse.careunite.procedures.UngainRelicCrownProcedure;
import net.apocalypse.careunite.procedures.UngainRelicChitinProcedure;
import net.apocalypse.careunite.procedures.UngainRelicCRYSTALProcedure;
import net.apocalypse.careunite.procedures.UngainRelicBedProcedure;
import net.apocalypse.careunite.procedures.UngainRelicArtifactProcedure;
import net.apocalypse.careunite.procedures.UngainRelicARMORProcedure;
import net.apocalypse.careunite.procedures.UngainHandThornsProcedure;
import net.apocalypse.careunite.procedures.UngainHandSwordProcedure;
import net.apocalypse.careunite.procedures.UngainHandStrangleProcedure;
import net.apocalypse.careunite.procedures.UngainHandSpeedProcedure;
import net.apocalypse.careunite.procedures.UngainHandSWIPEProcedure;
import net.apocalypse.careunite.procedures.UngainHandFERTILITYProcedure;
import net.apocalypse.careunite.procedures.UngainHandBARRENProcedure;
import net.apocalypse.careunite.procedures.UngainCursedResearchProcedure;
import net.apocalypse.careunite.procedures.UngainCursedGlowbodyProcedure;
import net.apocalypse.careunite.procedures.UngainCursedEMELIGHTProcedure;
import net.apocalypse.careunite.procedures.InnerOpenCaerulaProcedure;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RelicShowcaseButtonMessage {
	private final int buttonID, x, y, z;

	public RelicShowcaseButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public RelicShowcaseButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(RelicShowcaseButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(RelicShowcaseButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
		HashMap guistate = RelicShowcaseMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			InnerOpenCaerulaProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			UngainRelicCrownProcedure.execute(entity);
		}
		if (buttonID == 2) {

			UngainRelicSPEARProcedure.execute(entity);
		}
		if (buttonID == 3) {

			UngainRelicARMORProcedure.execute(entity);
		}
		if (buttonID == 4) {

			UngainRelicEXTENProcedure.execute(entity);
		}
		if (buttonID == 5) {

			UngainRelicCRYSTALProcedure.execute(entity);
		}
		if (buttonID == 6) {

			UngainRelicArtifactProcedure.execute(entity);
		}
		if (buttonID == 7) {

			UngainRelicFlagProcedure.execute(entity);
		}
		if (buttonID == 8) {

			UngainRelicBedProcedure.execute(entity);
		}
		if (buttonID == 10) {

			UngainHandThornsProcedure.execute(entity);
		}
		if (buttonID == 11) {

			UngainHandStrangleProcedure.execute(entity);
		}
		if (buttonID == 12) {

			UngainHandFERTILITYProcedure.execute(entity);
		}
		if (buttonID == 13) {

			UngainHandBARRENProcedure.execute(entity);
		}
		if (buttonID == 14) {

			UngainHandSWIPEProcedure.execute(entity);
		}
		if (buttonID == 15) {

			UngainRelicEngraveProcedure.execute(entity);
		}
		if (buttonID == 16) {

			UngainRelicFireworkProcedure.execute(entity);
		}
		if (buttonID == 17) {

			UngainRelicTreatyProcedure.execute(entity);
		}
		if (buttonID == 18) {

			UngainRelicSurvProcedure.execute(entity);
		}
		if (buttonID == 19) {

			UngainCursedEMELIGHTProcedure.execute(entity);
		}
		if (buttonID == 20) {

			UngainCursedGlowbodyProcedure.execute(entity);
		}
		if (buttonID == 21) {

			UngainCursedResearchProcedure.execute(entity);
		}
		if (buttonID == 35) {

			UngainHandSwordProcedure.execute(entity);
		}
		if (buttonID == 36) {

			UngainRelicChitinProcedure.execute(entity);
		}
		if (buttonID == 37) {

			UngainHandSpeedProcedure.execute(entity);
		}
		if (buttonID == 38) {

			UngainRelicHemostProcedure.execute(entity);
		}
		if (buttonID == 39) {

			UngainRelicYearningProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		CaerulaArborMod.addNetworkMessage(RelicShowcaseButtonMessage.class, RelicShowcaseButtonMessage::buffer, RelicShowcaseButtonMessage::new, RelicShowcaseButtonMessage::handler);
	}
}
