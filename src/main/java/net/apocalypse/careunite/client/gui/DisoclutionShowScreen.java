package net.apocalypse.careunite.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import net.apocalypse.careunite.world.inventory.DisoclutionShowMenu;
import net.apocalypse.careunite.network.DisoclutionShowButtonMessage;

import net.apocalypse.careunite.procedures.HasDisoNeuroProcedure;
import net.apocalypse.careunite.procedures.HasDisoFleshProcedure;
import net.apocalypse.careunite.procedures.HasDisoBloodProcedure;
import net.apocalypse.careunite.procedures.HasDisoAttentionProcedure;

import java.util.HashMap;
import net.apocalypse.careunite.CaerulaArborMod;

import com.mojang.blaze3d.systems.RenderSystem;
import org.checkerframework.checker.units.qual.degrees;
import net.minecraft.client.gui.Font;

public class DisoclutionShowScreen extends AbstractContainerScreen<DisoclutionShowMenu> {
	private final static HashMap<String, Object> guistate = DisoclutionShowMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	ImageButton imagebutton_button_16_48_linked;
	ImageButton imagebutton_button_16_48_linked1;
	ImageButton imagebutton_button_16_48;

	public DisoclutionShowScreen(DisoclutionShowMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 144;
	}

	private static final ResourceLocation texture = new ResourceLocation("caerula_arbor_reunite:textures/screens/disoclution_show.png");

	private static final ResourceLocation res_attention = new ResourceLocation("caerula_arbor_reunite:textures/screens/disoclution_attention.png");
	private static final ResourceLocation res_blood = new ResourceLocation("caerula_arbor_reunite:textures/screens/disoclution_blood.png");
	private static final ResourceLocation res_flesh = new ResourceLocation("caerula_arbor_reunite:textures/screens/disoclution_flesh.png");
	private static final ResourceLocation res_neuro = new ResourceLocation("caerula_arbor_reunite:textures/screens/disoclution_neuro.png");

	private static final ResourceLocation button_normal = new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_button_16_48.png");
	private static final ResourceLocation button_linked = new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_button_16_48_linked.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);

		boolean attention = HasDisoAttentionProcedure.execute(entity);
		boolean blood = HasDisoBloodProcedure.execute(entity);
		boolean flesh = HasDisoFleshProcedure.execute(entity);
		boolean neuro = HasDisoNeuroProcedure.execute(entity);

		int centerX = this.leftPos + 40;
		int centerY = this.topPos + 56;
		double mouthD = Math.sqrt((double)(Math.pow(mouseX - guiGraphics.guiWidth()/2, 2) + Math.pow(mouseY - guiGraphics.guiHeight()/2, 2)));
		
		if (!attention && !blood && !flesh && !neuro) {
			guiGraphics.drawString(this.font, Component.literal("NO INFO"), centerX, centerY, 0x353535, false);
		}

		int t = entity.tickCount;
		
		if (attention) {
			renderRejection(guiGraphics, t, centerX, centerY, mouthD, this.font,
				res_attention,
				Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_disconcentration"),
				Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.tooltip_discon")
			);
		}
		if (blood) {
			t += 90;
			renderRejection(guiGraphics, t, centerX, centerY, mouthD, this.font,
				res_blood,
				Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_haemophilia"),
				Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.tooltip_haemp")
			);
		}
		if (flesh) {
			t += 90;
			renderRejection(guiGraphics, t, centerX, centerY, mouthD, this.font,
				res_flesh,
				Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_deformity"),
				Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.tooltip_flesh")
			);
		}
		if (neuro) {
			t += 90;
			renderRejection(guiGraphics, t, centerX, centerY, mouthD, this.font,
				res_neuro,
				Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_neurodegression"),
				Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.tooltip_neuro")
			);
		}
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	private static void renderRejection(
		GuiGraphics guiGraphics, int t, int centerX, int centerY, double mouthD, Font pFont,
		ResourceLocation res, Component name, Component desc){
		int rX = centerX + degCos(t, 32 + lnDist(mouthD)), rY = centerY + degSin(t, 32 + lnDist(mouthD));
		guiGraphics.blit(res, rX, rY, 0, 0, 32, 32, 32, 32);
		guiGraphics.drawString(pFont, name, rX + 1, rY + 32, 0x5B1586, false);
		guiGraphics.drawString(pFont, name, rX, rY + 32, -1335556, false);
		guiGraphics.renderTooltip(pFont, desc, rX + 32, rY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	
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
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.disoclution_show.label_echo"),
			-44, 26, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.disoclution_show.label_rejection"),
			-44, 6, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.disoclution_show.label_enlightment"),
			-44, 46, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.disoclution_show.label_cognitive_symbolic_space"),
			1, -9, -1, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_button_16_48_linked = new ImageButton(this.leftPos + -47, this.topPos + 2, 50, 18, 0, 0, 18, button_linked, 50, 36, e -> {
		});
		guistate.put("button:imagebutton_button_16_48_linked", imagebutton_button_16_48_linked);
		this.addRenderableWidget(imagebutton_button_16_48_linked);
		imagebutton_button_16_48_linked1 = new ImageButton(this.leftPos + -47, this.topPos + 22, 50, 18, 0, 0, 18, button_normal, 50, 36, e -> {
			CaerulaArborMod.PACKET_HANDLER.sendToServer(new DisoclutionShowButtonMessage(1, x, y, z));
			DisoclutionShowButtonMessage.handleButtonAction(entity, 1, x, y, z);
		});
		guistate.put("button:imagebutton_button_16_48_linked1", imagebutton_button_16_48_linked1);
		this.addRenderableWidget(imagebutton_button_16_48_linked1);
		imagebutton_button_16_48 = new ImageButton(this.leftPos + -47, this.topPos + 42, 50, 18, 0, 0, 18, button_normal, 50, 36, e -> {
			CaerulaArborMod.PACKET_HANDLER.sendToServer(new DisoclutionShowButtonMessage(2, x, y, z));
			DisoclutionShowButtonMessage.handleButtonAction(entity, 2, x, y, z);
		});
		guistate.put("button:imagebutton_button_16_48", imagebutton_button_16_48);
		this.addRenderableWidget(imagebutton_button_16_48);
	}

	private static int degCos(int d, int r){
		return (int)(r * Math.cos(Math.toRadians(d * 1.5)));
	}
	private static int degSin(int d, int r){
		return (int)(r * Math.sin(Math.toRadians(d * 1.5)));
	}
	private static int lnDist(double x){
		return (int) (Math.log(Math.abs(x)+1));
	}
}
