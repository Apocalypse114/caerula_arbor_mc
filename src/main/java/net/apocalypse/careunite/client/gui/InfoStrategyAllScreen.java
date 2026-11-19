package net.apocalypse.careunite.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.apocalypse.careunite.world.inventory.InfoStrategyAllMenu;
import net.apocalypse.careunite.procedures.IfCanSublimeProcedure;
import net.apocalypse.careunite.procedures.IfCanSilenceProcedure;
import net.apocalypse.careunite.procedures.GetStraSilenceProcedure;
import net.apocalypse.careunite.procedures.GetPointSublimationProcedure;
import net.apocalypse.careunite.procedures.GetPointSilenceProcedure;
import net.apocalypse.careunite.procedures.GetEvoSublimationProcedure;
import net.apocalypse.careunite.network.InfoStrategyAllButtonMessage;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class InfoStrategyAllScreen extends AbstractContainerScreen<InfoStrategyAllMenu> {
	private final static HashMap<String, Object> guistate = InfoStrategyAllMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_evolution_tree;
	ImageButton imagebutton_breed_lit;
	ImageButton imagebutton_grow_lit;
	ImageButton imagebutton_mig_lit;
	ImageButton imagebutton_subs_lit;

	public InfoStrategyAllScreen(InfoStrategyAllMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 200;
		this.imageHeight = 120;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if (IfCanSilenceProcedure.execute(world))
			if (mouseX > leftPos + 3 && mouseX < leftPos + 27 && mouseY > topPos + -29 && mouseY < topPos + -5)
				guiGraphics.renderTooltip(font, Component.literal(GetPointSilenceProcedure.execute(world)), mouseX, mouseY);
		if (mouseX > leftPos + 105 && mouseX < leftPos + 129 && mouseY > topPos + 33 && mouseY < topPos + 57)
			guiGraphics.renderTooltip(font, Component.translatable("gui.caerula_arbor_reunite.info_strategy_all.tooltip_breed"), mouseX, mouseY);
		if (mouseX > leftPos + 34 && mouseX < leftPos + 58 && mouseY > topPos + 33 && mouseY < topPos + 57)
			guiGraphics.renderTooltip(font, Component.translatable("gui.caerula_arbor_reunite.info_strategy_all.tooltip_grow"), mouseX, mouseY);
		if (mouseX > leftPos + 143 && mouseX < leftPos + 167 && mouseY > topPos + 33 && mouseY < topPos + 57)
			guiGraphics.renderTooltip(font, Component.translatable("gui.caerula_arbor_reunite.info_strategy_all.tooltip_mig"), mouseX, mouseY);
		if (mouseX > leftPos + 72 && mouseX < leftPos + 96 && mouseY > topPos + 33 && mouseY < topPos + 57)
			guiGraphics.renderTooltip(font, Component.translatable("gui.caerula_arbor_reunite.info_strategy_all.tooltip_subs"), mouseX, mouseY);
		if (IfCanSublimeProcedure.execute(world))
			if (mouseX > leftPos + 36 && mouseX < leftPos + 61 && mouseY > topPos + -30 && mouseY < topPos + -5)
				guiGraphics.renderTooltip(font, Component.literal(GetPointSublimationProcedure.execute(world)), mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/wetplayer.png"), this.leftPos + 0, this.topPos + 0, 0, 0, 200, 120, 200, 120);

		guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/silence.png"), this.leftPos + 0, this.topPos + -34, Mth.clamp((int) GetStraSilenceProcedure.execute(world) * 29, 0, 116), 0, 29, 33, 145, 33);

		if (IfCanSublimeProcedure.execute(world)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/sublimation.png"), this.leftPos + 35, this.topPos + -33, Mth.clamp((int) GetEvoSublimationProcedure.execute(world) * 28, 0, 112), 0, 28, 32, 140, 32);
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
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.info_strategy_all.label_tide_observation"), 0, -1, -11801895, false);
	}

	@Override
	public void init() {
		super.init();
		button_evolution_tree = new PlainTextButton(this.leftPos + -1, this.topPos + 121, 76, 20, Component.translatable("gui.caerula_arbor_reunite.info_strategy_all.button_evolution_tree"), e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new InfoStrategyAllButtonMessage(0, x, y, z));
				InfoStrategyAllButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}, this.font);
		guistate.put("button:button_evolution_tree", button_evolution_tree);
		this.addRenderableWidget(button_evolution_tree);
		imagebutton_breed_lit = new ImageButton(this.leftPos + 100, this.topPos + 29, 32, 32, 0, 0, 32, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_breed_lit.png"), 32, 64, e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new InfoStrategyAllButtonMessage(1, x, y, z));
				InfoStrategyAllButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		});
		guistate.put("button:imagebutton_breed_lit", imagebutton_breed_lit);
		this.addRenderableWidget(imagebutton_breed_lit);
		imagebutton_grow_lit = new ImageButton(this.leftPos + 30, this.topPos + 29, 32, 32, 0, 0, 32, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_grow_lit.png"), 32, 64, e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new InfoStrategyAllButtonMessage(2, x, y, z));
				InfoStrategyAllButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		});
		guistate.put("button:imagebutton_grow_lit", imagebutton_grow_lit);
		this.addRenderableWidget(imagebutton_grow_lit);
		imagebutton_mig_lit = new ImageButton(this.leftPos + 138, this.topPos + 29, 32, 32, 0, 0, 32, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_mig_lit.png"), 32, 64, e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new InfoStrategyAllButtonMessage(3, x, y, z));
				InfoStrategyAllButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		});
		guistate.put("button:imagebutton_mig_lit", imagebutton_mig_lit);
		this.addRenderableWidget(imagebutton_mig_lit);
		imagebutton_subs_lit = new ImageButton(this.leftPos + 67, this.topPos + 30, 32, 32, 0, 0, 32, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_subs_lit.png"), 32, 64, e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new InfoStrategyAllButtonMessage(4, x, y, z));
				InfoStrategyAllButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		});
		guistate.put("button:imagebutton_subs_lit", imagebutton_subs_lit);
		this.addRenderableWidget(imagebutton_subs_lit);
	}
}
