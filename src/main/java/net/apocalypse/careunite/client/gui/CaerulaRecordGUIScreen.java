package net.apocalypse.careunite.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.apocalypse.careunite.world.inventory.CaerulaRecordGUIMenu;
import net.apocalypse.careunite.procedures.LightIsWavingProcedure;
import net.apocalypse.careunite.procedures.LightIsDimProcedure;
import net.apocalypse.careunite.procedures.LightIsCeasedProcedure;
import net.apocalypse.careunite.procedures.LightIsBrightProcedure;
import net.apocalypse.careunite.procedures.IsDifficultProcedure;
import net.apocalypse.careunite.procedures.GetShieldProcedure;
import net.apocalypse.careunite.procedures.GetSanityProcedure;
import net.apocalypse.careunite.procedures.GetSanityIndexProcedure;
import net.apocalypse.careunite.procedures.GetPlayerEntityProcedure;
import net.apocalypse.careunite.procedures.GetOceanizeStateProcedure;
import net.apocalypse.careunite.procedures.GetOceanizeIndexProcedure;
import net.apocalypse.careunite.procedures.GetLivesProcedure;
import net.apocalypse.careunite.procedures.GetLiveMaxShownProcedure;
import net.apocalypse.careunite.procedures.GetLightFloatProcedure;
import net.apocalypse.careunite.procedures.GetHealthProcedure;
import net.apocalypse.careunite.procedures.GetDifficultyProcedure;
import net.apocalypse.careunite.procedures.CanPlayerEvoProcedure;
import net.apocalypse.careunite.network.CaerulaRecordGUIButtonMessage;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class CaerulaRecordGUIScreen extends AbstractContainerScreen<CaerulaRecordGUIMenu> {
	private final static HashMap<String, Object> guistate = CaerulaRecordGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_show_on_hud;
	Button button_show_relic_ptc;
	ImageButton imagebutton_relic_icon;
	ImageButton imagebutton_nurture_gene_set;
	ImageButton imagebutton_cognize_space;

	public CaerulaRecordGUIScreen(CaerulaRecordGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 168;
		this.imageHeight = 166;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		if (GetPlayerEntityProcedure.execute(entity) instanceof LivingEntity livingEntity) {
			InventoryScreen.renderEntityInInventoryFollowsAngle(guiGraphics, this.leftPos + 86, this.topPos + 67, 30, 0f + (float) Math.atan((this.leftPos + 86 - mouseX) / 40.0), (float) Math.atan((this.topPos + 18 - mouseY) / 40.0), livingEntity);
		}
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if (mouseX > leftPos + 4 && mouseX < leftPos + 28 && mouseY > topPos + 131 && mouseY < topPos + 155)
			guiGraphics.renderTooltip(font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.tooltip_occupy_warn"), mouseX, mouseY);
		if (mouseX > leftPos + 4 && mouseX < leftPos + 20 && mouseY > topPos + 91 && mouseY < topPos + 107)
			guiGraphics.renderTooltip(font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.tooltip_show_relics"), mouseX, mouseY);
		if (mouseX > leftPos + 5 && mouseX < leftPos + 16 && mouseY > topPos + 32 && mouseY < topPos + 43)
			guiGraphics.renderTooltip(font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.tooltip_playerlife"), mouseX, mouseY);
		if (mouseX > leftPos + 5 && mouseX < leftPos + 16 && mouseY > topPos + 53 && mouseY < topPos + 64)
			guiGraphics.renderTooltip(font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.tooltip_playershield"), mouseX, mouseY);
		if (mouseX > leftPos + 140 && mouseX < leftPos + 165 && mouseY > topPos + 3 && mouseY < topPos + 23)
			guiGraphics.renderTooltip(font, Component.literal(GetOceanizeStateProcedure.execute(entity)), mouseX, mouseY);
		if (CanPlayerEvoProcedure.execute(entity))
			if (mouseX > leftPos + 36 && mouseX < leftPos + 52 && mouseY > topPos + 91 && mouseY < topPos + 107)
				guiGraphics.renderTooltip(font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.tooltip_playerevolution"), mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/caerularecord.png"), this.leftPos + 0, this.topPos + 0, 0, 0, 168, 166, 168, 166);

		guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/target_health.png"), this.leftPos + 4, this.topPos + 30, 0, 0, 24, 16, 24, 16);

		guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/target_shield.png"), this.leftPos + 4, this.topPos + 50, 0, 0, 24, 16, 24, 16);

		if (LightIsBrightProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/light.png"), this.leftPos + 36, this.topPos + -37, 0, 0, 64, 32, 64, 32);
		}
		if (LightIsWavingProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/light_waving.png"), this.leftPos + 36, this.topPos + -37, 0, 0, 64, 32, 64, 32);
		}
		if (LightIsDimProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/light_dim.png"), this.leftPos + 36, this.topPos + -37, 0, 0, 64, 32, 64, 32);
		}
		if (LightIsCeasedProcedure.execute(entity)) {
			guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/light_extinguish.png"), this.leftPos + 36, this.topPos + -37, 0, 0, 64, 32, 64, 32);
		}

		guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/sanity.png"), this.leftPos + 106, this.topPos + 43, Mth.clamp((int) GetSanityIndexProcedure.execute(entity) * 16, 0, 304), 0, 16, 16, 320, 16);

		guiGraphics.blit(new ResourceLocation("caerula_arbor_reunite:textures/screens/oceanize_icon.png"), this.leftPos + 140, this.topPos + 3, Mth.clamp((int) GetOceanizeIndexProcedure.execute(entity) * 24, 0, 72), 0, 24, 20, 96, 20);

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
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_health1"), 45, 71, -10092442, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_health"), 44, 71, -1, false);
		guiGraphics.drawString(this.font,

				GetHealthProcedure.execute(entity), 82, 71, -10092442, false);
		guiGraphics.drawString(this.font,

				GetHealthProcedure.execute(entity), 81, 71, -1, false);
		guiGraphics.drawString(this.font,

				GetLivesProcedure.execute(entity), 18, 33, -16764058, false);
		guiGraphics.drawString(this.font,

				GetLivesProcedure.execute(entity), 17, 33, -1, false);
		guiGraphics.drawString(this.font,

				GetLiveMaxShownProcedure.execute(entity), 36, 33, -16764058, false);
		guiGraphics.drawString(this.font,

				GetLiveMaxShownProcedure.execute(entity), 35, 33, -16724737, false);
		guiGraphics.drawString(this.font,

				GetShieldProcedure.execute(entity), 18, 53, -13421773, false);
		guiGraphics.drawString(this.font,

				GetShieldProcedure.execute(entity), 17, 53, -1, false);
		guiGraphics.drawString(this.font,

				GetLightFloatProcedure.execute(entity), 100, -13, -1, false);
		if (LightIsBrightProcedure.execute(entity))
			guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_lightsablaze"), 100, -29, -3342337, false);
		if (LightIsWavingProcedure.execute(entity))
			guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_lightsflickering"), 100, -29, -3342388, false);
		if (LightIsDimProcedure.execute(entity))
			guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_lightsdim"), 100, -29, -13159, false);
		if (LightIsCeasedProcedure.execute(entity))
			guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_lightstranquil"), 100, -29, -26215, false);
		guiGraphics.drawString(this.font,

				GetSanityProcedure.execute(entity), 124, 50, -16737895, false);
		guiGraphics.drawString(this.font,

				GetSanityProcedure.execute(entity), 123, 50, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_sanity1"), 124, 41, -16737895, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_sanity"), 123, 41, -1, false);
		if (IsDifficultProcedure.execute(world))
			guiGraphics.drawString(this.font,

					GetDifficultyProcedure.execute(world), -4, -13, -10040065, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.label_cognize_symbol_space"), 108, 147, -986119, false);
	}

	@Override
	public void init() {
		super.init();
		button_show_on_hud = new PlainTextButton(this.leftPos + 4, this.topPos + 131, 82, 20, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.button_show_on_hud"), e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new CaerulaRecordGUIButtonMessage(0, x, y, z));
				CaerulaRecordGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}, this.font);
		guistate.put("button:button_show_on_hud", button_show_on_hud);
		this.addRenderableWidget(button_show_on_hud);
		button_show_relic_ptc = new PlainTextButton(this.leftPos + 4, this.topPos + 147, 98, 20, Component.translatable("gui.caerula_arbor_reunite.caerula_record_gui.button_show_relic_ptc"), e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new CaerulaRecordGUIButtonMessage(1, x, y, z));
				CaerulaRecordGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}, this.font);
		guistate.put("button:button_show_relic_ptc", button_show_relic_ptc);
		this.addRenderableWidget(button_show_relic_ptc);
		imagebutton_relic_icon = new ImageButton(this.leftPos + 4, this.topPos + 91, 16, 16, 0, 0, 16, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_relic_icon.png"), 16, 32, e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new CaerulaRecordGUIButtonMessage(2, x, y, z));
				CaerulaRecordGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		});
		guistate.put("button:imagebutton_relic_icon", imagebutton_relic_icon);
		this.addRenderableWidget(imagebutton_relic_icon);
		imagebutton_nurture_gene_set = new ImageButton(this.leftPos + 36, this.topPos + 91, 16, 16, 0, 0, 16, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_nurture_gene_set.png"), 16, 32, e -> {
			if (CanPlayerEvoProcedure.execute(entity)) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new CaerulaRecordGUIButtonMessage(3, x, y, z));
				CaerulaRecordGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
				this.visible = CanPlayerEvoProcedure.execute(entity);
				super.renderWidget(guiGraphics, gx, gy, ticks);
			}
		};
		guistate.put("button:imagebutton_nurture_gene_set", imagebutton_nurture_gene_set);
		this.addRenderableWidget(imagebutton_nurture_gene_set);
		imagebutton_cognize_space = new ImageButton(this.leftPos + 116, this.topPos + 107, 36, 36, 0, 0, 36, new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_cognize_space.png"), 36, 72, e -> {
			if (true) {
				CaerulaArborMod.PACKET_HANDLER.sendToServer(new CaerulaRecordGUIButtonMessage(4, x, y, z));
				CaerulaRecordGUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		});
		guistate.put("button:imagebutton_cognize_space", imagebutton_cognize_space);
		this.addRenderableWidget(imagebutton_cognize_space);
	}
}
