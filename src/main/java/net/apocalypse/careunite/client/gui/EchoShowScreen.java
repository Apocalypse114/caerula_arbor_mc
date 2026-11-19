package net.apocalypse.careunite.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import net.apocalypse.careunite.world.inventory.EchoShowMenu;

import net.apocalypse.careunite.procedures.GetEchoExploreProcedure;
import net.apocalypse.careunite.procedures.GetEchoFightProcedure;
import net.apocalypse.careunite.procedures.GetEchoRemouldProcedure;
import net.apocalypse.careunite.procedures.GetEchoVissiProcedure;
import net.apocalypse.careunite.procedures.GetEchoFeedProcedure;
import net.apocalypse.careunite.procedures.GetEchoCrowdProcedure;
import net.apocalypse.careunite.procedures.GetEchoSpindlingProcedure;
import net.apocalypse.careunite.procedures.GetEchoAdaptProcedure;

import net.apocalypse.careunite.network.EchoShowButtonMessage;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class EchoShowScreen extends AbstractContainerScreen<EchoShowMenu> {
	private final static HashMap<String, Object> guistate = EchoShowMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	ImageButton imagebutton_button_16_48;
	ImageButton imagebutton_button_16_48_linked;
	ImageButton imagebutton_button_16_481;

	public EchoShowScreen(EchoShowMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 144;
	}

	private static final ResourceLocation texture = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_show.png");
	private static final ResourceLocation FRAME = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_frame.png");
	private static final ResourceLocation PROGRESS = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_progress.png");

	private static final ResourceLocation EXPLORE = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_explore.png");
	private static final ResourceLocation FIGHT = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_fight.png");
	private static final ResourceLocation REMOULD = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_remould.png");
	private static final ResourceLocation VISSIS = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_vissisitude.png");
	private static final ResourceLocation FEED = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_feed.png");
	private static final ResourceLocation CROWD = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_crowd.png");
	private static final ResourceLocation SPINDLING = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_spindling.png");
	private static final ResourceLocation ADAPT = new ResourceLocation("caerula_arbor_reunite:textures/screens/echo_adapt.png");
	private static final ResourceLocation BUTTON = new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_button_16_48.png");
	private static final ResourceLocation BUTTON_1 = new ResourceLocation("caerula_arbor_reunite:textures/screens/atlas/imagebutton_button_16_48_linked.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		
		double exploreP = GetEchoExploreProcedure.execute(this.entity);
		double fightP = GetEchoFightProcedure.execute(this.entity);
		double remouldP = GetEchoRemouldProcedure.execute(this.entity);
		double vissisP = GetEchoVissiProcedure.execute(this.entity);
		double feedP = GetEchoFeedProcedure.execute(this.entity);
		double crowdP = GetEchoCrowdProcedure.execute(this.entity);
		double spindlingP = GetEchoSpindlingProcedure.execute(this.entity);
		double adaptP = GetEchoAdaptProcedure.execute(this.entity);

		int renderingEcho = boolToInt(exploreP) + boolToInt(fightP) + boolToInt(remouldP) + boolToInt(vissisP)
			+ boolToInt(feedP) + boolToInt(crowdP) + boolToInt(spindlingP) + boolToInt(adaptP);
		if(renderingEcho <= 0) return;
		int gap = 132 / renderingEcho;
		int dy = 0;
		
		if (renderEcho(guiGraphics, EXPLORE, "explore", dy, mouseX, mouseY, exploreP)) dy += gap;
		if (renderEcho(guiGraphics, FIGHT, "fight", dy, mouseX, mouseY, fightP)) dy += gap;
		if (renderEcho(guiGraphics, REMOULD, "remould", dy, mouseX, mouseY, remouldP)) dy += gap;
		if (renderEcho(guiGraphics, VISSIS, "vissis", dy, mouseX, mouseY, vissisP)) dy += gap;
		if (renderEcho(guiGraphics, FEED, "feed", dy, mouseX, mouseY, feedP)) dy += gap;
		if (renderEcho(guiGraphics, CROWD, "crowd", dy, mouseX, mouseY, crowdP)) dy += gap;
		if (renderEcho(guiGraphics, SPINDLING, "spindling", dy, mouseX, mouseY, spindlingP)) dy += gap;
		renderEcho(guiGraphics, ADAPT, "adapt", dy, mouseX, mouseY, adaptP);
	}

	private boolean renderEcho(GuiGraphics guiGraphics, ResourceLocation res, String name, int dy, int mouseX, int mouseY, double progress){
		if(progress > -0.0001 && progress <= 0) return false;
		guiGraphics.blit(res, this.leftPos + 8, this.topPos + 4 + dy, 0, 0, 18, 18, 18, 18);
		int p = progress < 0 ? 128 : Math.round((float)Mth.clamp(progress * 128, 0, 128));
		guiGraphics.blit(FRAME, this.leftPos + 30, this.topPos + 4 + dy, 0, 0, 130, 18, 130, 18);
		guiGraphics.blit(PROGRESS, this.leftPos + 31, this.topPos + 5 + dy, 0, 0, p, 16, 128, 16);
		guiGraphics.drawString(this.font, getEchoTitle(name), this.leftPos+32, this.topPos+5+dy, -1, false);
		guiGraphics.drawString(this.font, getEchoTooltip(name), this.leftPos+32, this.topPos+13+dy, -13108, false);
		String timeLeft = progress < 0 ? "∞" : ""+(int)(progress * 2400)+"s";
		guiGraphics.drawString(this.font, timeLeft, this.leftPos+128, this.topPos+5+dy, -1, false);
		if (mouseX > leftPos + 8 && mouseX < leftPos + 26 && mouseY > topPos + 4 + dy && mouseY < topPos + 22 + dy)
			guiGraphics.renderTooltip(font, getEchoLable(name), mouseX, mouseY);
		return true;
	}
	private static Component getEchoTooltip(String name){
		return Component.translatable("gui.caerula_arbor_reunite.echo_show.tooltip_"+name+"_desc");
	}
	private static Component getEchoTitle(String name){
		return Component.translatable("gui.caerula_arbor_reunite.echo_show.label_echo_"+name);
	}
	private static Component getEchoLable(String name){
		return Component.translatable("gui.caerula_arbor_reunite.echo_show.label_"+name+"_desc");
	}
	private static int boolToInt(double p){
		return (p > -0.0001 && p <= 0) ? 0 : 1;
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
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.echo_show.label_cognitive_symbol_space"),
			1, -9, -1, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.echo_show.label_rejection"),
			-44, 6, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.echo_show.label_call_of_we_many"),
			-44, 26, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.caerula_arbor_reunite.echo_show.label_enlightment"),
			-44, 46, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		imagebutton_button_16_48 = new ImageButton(this.leftPos + -47, this.topPos + 2, 50, 18, 0, 0, 18, BUTTON, 50, 36, e -> {
			CaerulaArborMod.PACKET_HANDLER.sendToServer(new EchoShowButtonMessage(0, x, y, z));
			EchoShowButtonMessage.handleButtonAction(entity, 0, x, y, z);
		});
		guistate.put("button:imagebutton_button_16_48", imagebutton_button_16_48);
		this.addRenderableWidget(imagebutton_button_16_48);
		imagebutton_button_16_48_linked = new ImageButton(this.leftPos + -47, this.topPos + 22, 50, 18, 0, 0, 18, BUTTON_1, 50, 36, e -> {
		});
		guistate.put("button:imagebutton_button_16_48_linked", imagebutton_button_16_48_linked);
		this.addRenderableWidget(imagebutton_button_16_48_linked);
		imagebutton_button_16_481 = new ImageButton(this.leftPos + -47, this.topPos + 42, 50, 18, 0, 0, 18, BUTTON, 50, 36, e -> {
			CaerulaArborMod.PACKET_HANDLER.sendToServer(new EchoShowButtonMessage(2, x, y, z));
			EchoShowButtonMessage.handleButtonAction(entity, 2, x, y, z);
		});
		guistate.put("button:imagebutton_button_16_481", imagebutton_button_16_481);
		this.addRenderableWidget(imagebutton_button_16_481);
	}
}
