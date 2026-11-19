
package net.apocalypse.careunite.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.world.inventory.PlayerEvoMenu;
import net.apocalypse.careunite.procedures.SelectNodeWorseBreak4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeWorseBreak3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeWorseBreak2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeRealDamage4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeRealDamage2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeRealDamage1Procedure;
import net.apocalypse.careunite.procedures.SelectNodeLivingBarrierProcedure;
import net.apocalypse.careunite.procedures.SelectNodeLivingBarrier3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeLivingBarrier2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeLivingBarrier1Procedure;
import net.apocalypse.careunite.procedures.SelectNodeLessDamage4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeLessDamage3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeLessDamage2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeLessDamage1Procedure;
import net.apocalypse.careunite.procedures.SelectNodeLessArmorProcedure;
import net.apocalypse.careunite.procedures.SelectNodeLessArmor4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeLessArmor3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeLessArmor2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeHealDamageProcedure;
import net.apocalypse.careunite.procedures.SelectNodeHealDamage4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeHealDamage3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeHealDamage2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeEunetctes2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeEunectesTalentProcedure;
import net.apocalypse.careunite.procedures.SelectNodeEunectes4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddSpeed4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddSpeed3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddSpeed2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddSpeed1Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddSanity4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddSanity3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddSanity2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddSanity1Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddResis4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddResis3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddResis2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddResis1Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddMiss4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddMiss3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddMiss2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddMiss1Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddDef4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddDef3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddDef2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddDef1Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddDamage4Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddDamage3Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddDamage2Procedure;
import net.apocalypse.careunite.procedures.SelectNodeAddDamage1Procedure;
import net.apocalypse.careunite.procedures.SelectNexusRegSanityProcedure;
import net.apocalypse.careunite.procedures.SelectNexusRegLightsProcedure;
import net.apocalypse.careunite.procedures.SelectNexusPercDamageProcedure;
import net.apocalypse.careunite.procedures.SelectNexusNoRejectionProcedure;
import net.apocalypse.careunite.procedures.SelectNexusExpoShieldProcedure;
import net.apocalypse.careunite.procedures.SelectEunectes3Procedure;
import net.apocalypse.careunite.procedures.SeelctNodeWorseBreak1Procedure;
import net.apocalypse.careunite.procedures.SeelctNodeRealDamage3Procedure;
import net.apocalypse.careunite.procedures.CheckNodesProcedure;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerEvoButtonMessage {
	private final int buttonID, x, y, z;

	public PlayerEvoButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public PlayerEvoButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(PlayerEvoButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(PlayerEvoButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
		HashMap guistate = PlayerEvoMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			SelectNexusNoRejectionProcedure.execute(entity);
		}
		if (buttonID == 1) {

			CheckNodesProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 2) {

			SelectNexusRegSanityProcedure.execute(entity);
		}
		if (buttonID == 3) {

			SelectNodeAddDef1Procedure.execute(entity);
		}
		if (buttonID == 4) {

			SelectNodeAddDef2Procedure.execute(entity);
		}
		if (buttonID == 5) {

			SelectNodeAddDef3Procedure.execute(entity);
		}
		if (buttonID == 6) {

			SelectNodeAddDef4Procedure.execute(entity);
		}
		if (buttonID == 7) {

			SelectNodeAddResis1Procedure.execute(entity);
		}
		if (buttonID == 8) {

			SelectNodeAddResis2Procedure.execute(entity);
		}
		if (buttonID == 9) {

			SelectNodeAddResis3Procedure.execute(entity);
		}
		if (buttonID == 10) {

			SelectNodeAddResis4Procedure.execute(entity);
		}
		if (buttonID == 11) {

			SelectNodeAddSpeed1Procedure.execute(entity);
		}
		if (buttonID == 12) {

			SelectNodeAddSpeed2Procedure.execute(entity);
		}
		if (buttonID == 13) {

			SelectNodeAddSpeed3Procedure.execute(entity);
		}
		if (buttonID == 14) {

			SelectNodeAddSpeed4Procedure.execute(entity);
		}
		if (buttonID == 15) {

			SelectNodeAddSanity1Procedure.execute(entity);
		}
		if (buttonID == 16) {

			SelectNodeAddSanity2Procedure.execute(entity);
		}
		if (buttonID == 17) {

			SelectNodeAddSanity3Procedure.execute(entity);
		}
		if (buttonID == 18) {

			SelectNodeAddSanity4Procedure.execute(entity);
		}
		if (buttonID == 19) {

			SelectNexusRegLightsProcedure.execute(entity);
		}
		if (buttonID == 20) {

			SelectNodeAddDamage1Procedure.execute(entity);
		}
		if (buttonID == 21) {

			SelectNodeAddDamage2Procedure.execute(entity);
		}
		if (buttonID == 22) {

			SelectNodeAddDamage3Procedure.execute(entity);
		}
		if (buttonID == 23) {

			SelectNodeAddDamage4Procedure.execute(entity);
		}
		if (buttonID == 24) {

			SelectNodeLessDamage1Procedure.execute(entity);
		}
		if (buttonID == 25) {

			SelectNodeLessDamage2Procedure.execute(entity);
		}
		if (buttonID == 26) {

			SelectNodeLessDamage3Procedure.execute(entity);
		}
		if (buttonID == 27) {

			SelectNodeLessDamage4Procedure.execute(entity);
		}
		if (buttonID == 28) {

			SelectNodeLivingBarrier1Procedure.execute(entity);
		}
		if (buttonID == 29) {

			SelectNodeLivingBarrier2Procedure.execute(entity);
		}
		if (buttonID == 30) {

			SelectNodeLivingBarrier3Procedure.execute(entity);
		}
		if (buttonID == 31) {

			SelectNodeLivingBarrierProcedure.execute(entity);
		}
		if (buttonID == 32) {

			SelectNodeAddMiss1Procedure.execute(entity);
		}
		if (buttonID == 33) {

			SelectNodeAddMiss2Procedure.execute(entity);
		}
		if (buttonID == 34) {

			SelectNodeAddMiss3Procedure.execute(entity);
		}
		if (buttonID == 35) {

			SelectNodeAddMiss4Procedure.execute(entity);
		}
		if (buttonID == 36) {

			SelectNexusPercDamageProcedure.execute(entity);
		}
		if (buttonID == 37) {

			SelectNodeRealDamage1Procedure.execute(entity);
		}
		if (buttonID == 38) {

			SelectNodeRealDamage2Procedure.execute(entity);
		}
		if (buttonID == 39) {

			SeelctNodeRealDamage3Procedure.execute(entity);
		}
		if (buttonID == 40) {

			SelectNodeRealDamage4Procedure.execute(entity);
		}
		if (buttonID == 41) {

			SelectNodeHealDamageProcedure.execute(entity);
		}
		if (buttonID == 42) {

			SelectNodeHealDamage2Procedure.execute(entity);
		}
		if (buttonID == 43) {

			SelectNodeHealDamage3Procedure.execute(entity);
		}
		if (buttonID == 44) {

			SelectNodeHealDamage4Procedure.execute(entity);
		}
		if (buttonID == 45) {

			SeelctNodeWorseBreak1Procedure.execute(entity);
		}
		if (buttonID == 46) {

			SelectNodeWorseBreak2Procedure.execute(entity);
		}
		if (buttonID == 47) {

			SelectNodeWorseBreak3Procedure.execute(entity);
		}
		if (buttonID == 48) {

			SelectNodeWorseBreak4Procedure.execute(entity);
		}
		if (buttonID == 49) {

			SelectNexusExpoShieldProcedure.execute(entity);
		}
		if (buttonID == 50) {

			SelectNodeEunectesTalentProcedure.execute(entity);
		}
		if (buttonID == 51) {

			SelectNodeEunetctes2Procedure.execute(entity);
		}
		if (buttonID == 52) {

			SelectEunectes3Procedure.execute(entity);
		}
		if (buttonID == 53) {

			SelectNodeEunectes4Procedure.execute(entity);
		}
		if (buttonID == 54) {

			SelectNodeLessArmorProcedure.execute(entity);
		}
		if (buttonID == 55) {

			SelectNodeLessArmor2Procedure.execute(entity);
		}
		if (buttonID == 56) {

			SelectNodeLessArmor3Procedure.execute(entity);
		}
		if (buttonID == 57) {

			SelectNodeLessArmor4Procedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		CaerulaArborMod.addNetworkMessage(PlayerEvoButtonMessage.class, PlayerEvoButtonMessage::buffer, PlayerEvoButtonMessage::new, PlayerEvoButtonMessage::handler);
	}
}
