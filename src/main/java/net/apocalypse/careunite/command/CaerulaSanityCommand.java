
package net.apocalypse.careunite.command;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.common.util.FakePlayerFactory;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import net.apocalypse.careunite.procedures.CommandSetSanityProcedure;
import net.apocalypse.careunite.procedures.CommandHurtSanityProcedure;
import net.apocalypse.careunite.procedures.CommandHealSanityProcedure;
import net.apocalypse.careunite.procedures.CommandCheckEntitySanityProcedure;

import com.mojang.brigadier.arguments.DoubleArgumentType;

@Mod.EventBusSubscriber
public class CaerulaSanityCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("caerula_arbor_reunite:sanity").requires(s -> s.hasPermission(2)).then(Commands.literal("check").then(Commands.argument("name", EntityArgument.entity()).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			double x = arguments.getSource().getPosition().x();
			double y = arguments.getSource().getPosition().y();
			double z = arguments.getSource().getPosition().z();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			Direction direction = Direction.DOWN;
			if (entity != null)
				direction = entity.getDirection();

			CommandCheckEntitySanityProcedure.execute(arguments);
			return 0;
		}))).then(Commands.literal("set").then(Commands.argument("name", EntityArgument.entities()).then(Commands.argument("amount", DoubleArgumentType.doubleArg(-1, 1000)).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			double x = arguments.getSource().getPosition().x();
			double y = arguments.getSource().getPosition().y();
			double z = arguments.getSource().getPosition().z();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			Direction direction = Direction.DOWN;
			if (entity != null)
				direction = entity.getDirection();

			CommandSetSanityProcedure.execute(arguments);
			return 0;
		})))).then(Commands.literal("hurt").then(Commands.argument("name", EntityArgument.entities()).then(Commands.argument("amount", DoubleArgumentType.doubleArg(0, 2147483647)).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			double x = arguments.getSource().getPosition().x();
			double y = arguments.getSource().getPosition().y();
			double z = arguments.getSource().getPosition().z();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			Direction direction = Direction.DOWN;
			if (entity != null)
				direction = entity.getDirection();

			CommandHurtSanityProcedure.execute(arguments);
			return 0;
		})))).then(Commands.literal("heal").then(Commands.argument("name", EntityArgument.entities()).then(Commands.argument("amount", DoubleArgumentType.doubleArg(0, 2147483647)).executes(arguments -> {
			Level world = arguments.getSource().getUnsidedLevel();
			double x = arguments.getSource().getPosition().x();
			double y = arguments.getSource().getPosition().y();
			double z = arguments.getSource().getPosition().z();
			Entity entity = arguments.getSource().getEntity();
			if (entity == null && world instanceof ServerLevel _servLevel)
				entity = FakePlayerFactory.getMinecraft(_servLevel);
			Direction direction = Direction.DOWN;
			if (entity != null)
				direction = entity.getDirection();

			CommandHealSanityProcedure.execute(arguments);
			return 0;
		})))));
	}
}
