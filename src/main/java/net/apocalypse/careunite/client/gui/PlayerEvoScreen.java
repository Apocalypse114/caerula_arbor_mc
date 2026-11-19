package net.apocalypse.careunite.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import net.apocalypse.careunite.world.inventory.PlayerEvoMenu;
import net.apocalypse.careunite.procedures.IsNodeWorseBreakTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeWorseBreakTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeWorseBreakTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeWorseBreakTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeSet4DoneProcedure;
import net.apocalypse.careunite.procedures.IsNodeSet3DoneProcedure;
import net.apocalypse.careunite.procedures.IsNodeSet2DoneProcedure;
import net.apocalypse.careunite.procedures.IsNodeSet1DoneProcedure;
import net.apocalypse.careunite.procedures.IsNodeSelectedProcedure;
import net.apocalypse.careunite.procedures.IsNodeRealDamageTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeRealDamageTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeRealDamageTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeRealDamageTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeLivingBarrierTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeLivingBarrierTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeLivingBarrierTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeLivingBarrier4Procedure;
import net.apocalypse.careunite.procedures.IsNodeLessDamageTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeLessDamageTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeLessDamage3Procedure;
import net.apocalypse.careunite.procedures.IsNodeLessDamage2Procedure;
import net.apocalypse.careunite.procedures.IsNodeLessArmorTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeLessArmorTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeLessArmorTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeHealDamageTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeHealDamageTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeEunectesTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeEunectesTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeEunectesTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeEunectesTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeDamageTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeDamageTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddSpeedTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddSpeedTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddSpeedTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddSpeedTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddSanityTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddSanityTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddSanityTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddSanityTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddResisTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddResisTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddResisTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddResisTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddMissTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddMissTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddMissTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddMissTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddDefTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddDefTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddDefTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddDefTo1Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddDamageTo4Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddDamageTo3Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddDamageTo2Procedure;
import net.apocalypse.careunite.procedures.IsNodeAddDamageTo1Procedure;
import net.apocalypse.careunite.procedures.IsNexusRegSanitySelectedProcedure;
import net.apocalypse.careunite.procedures.IsNexusRegLightsSelectedProcedure;
import net.apocalypse.careunite.procedures.IsNexusPercDamageSelectedProcedure;
import net.apocalypse.careunite.procedures.IsNexusNoRejectionSelectedProcedure;
import net.apocalypse.careunite.procedures.IsNexusExpoShieldSelectedProcedure;
import net.apocalypse.careunite.procedures.ISNodeLessArmorTo3Procedure;
import net.apocalypse.careunite.procedures.GetQuantityReserveProcedure;
import net.apocalypse.careunite.procedures.GetQualityReserveProcedure;
import net.apocalypse.careunite.procedures.GetNodeTitleProcedure;
import net.apocalypse.careunite.procedures.GetNodeDescProcedure;
import net.apocalypse.careunite.procedures.G3tNodeDesc1Procedure;
import net.apocalypse.careunite.network.PlayerEvoButtonMessage;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class PlayerEvoScreen extends AbstractContainerScreen<PlayerEvoMenu> {
	private final static HashMap<String, Object> guistate = PlayerEvoMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	ImageButton imagebutton_player_evo_nexus_no_rejection;
	ImageButton imagebutton_player_evo_checkbox;
	ImageButton imagebutton_player_evo_nexus_regnr_sanity;
	ImageButton imagebutton_player_evo_node_add_def;
	ImageButton imagebutton_player_evo_node_add_def1;
	ImageButton imagebutton_player_evo_node_add_def2;
	ImageButton imagebutton_player_evo_node_add_def3;
	ImageButton imagebutton_player_evo_node_add_resis;
	ImageButton imagebutton_player_evo_node_add_resis1;
	ImageButton imagebutton_player_evo_node_add_resis2;
	ImageButton imagebutton_player_evo_node_add_resis3;
	ImageButton imagebutton_player_evo_node_add_speed;
	ImageButton imagebutton_player_evo_node_add_speed1;
	ImageButton imagebutton_player_evo_node_add_speed2;
	ImageButton imagebutton_player_evo_node_add_speed3;
	ImageButton imagebutton_player_evo_node_add_nervous;
	ImageButton imagebutton_player_evo_node_add_nervous1;
	ImageButton imagebutton_player_evo_node_add_nervous2;
	ImageButton imagebutton_player_evo_node_add_nervous3;
	ImageButton imagebutton_player_evo_nexus_regnr_lights;
	ImageButton imagebutton_player_evo_node_add_damage;
	ImageButton imagebutton_player_evo_node_add_damage1;
	ImageButton imagebutton_player_evo_node_add_damage2;
	ImageButton imagebutton_player_evo_node_add_damage3;
	ImageButton imagebutton_player_evo_node_less_damage;
	ImageButton imagebutton_player_evo_node_less_damage1;
	ImageButton imagebutton_player_evo_node_less_damage2;
	ImageButton imagebutton_player_evo_node_less_damage3;
	ImageButton imagebutton_player_evo_node_living_barrier;
	ImageButton imagebutton_player_evo_node_living_barrier1;
	ImageButton imagebutton_player_evo_node_living_barrier2;
	ImageButton imagebutton_player_evo_node_living_barrier3;
	ImageButton imagebutton_player_evo_node_add_miss;
	ImageButton imagebutton_player_evo_node_add_miss1;
	ImageButton imagebutton_player_evo_node_add_miss2;
	ImageButton imagebutton_player_evo_node_add_miss3;
	ImageButton imagebutton_player_evo_nexus_perc_attack;
	ImageButton imagebutton_player_evo_node_real_damage;
	ImageButton imagebutton_player_evo_node_real_damage1;
	ImageButton imagebutton_player_evo_node_real_damage2;
	ImageButton imagebutton_player_evo_node_real_damage3;
	ImageButton imagebutton_player_evo_node_heal_damage;
	ImageButton imagebutton_player_evo_node_heal_damage1;
	ImageButton imagebutton_player_evo_node_heal_damage2;
	ImageButton imagebutton_player_evo_node_heal_damage3;
	ImageButton imagebutton_player_evo_node_worse_break;
	ImageButton imagebutton_player_evo_node_worse_break1;
	ImageButton imagebutton_player_evo_node_worse_break2;
	ImageButton imagebutton_player_evo_node_worse_break3;
	ImageButton imagebutton_player_evo_nexus_expo_shield;
	ImageButton imagebutton_player_evo_talent_eunectes;
	ImageButton imagebutton_player_evo_talent_eunectes1;
	ImageButton imagebutton_player_evo_talent_eunectes2;
	ImageButton imagebutton_player_evo_talent_eunectes3;
	ImageButton imagebutton_player_evo_node_reduce_armor;
	ImageButton imagebutton_player_evo_node_reduce_armor1;
	ImageButton imagebutton_player_evo_node_reduce_armor2;
	ImageButton imagebutton_player_evo_node_reduce_armor3;

	public PlayerEvoScreen(PlayerEvoMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 304;
		this.imageHeight = 216;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if (IsNodeSelectedProcedure.execute(entity))
			if (mouseX > leftPos + 270 && mouseX < leftPos + 296 && mouseY > topPos + 177 && mouseY < topPos + 188)
				guiGraphics.renderTooltip(font, Component.translatable("gui.caerula_arbor_reunite.player_evo.tooltip_click_to_learn_the_node"), mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_bg.png"), this.leftPos + 0, this.topPos + 0, 0, 0, 304, 216, 304, 216);

		if (IsNexusNoRejectionSelectedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 26, this.topPos + 43, 0, 0, 6, 6, 6, 6);
		}
		if (IsNexusNoRejectionSelectedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_short_bar.png"), this.leftPos + 30, this.topPos + 51, 0, 0, 4, 1, 4, 1);
		}
		if (IsNexusRegSanitySelectedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_quart_bar.png"), this.leftPos + 58, this.topPos + 14, 0, 0, 9, 74, 9, 74);
		}
		if (IsNexusRegSanitySelectedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 54, this.topPos + 43, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddDefTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 83, this.topPos + 19, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeAddDefTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 83, this.topPos + 9, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddDefTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 105, this.topPos + 9, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddDefTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 127, this.topPos + 9, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddDefTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 149, this.topPos + 9, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddDefTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 83, this.topPos + 43, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeAddResisTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 83, this.topPos + 33, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddResisTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 105, this.topPos + 33, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddResisTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 127, this.topPos + 33, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddResisTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 149, this.topPos + 33, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddSpeedTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 83, this.topPos + 67, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeAddSpeedTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 83, this.topPos + 57, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddSpeedTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 105, this.topPos + 57, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddSpeedTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 127, this.topPos + 57, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddSpeedTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 149, this.topPos + 57, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddSanityTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 83, this.topPos + 91, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeAddSanityTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 83, this.topPos + 81, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddSanityTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 105, this.topPos + 81, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddSanityTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 127, this.topPos + 81, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddSanityTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 149, this.topPos + 81, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeSet1DoneProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_quart_bar_reverse.png"), this.leftPos + 153, this.topPos + 15, 0, 0, 9, 74, 9, 74);
		}
		if (IsNexusRegLightsSelectedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_quart_bar.png"), this.leftPos + 186, this.topPos + 15, 0, 0, 9, 74, 9, 74);
		}
		if (IsNexusRegLightsSelectedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 182, this.topPos + 44, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddDamageTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 211, this.topPos + 19, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeAddDamageTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 211, this.topPos + 9, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddDamageTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 233, this.topPos + 9, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddDamageTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 255, this.topPos + 9, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddDamageTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 277, this.topPos + 9, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLessDamageTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 211, this.topPos + 43, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeLessDamageTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 211, this.topPos + 33, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLessDamage2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 232, this.topPos + 33, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLessDamage3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 255, this.topPos + 33, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLessDamageTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 277, this.topPos + 33, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLivingBarrierTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 211, this.topPos + 67, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeLivingBarrierTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 211, this.topPos + 57, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLivingBarrierTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 233, this.topPos + 57, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLivingBarrierTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 255, this.topPos + 57, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLivingBarrier4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 277, this.topPos + 57, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddMissTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 211, this.topPos + 91, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeAddMissTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 211, this.topPos + 81, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddMissTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 233, this.topPos + 81, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddMissTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 255, this.topPos + 81, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeAddMissTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 277, this.topPos + 81, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeSet2DoneProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_quart_bar_reverse.png"), this.leftPos + 281, this.topPos + 16, 0, 0, 9, 74, 9, 74);
		}
		if (IsNodeSet2DoneProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_short_bar.png"), this.leftPos + 14, this.topPos + 135, 0, 0, 4, 1, 4, 1);
		}
		if (IsNexusPercDamageSelectedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 38, this.topPos + 127, 0, 0, 6, 6, 6, 6);
		}
		if (IsNexusPercDamageSelectedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_triple_bar.png"), this.leftPos + 42, this.topPos + 110, 0, 0, 9, 50, 9, 50);
		}
		if (IsNodeRealDamageTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 67, this.topPos + 115, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeRealDamageTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 67, this.topPos + 105, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeRealDamageTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 89, this.topPos + 105, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeRealDamageTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 111, this.topPos + 105, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeRealDamageTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 133, this.topPos + 105, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeHealDamageTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 67, this.topPos + 139, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeHealDamageTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 67, this.topPos + 129, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeHealDamageTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 89, this.topPos + 129, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeDamageTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 111, this.topPos + 129, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeDamageTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 133, this.topPos + 129, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeWorseBreakTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 67, this.topPos + 163, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeWorseBreakTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 67, this.topPos + 153, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeWorseBreakTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 89, this.topPos + 153, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeWorseBreakTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 111, this.topPos + 153, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeWorseBreakTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 133, this.topPos + 153, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeSet3DoneProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_triple_bar_reverse.png"), this.leftPos + 137, this.topPos + 111, 0, 0, 9, 50, 9, 50);
		}
		if (IsNexusExpoShieldSelectedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_double_bar.png"), this.leftPos + 170, this.topPos + 123, 0, 0, 9, 26, 9, 26);
		}
		if (IsNexusExpoShieldSelectedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 166, this.topPos + 129, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeEunectesTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 195, this.topPos + 128, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeEunectesTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 195, this.topPos + 118, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeEunectesTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 217, this.topPos + 118, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeEunectesTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 239, this.topPos + 118, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeEunectesTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 261, this.topPos + 118, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLessArmorTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evolution_ids.png"), this.leftPos + 195, this.topPos + 152, 0, 0, 72, 7, 72, 7);
		}
		if (IsNodeLessArmorTo1Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 195, this.topPos + 142, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLessArmorTo2Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 217, this.topPos + 142, 0, 0, 6, 6, 6, 6);
		}
		if (ISNodeLessArmorTo3Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 239, this.topPos + 142, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeLessArmorTo4Procedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_checked.png"), this.leftPos + 261, this.topPos + 142, 0, 0, 6, 6, 6, 6);
		}
		if (IsNodeSet4DoneProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/player_evo_double_bar_reverse.png"), this.leftPos + 265, this.topPos + 124, 0, 0, 9, 26, 9, 26);
		}
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font,

				GetNodeTitleProcedure.execute(entity), 8, 178, -52, false);
		guiGraphics.drawString(this.font,

				GetNodeDescProcedure.execute(entity), 9, 192, -1, false);
		guiGraphics.drawString(this.font,

				GetQuantityReserveProcedure.execute(entity), 3, 3, -13158601, false);
		guiGraphics.drawString(this.font,

				GetQualityReserveProcedure.execute(entity), 3, 14, -16777114, false);
		guiGraphics.drawString(this.font,

				G3tNodeDesc1Procedure.execute(entity), 9, 202, -1, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_player_evo_nexus_no_rejection = new ImageButton(this.leftPos + 10, this.topPos + 44, 16, 16, 0, 0, 16, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_nexus_no_rejection.png"), 16, 32, e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(0, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		});
		guistate.put("button:imagebutton_player_evo_nexus_no_rejection", imagebutton_player_evo_nexus_no_rejection);
		this.addRenderableWidget(imagebutton_player_evo_nexus_no_rejection);
		imagebutton_player_evo_checkbox = new ImageButton(this.leftPos + 270, this.topPos + 176, 26, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_checkbox.png"), 26, 24, e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(1, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		});
		guistate.put("button:imagebutton_player_evo_checkbox", imagebutton_player_evo_checkbox);
		this.addRenderableWidget(imagebutton_player_evo_checkbox);
		imagebutton_player_evo_nexus_regnr_sanity = new ImageButton(this.leftPos + 38, this.topPos + 44, 16, 16, 0, 0, 16, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_nexus_regnr_sanity.png"), 16, 32, e -> {
			if (IsNexusNoRejectionSelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(2, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusNoRejectionSelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_nexus_regnr_sanity", imagebutton_player_evo_nexus_regnr_sanity);
		this.addRenderableWidget(imagebutton_player_evo_nexus_regnr_sanity);
		imagebutton_player_evo_node_add_def = new ImageButton(this.leftPos + 71, this.topPos + 10, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_def.png"), 12, 24, e -> {
			if (IsNexusRegSanitySelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(3, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusRegSanitySelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_def", imagebutton_player_evo_node_add_def);
		this.addRenderableWidget(imagebutton_player_evo_node_add_def);
		imagebutton_player_evo_node_add_def1 = new ImageButton(this.leftPos + 93, this.topPos + 10, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_def1.png"), 12, 24, e -> {
			if (IsNodeAddDefTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(4, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddDefTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_def1", imagebutton_player_evo_node_add_def1);
		this.addRenderableWidget(imagebutton_player_evo_node_add_def1);
		imagebutton_player_evo_node_add_def2 = new ImageButton(this.leftPos + 115, this.topPos + 10, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_def2.png"), 12, 24, e -> {
			if (IsNodeAddDefTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(5, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 5, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddDefTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_def2", imagebutton_player_evo_node_add_def2);
		this.addRenderableWidget(imagebutton_player_evo_node_add_def2);
		imagebutton_player_evo_node_add_def3 = new ImageButton(this.leftPos + 137, this.topPos + 10, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_def3.png"), 12, 24, e -> {
			if (IsNodeAddDefTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(6, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 6, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddDefTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_def3", imagebutton_player_evo_node_add_def3);
		this.addRenderableWidget(imagebutton_player_evo_node_add_def3);
		imagebutton_player_evo_node_add_resis = new ImageButton(this.leftPos + 71, this.topPos + 34, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_resis.png"), 12, 24, e -> {
			if (IsNexusRegSanitySelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(7, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 7, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusRegSanitySelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_resis", imagebutton_player_evo_node_add_resis);
		this.addRenderableWidget(imagebutton_player_evo_node_add_resis);
		imagebutton_player_evo_node_add_resis1 = new ImageButton(this.leftPos + 93, this.topPos + 34, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_resis1.png"), 12, 24, e -> {
			if (IsNodeAddResisTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(8, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 8, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddResisTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_resis1", imagebutton_player_evo_node_add_resis1);
		this.addRenderableWidget(imagebutton_player_evo_node_add_resis1);
		imagebutton_player_evo_node_add_resis2 = new ImageButton(this.leftPos + 115, this.topPos + 34, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_resis2.png"), 12, 24, e -> {
			if (IsNodeAddResisTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(9, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 9, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddResisTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_resis2", imagebutton_player_evo_node_add_resis2);
		this.addRenderableWidget(imagebutton_player_evo_node_add_resis2);
		imagebutton_player_evo_node_add_resis3 = new ImageButton(this.leftPos + 137, this.topPos + 34, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_resis3.png"), 12, 24, e -> {
			if (IsNodeAddResisTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(10, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 10, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddResisTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_resis3", imagebutton_player_evo_node_add_resis3);
		this.addRenderableWidget(imagebutton_player_evo_node_add_resis3);
		imagebutton_player_evo_node_add_speed = new ImageButton(this.leftPos + 71, this.topPos + 58, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_speed.png"), 12, 24, e -> {
			if (IsNexusRegSanitySelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(11, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 11, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusRegSanitySelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_speed", imagebutton_player_evo_node_add_speed);
		this.addRenderableWidget(imagebutton_player_evo_node_add_speed);
		imagebutton_player_evo_node_add_speed1 = new ImageButton(this.leftPos + 93, this.topPos + 58, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_speed1.png"), 12, 24, e -> {
			if (IsNodeAddSpeedTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(12, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 12, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddSpeedTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_speed1", imagebutton_player_evo_node_add_speed1);
		this.addRenderableWidget(imagebutton_player_evo_node_add_speed1);
		imagebutton_player_evo_node_add_speed2 = new ImageButton(this.leftPos + 115, this.topPos + 58, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_speed2.png"), 12, 24, e -> {
			if (IsNodeAddSpeedTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(13, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 13, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddSpeedTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_speed2", imagebutton_player_evo_node_add_speed2);
		this.addRenderableWidget(imagebutton_player_evo_node_add_speed2);
		imagebutton_player_evo_node_add_speed3 = new ImageButton(this.leftPos + 137, this.topPos + 58, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_speed3.png"), 12, 24, e -> {
			if (IsNodeAddSpeedTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(14, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 14, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddSpeedTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_speed3", imagebutton_player_evo_node_add_speed3);
		this.addRenderableWidget(imagebutton_player_evo_node_add_speed3);
		imagebutton_player_evo_node_add_nervous = new ImageButton(this.leftPos + 71, this.topPos + 82, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_nervous.png"), 12, 24, e -> {
			if (IsNexusRegSanitySelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(15, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 15, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusRegSanitySelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_nervous", imagebutton_player_evo_node_add_nervous);
		this.addRenderableWidget(imagebutton_player_evo_node_add_nervous);
		imagebutton_player_evo_node_add_nervous1 = new ImageButton(this.leftPos + 93, this.topPos + 82, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_nervous1.png"), 12, 24, e -> {
			if (IsNodeAddSanityTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(16, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 16, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddSanityTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_nervous1", imagebutton_player_evo_node_add_nervous1);
		this.addRenderableWidget(imagebutton_player_evo_node_add_nervous1);
		imagebutton_player_evo_node_add_nervous2 = new ImageButton(this.leftPos + 115, this.topPos + 82, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_nervous2.png"), 12, 24, e -> {
			if (IsNodeAddSanityTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(17, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 17, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddSanityTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_nervous2", imagebutton_player_evo_node_add_nervous2);
		this.addRenderableWidget(imagebutton_player_evo_node_add_nervous2);
		imagebutton_player_evo_node_add_nervous3 = new ImageButton(this.leftPos + 137, this.topPos + 82, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_nervous3.png"), 12, 24, e -> {
			if (IsNodeAddSanityTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(18, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 18, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddSanityTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_nervous3", imagebutton_player_evo_node_add_nervous3);
		this.addRenderableWidget(imagebutton_player_evo_node_add_nervous3);
		imagebutton_player_evo_nexus_regnr_lights = new ImageButton(this.leftPos + 166, this.topPos + 45, 16, 16, 0, 0, 16, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_nexus_regnr_lights.png"), 16, 32, e -> {
			if (IsNodeSet1DoneProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(19, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 19, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeSet1DoneProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_nexus_regnr_lights", imagebutton_player_evo_nexus_regnr_lights);
		this.addRenderableWidget(imagebutton_player_evo_nexus_regnr_lights);
		imagebutton_player_evo_node_add_damage = new ImageButton(this.leftPos + 199, this.topPos + 10, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_damage.png"), 12, 24, e -> {
			if (IsNexusRegLightsSelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(20, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 20, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusRegLightsSelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_damage", imagebutton_player_evo_node_add_damage);
		this.addRenderableWidget(imagebutton_player_evo_node_add_damage);
		imagebutton_player_evo_node_add_damage1 = new ImageButton(this.leftPos + 221, this.topPos + 10, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_damage1.png"), 12, 24, e -> {
			if (IsNodeAddDamageTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(21, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 21, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddDamageTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_damage1", imagebutton_player_evo_node_add_damage1);
		this.addRenderableWidget(imagebutton_player_evo_node_add_damage1);
		imagebutton_player_evo_node_add_damage2 = new ImageButton(this.leftPos + 243, this.topPos + 10, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_damage2.png"), 12, 24, e -> {
			if (IsNodeAddDamageTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(22, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 22, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddDamageTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_damage2", imagebutton_player_evo_node_add_damage2);
		this.addRenderableWidget(imagebutton_player_evo_node_add_damage2);
		imagebutton_player_evo_node_add_damage3 = new ImageButton(this.leftPos + 265, this.topPos + 10, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_damage3.png"), 12, 24, e -> {
			if (IsNodeAddDamageTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(23, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 23, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddDamageTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_damage3", imagebutton_player_evo_node_add_damage3);
		this.addRenderableWidget(imagebutton_player_evo_node_add_damage3);
		imagebutton_player_evo_node_less_damage = new ImageButton(this.leftPos + 199, this.topPos + 34, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_less_damage.png"), 12, 24, e -> {
			if (IsNexusRegLightsSelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(24, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 24, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusRegLightsSelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_less_damage", imagebutton_player_evo_node_less_damage);
		this.addRenderableWidget(imagebutton_player_evo_node_less_damage);
		imagebutton_player_evo_node_less_damage1 = new ImageButton(this.leftPos + 221, this.topPos + 34, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_less_damage1.png"), 12, 24, e -> {
			if (IsNodeLessDamageTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(25, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 25, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeLessDamageTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_less_damage1", imagebutton_player_evo_node_less_damage1);
		this.addRenderableWidget(imagebutton_player_evo_node_less_damage1);
		imagebutton_player_evo_node_less_damage2 = new ImageButton(this.leftPos + 243, this.topPos + 34, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_less_damage2.png"), 12, 24, e -> {
			if (IsNodeLessDamage2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(26, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 26, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeLessDamage2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_less_damage2", imagebutton_player_evo_node_less_damage2);
		this.addRenderableWidget(imagebutton_player_evo_node_less_damage2);
		imagebutton_player_evo_node_less_damage3 = new ImageButton(this.leftPos + 265, this.topPos + 34, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_less_damage3.png"), 12, 24, e -> {
			if (IsNodeLessDamage3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(27, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 27, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeLessDamage3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_less_damage3", imagebutton_player_evo_node_less_damage3);
		this.addRenderableWidget(imagebutton_player_evo_node_less_damage3);
		imagebutton_player_evo_node_living_barrier = new ImageButton(this.leftPos + 199, this.topPos + 58, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_living_barrier.png"), 12, 24, e -> {
			if (IsNexusRegLightsSelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(28, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 28, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusRegLightsSelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_living_barrier", imagebutton_player_evo_node_living_barrier);
		this.addRenderableWidget(imagebutton_player_evo_node_living_barrier);
		imagebutton_player_evo_node_living_barrier1 = new ImageButton(this.leftPos + 221, this.topPos + 58, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_living_barrier1.png"), 12, 24, e -> {
			if (IsNodeLivingBarrierTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(29, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 29, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeLivingBarrierTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_living_barrier1", imagebutton_player_evo_node_living_barrier1);
		this.addRenderableWidget(imagebutton_player_evo_node_living_barrier1);
		imagebutton_player_evo_node_living_barrier2 = new ImageButton(this.leftPos + 243, this.topPos + 58, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_living_barrier2.png"), 12, 24, e -> {
			if (IsNodeLivingBarrierTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(30, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 30, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeLivingBarrierTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_living_barrier2", imagebutton_player_evo_node_living_barrier2);
		this.addRenderableWidget(imagebutton_player_evo_node_living_barrier2);
		imagebutton_player_evo_node_living_barrier3 = new ImageButton(this.leftPos + 265, this.topPos + 58, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_living_barrier3.png"), 12, 24, e -> {
			if (IsNodeLivingBarrierTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(31, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 31, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeLivingBarrierTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_living_barrier3", imagebutton_player_evo_node_living_barrier3);
		this.addRenderableWidget(imagebutton_player_evo_node_living_barrier3);
		imagebutton_player_evo_node_add_miss = new ImageButton(this.leftPos + 199, this.topPos + 82, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_miss.png"), 12, 24, e -> {
			if (IsNexusRegLightsSelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(32, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 32, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusRegLightsSelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_miss", imagebutton_player_evo_node_add_miss);
		this.addRenderableWidget(imagebutton_player_evo_node_add_miss);
		imagebutton_player_evo_node_add_miss1 = new ImageButton(this.leftPos + 221, this.topPos + 82, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_miss1.png"), 12, 24, e -> {
			if (IsNodeAddMissTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(33, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 33, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddMissTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_miss1", imagebutton_player_evo_node_add_miss1);
		this.addRenderableWidget(imagebutton_player_evo_node_add_miss1);
		imagebutton_player_evo_node_add_miss2 = new ImageButton(this.leftPos + 243, this.topPos + 82, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_miss2.png"), 12, 24, e -> {
			if (IsNodeAddMissTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(34, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 34, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddMissTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_miss2", imagebutton_player_evo_node_add_miss2);
		this.addRenderableWidget(imagebutton_player_evo_node_add_miss2);
		imagebutton_player_evo_node_add_miss3 = new ImageButton(this.leftPos + 265, this.topPos + 82, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_add_miss3.png"), 12, 24, e -> {
			if (IsNodeAddMissTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(35, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 35, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeAddMissTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_add_miss3", imagebutton_player_evo_node_add_miss3);
		this.addRenderableWidget(imagebutton_player_evo_node_add_miss3);
		imagebutton_player_evo_nexus_perc_attack = new ImageButton(this.leftPos + 22, this.topPos + 128, 16, 16, 0, 0, 16, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_nexus_perc_attack.png"), 16, 32, e -> {
			if (IsNodeSet2DoneProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(36, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 36, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeSet2DoneProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_nexus_perc_attack", imagebutton_player_evo_nexus_perc_attack);
		this.addRenderableWidget(imagebutton_player_evo_nexus_perc_attack);
		imagebutton_player_evo_node_real_damage = new ImageButton(this.leftPos + 55, this.topPos + 106, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_real_damage.png"), 12, 24, e -> {
			if (IsNexusPercDamageSelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(37, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 37, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusPercDamageSelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_real_damage", imagebutton_player_evo_node_real_damage);
		this.addRenderableWidget(imagebutton_player_evo_node_real_damage);
		imagebutton_player_evo_node_real_damage1 = new ImageButton(this.leftPos + 77, this.topPos + 106, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_real_damage1.png"), 12, 24, e -> {
			if (IsNodeRealDamageTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(38, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 38, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeRealDamageTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_real_damage1", imagebutton_player_evo_node_real_damage1);
		this.addRenderableWidget(imagebutton_player_evo_node_real_damage1);
		imagebutton_player_evo_node_real_damage2 = new ImageButton(this.leftPos + 99, this.topPos + 106, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_real_damage2.png"), 12, 24, e -> {
			if (IsNodeRealDamageTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(39, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 39, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeRealDamageTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_real_damage2", imagebutton_player_evo_node_real_damage2);
		this.addRenderableWidget(imagebutton_player_evo_node_real_damage2);
		imagebutton_player_evo_node_real_damage3 = new ImageButton(this.leftPos + 121, this.topPos + 106, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_real_damage3.png"), 12, 24, e -> {
			if (IsNodeRealDamageTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(40, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 40, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeRealDamageTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_real_damage3", imagebutton_player_evo_node_real_damage3);
		this.addRenderableWidget(imagebutton_player_evo_node_real_damage3);
		imagebutton_player_evo_node_heal_damage = new ImageButton(this.leftPos + 55, this.topPos + 130, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_heal_damage.png"), 12, 24, e -> {
			if (IsNexusPercDamageSelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(41, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 41, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusPercDamageSelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_heal_damage", imagebutton_player_evo_node_heal_damage);
		this.addRenderableWidget(imagebutton_player_evo_node_heal_damage);
		imagebutton_player_evo_node_heal_damage1 = new ImageButton(this.leftPos + 77, this.topPos + 130, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_heal_damage1.png"), 12, 24, e -> {
			if (IsNodeHealDamageTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(42, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 42, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeHealDamageTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_heal_damage1", imagebutton_player_evo_node_heal_damage1);
		this.addRenderableWidget(imagebutton_player_evo_node_heal_damage1);
		imagebutton_player_evo_node_heal_damage2 = new ImageButton(this.leftPos + 99, this.topPos + 130, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_heal_damage2.png"), 12, 24, e -> {
			if (IsNodeHealDamageTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(43, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 43, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeHealDamageTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_heal_damage2", imagebutton_player_evo_node_heal_damage2);
		this.addRenderableWidget(imagebutton_player_evo_node_heal_damage2);
		imagebutton_player_evo_node_heal_damage3 = new ImageButton(this.leftPos + 121, this.topPos + 130, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_heal_damage3.png"), 12, 24, e -> {
			if (IsNodeDamageTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(44, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 44, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeDamageTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_heal_damage3", imagebutton_player_evo_node_heal_damage3);
		this.addRenderableWidget(imagebutton_player_evo_node_heal_damage3);
		imagebutton_player_evo_node_worse_break = new ImageButton(this.leftPos + 55, this.topPos + 154, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_worse_break.png"), 12, 24, e -> {
			if (IsNexusPercDamageSelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(45, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 45, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusPercDamageSelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_worse_break", imagebutton_player_evo_node_worse_break);
		this.addRenderableWidget(imagebutton_player_evo_node_worse_break);
		imagebutton_player_evo_node_worse_break1 = new ImageButton(this.leftPos + 77, this.topPos + 154, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_worse_break1.png"), 12, 24, e -> {
			if (IsNodeWorseBreakTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(46, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 46, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeWorseBreakTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_worse_break1", imagebutton_player_evo_node_worse_break1);
		this.addRenderableWidget(imagebutton_player_evo_node_worse_break1);
		imagebutton_player_evo_node_worse_break2 = new ImageButton(this.leftPos + 99, this.topPos + 154, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_worse_break2.png"), 12, 24, e -> {
			if (IsNodeWorseBreakTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(47, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 47, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeWorseBreakTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_worse_break2", imagebutton_player_evo_node_worse_break2);
		this.addRenderableWidget(imagebutton_player_evo_node_worse_break2);
		imagebutton_player_evo_node_worse_break3 = new ImageButton(this.leftPos + 121, this.topPos + 154, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_worse_break3.png"), 12, 24, e -> {
			if (IsNodeWorseBreakTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(48, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 48, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeWorseBreakTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_worse_break3", imagebutton_player_evo_node_worse_break3);
		this.addRenderableWidget(imagebutton_player_evo_node_worse_break3);
		imagebutton_player_evo_nexus_expo_shield = new ImageButton(this.leftPos + 150, this.topPos + 130, 16, 16, 0, 0, 16, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_nexus_expo_shield.png"), 16, 32, e -> {
			if (IsNodeSet3DoneProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(49, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 49, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeSet3DoneProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_nexus_expo_shield", imagebutton_player_evo_nexus_expo_shield);
		this.addRenderableWidget(imagebutton_player_evo_nexus_expo_shield);
		imagebutton_player_evo_talent_eunectes = new ImageButton(this.leftPos + 183, this.topPos + 119, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_talent_eunectes.png"), 12, 24, e -> {
			if (IsNexusExpoShieldSelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(50, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 50, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusExpoShieldSelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_talent_eunectes", imagebutton_player_evo_talent_eunectes);
		this.addRenderableWidget(imagebutton_player_evo_talent_eunectes);
		imagebutton_player_evo_talent_eunectes1 = new ImageButton(this.leftPos + 205, this.topPos + 119, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_talent_eunectes1.png"), 12, 24, e -> {
			if (IsNodeEunectesTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(51, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 51, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeEunectesTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_talent_eunectes1", imagebutton_player_evo_talent_eunectes1);
		this.addRenderableWidget(imagebutton_player_evo_talent_eunectes1);
		imagebutton_player_evo_talent_eunectes2 = new ImageButton(this.leftPos + 227, this.topPos + 119, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_talent_eunectes2.png"), 12, 24, e -> {
			if (IsNodeEunectesTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(52, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 52, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeEunectesTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_talent_eunectes2", imagebutton_player_evo_talent_eunectes2);
		this.addRenderableWidget(imagebutton_player_evo_talent_eunectes2);
		imagebutton_player_evo_talent_eunectes3 = new ImageButton(this.leftPos + 249, this.topPos + 119, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_talent_eunectes3.png"), 12, 24, e -> {
			if (IsNodeEunectesTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(53, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 53, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeEunectesTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_talent_eunectes3", imagebutton_player_evo_talent_eunectes3);
		this.addRenderableWidget(imagebutton_player_evo_talent_eunectes3);
		imagebutton_player_evo_node_reduce_armor = new ImageButton(this.leftPos + 183, this.topPos + 143, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_reduce_armor.png"), 12, 24, e -> {
			if (IsNexusExpoShieldSelectedProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(54, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 54, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNexusExpoShieldSelectedProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_reduce_armor", imagebutton_player_evo_node_reduce_armor);
		this.addRenderableWidget(imagebutton_player_evo_node_reduce_armor);
		imagebutton_player_evo_node_reduce_armor1 = new ImageButton(this.leftPos + 205, this.topPos + 143, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_reduce_armor1.png"), 12, 24, e -> {
			if (IsNodeLessArmorTo1Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(55, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 55, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeLessArmorTo1Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_reduce_armor1", imagebutton_player_evo_node_reduce_armor1);
		this.addRenderableWidget(imagebutton_player_evo_node_reduce_armor1);
		imagebutton_player_evo_node_reduce_armor2 = new ImageButton(this.leftPos + 227, this.topPos + 143, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_reduce_armor2.png"), 12, 24, e -> {
			if (IsNodeLessArmorTo2Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(56, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 56, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = IsNodeLessArmorTo2Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_reduce_armor2", imagebutton_player_evo_node_reduce_armor2);
		this.addRenderableWidget(imagebutton_player_evo_node_reduce_armor2);
		imagebutton_player_evo_node_reduce_armor3 = new ImageButton(this.leftPos + 249, this.topPos + 143, 12, 12, 0, 0, 12, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_player_evo_node_reduce_armor3.png"), 12, 24, e -> {
			if (ISNodeLessArmorTo3Procedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new PlayerEvoButtonMessage(57, x, y, z));
				PlayerEvoButtonMessage.handleButtonAction(entity, 57, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = ISNodeLessArmorTo3Procedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_player_evo_node_reduce_armor3", imagebutton_player_evo_node_reduce_armor3);
		this.addRenderableWidget(imagebutton_player_evo_node_reduce_armor3);
	}
}
