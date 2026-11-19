package net.apocalypse.careunite.core;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

public class SeabornishFont {

    public static final Style SEABORNISH = Style.EMPTY.withFont(new ResourceLocation("caerula_arbor_reunite:seabornish"));

    public static MutableComponent seabornText(String text, @Nullable Style style, Component origin) {
        boolean isShiftKeyDown = false;
        if (Minecraft.getInstance().player != null) {
            isShiftKeyDown = Minecraft.getInstance().player.isShiftKeyDown();
        }
        if (shouldSeabornish()) {
            style = Objects.requireNonNullElse(style, Style.EMPTY);
            if (origin instanceof MutableComponent mc) {
                return Component.literal(text).withStyle(SEABORNISH).withStyle(style.withHoverEvent(hoverStyle(isShiftKeyDown, origin)));
            }
        }
        return Component.literal("").append(origin);
    }

    public static HoverEvent hoverStyle(boolean shift, Component origin){
        if (shift){
            return new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Press shift to show origin text"));
        }
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, origin);
    }

    public static boolean shouldSeabornish(){
        return true;
    }
}
