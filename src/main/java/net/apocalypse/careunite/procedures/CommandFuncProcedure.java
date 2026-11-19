package net.apocalypse.careunite.procedures;

import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.CommandEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;

import javax.annotation.Nullable;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.IntegerArgumentType;

@Mod.EventBusSubscriber
public class CommandFuncProcedure {
	@SubscribeEvent
	public static void onCommand(CommandEvent event) {
		Entity entity = event.getParseResults().getContext().getSource().getEntity();
		if (entity != null) {
			execute(event, entity.level(), event.getParseResults().getContext().build(event.getParseResults().getReader().getString()), entity, event.getParseResults().getReader().getString());
		}
	}

	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity, String command) {
		execute(null, world, arguments, entity, command);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity, String command) {
		if (entity == null || command == null)
			return;
		String notice = "";
		double lvl = 0;
		double nowLvl = 0;
		if (command.contains("surgingWaves")) {
			if ((!world.isClientSide() && world.getServer() != null) ? ((ServerLifecycleHooks.getCurrentServer().getDefaultGameType()) == (GameType.SURVIVAL)) : false) {
				lvl = (double) IntegerArgumentType.getInteger(arguments, "value");
				nowLvl = (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.SURGING_WAVES));
				if (lvl > nowLvl) {
					if (nowLvl < 12 && lvl >= 12) {
						notice = Component.translatable("gameplay.caerula_arbor_reunite.n_warn_12").getString();
					} else if (nowLvl < 6 && lvl >= 6) {
						notice = Component.translatable("gameplay.caerula_arbor_reunite.n_warn_6").getString();
					}
					if (!(notice).isEmpty()) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(notice), false);
					}
				}
			}
		}
	}
}
