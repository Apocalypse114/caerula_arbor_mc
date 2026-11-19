
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
import net.minecraft.commands.Commands;

import net.apocalypse.careunite.procedures.SwitchEndspeakerSummonProcedure;
import net.apocalypse.careunite.procedures.RevokeOneAbilityProcedure;
import net.apocalypse.careunite.procedures.RevokeAllAbilitiesProcedure;
import net.apocalypse.careunite.procedures.InquiryAllAbilitiesProcedure;
import net.apocalypse.careunite.procedures.BestowOneAbilityProcedure;
import net.apocalypse.careunite.procedures.BestowAllAbilitiesProcedure;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;

@Mod.EventBusSubscriber
public class CaerulaArborEndspeakerCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("caerula_arbor_reunite:endspeaker").requires(s -> s.hasPermission(2)).then(Commands.literal("inquiry").executes(arguments -> {
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

			InquiryAllAbilitiesProcedure.execute(world, entity);
			return 0;
		})).then(Commands.literal("bestow").then(Commands.literal("all").executes(arguments -> {
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

			BestowAllAbilitiesProcedure.execute(world, arguments);
			return 0;
		})).then(Commands.argument("index", DoubleArgumentType.doubleArg(1, 6)).executes(arguments -> {
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

			BestowOneAbilityProcedure.execute(world, arguments);
			return 0;
		}))).then(Commands.literal("revoke").then(Commands.literal("all").executes(arguments -> {
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

			RevokeAllAbilitiesProcedure.execute(world, arguments);
			return 0;
		})).then(Commands.argument("index", DoubleArgumentType.doubleArg(1, 6)).executes(arguments -> {
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

			RevokeOneAbilityProcedure.execute(world, arguments);
			return 0;
		}))).then(Commands.literal("can_summon").then(Commands.argument("can", BoolArgumentType.bool()).executes(arguments -> {
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

			SwitchEndspeakerSummonProcedure.execute(world, arguments);
			return 0;
		}))));
	}
}
