
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

import net.apocalypse.careunite.procedures.SwitchPlayerEvolutionProcedure;
import net.apocalypse.careunite.procedures.SwitchOceanizeProcedure;
import net.apocalypse.careunite.procedures.SwitchDisoToNeuroProcedure;
import net.apocalypse.careunite.procedures.SwitchDisoToBloodProcedure;
import net.apocalypse.careunite.procedures.SwitchDisoToAttentionProcedure;
import net.apocalypse.careunite.procedures.SwitchDesoToDeformProcedure;
import net.apocalypse.careunite.procedures.SwitchAllDisoProcedure;
import net.apocalypse.careunite.procedures.SetlightAblazeProcedure;
import net.apocalypse.careunite.procedures.SetSurvivorCOntractLayerProcedure;
import net.apocalypse.careunite.procedures.SetShieldProcedure;
import net.apocalypse.careunite.procedures.SetMaxlifeProcedure;
import net.apocalypse.careunite.procedures.SetLiveProcedure;
import net.apocalypse.careunite.procedures.SetLightVawingProcedure;
import net.apocalypse.careunite.procedures.SetLightProcedure;
import net.apocalypse.careunite.procedures.SetLightDimProcedure;
import net.apocalypse.careunite.procedures.SetLightCeasedProcedure;
import net.apocalypse.careunite.procedures.SetHandEngraveLayerProcedure;
import net.apocalypse.careunite.procedures.SetGoldenChalisebalanceProcedure;
import net.apocalypse.careunite.procedures.SetEchoTimeProcedure;
import net.apocalypse.careunite.procedures.SetAllEchoTimeProcedure;
import net.apocalypse.careunite.procedures.RandomRemoveRejCommandProcedure;
import net.apocalypse.careunite.procedures.RandomGiveRejCommandProcedure;
import net.apocalypse.careunite.procedures.EraceDisoNeuroProcedure;
import net.apocalypse.careunite.procedures.EraceDisoDeformProcedure;
import net.apocalypse.careunite.procedures.EraceDisoBloodProcedure;
import net.apocalypse.careunite.procedures.EraceDisoAttentionProcedure;
import net.apocalypse.careunite.procedures.EraceAllDisoProcedure;
import net.apocalypse.careunite.procedures.ClearDisoProcedure;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;

@Mod.EventBusSubscriber
public class SetPlayerLifeCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("caerula_arbor_reunite:player_data").requires(s -> s.hasPermission(2))
				.then(Commands.argument("name", EntityArgument.players()).then(Commands.literal("life_point").then(Commands.argument("life", DoubleArgumentType.doubleArg(0, 16384)).executes(arguments -> {
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

					SetLiveProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("max_life_point").then(Commands.argument("life", DoubleArgumentType.doubleArg(1, 16384)).executes(arguments -> {
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

					SetMaxlifeProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("shield").then(Commands.argument("shield", DoubleArgumentType.doubleArg(0, 32767)).executes(arguments -> {
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

					SetShieldProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("lights").then(Commands.literal("ablaze").executes(arguments -> {
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

					SetlightAblazeProcedure.execute(arguments);
					return 0;
				})).then(Commands.literal("flicker").executes(arguments -> {
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

					SetLightVawingProcedure.execute(arguments);
					return 0;
				})).then(Commands.literal("dim").executes(arguments -> {
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

					SetLightDimProcedure.execute(arguments);
					return 0;
				})).then(Commands.literal("tranquil").executes(arguments -> {
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

					SetLightCeasedProcedure.execute(arguments);
					return 0;
				})).then(Commands.argument("light", DoubleArgumentType.doubleArg(0, 100)).executes(arguments -> {
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

					SetLightProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("oceanization").then(Commands.argument("state", DoubleArgumentType.doubleArg(0, 3)).executes(arguments -> {
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

					SwitchOceanizeProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("rejection").then(Commands.literal("clear").executes(arguments -> {
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

					ClearDisoProcedure.execute(arguments);
					return 0;
				})).then(Commands.literal("random").then(Commands.literal("give").executes(arguments -> {
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

					RandomGiveRejCommandProcedure.execute(arguments);
					return 0;
				})).then(Commands.literal("remove").executes(arguments -> {
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

					RandomRemoveRejCommandProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("disconcentration").then(Commands.literal("on").executes(arguments -> {
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

					SwitchDisoToAttentionProcedure.execute(arguments);
					return 0;
				})).then(Commands.literal("off").executes(arguments -> {
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

					EraceDisoAttentionProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("haemophilia").then(Commands.literal("on").executes(arguments -> {
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

					SwitchDisoToBloodProcedure.execute(arguments);
					return 0;
				})).then(Commands.literal("off").executes(arguments -> {
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

					EraceDisoBloodProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("neurodegression").then(Commands.literal("on").executes(arguments -> {
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

					SwitchDisoToNeuroProcedure.execute(arguments);
					return 0;
				})).then(Commands.literal("off").executes(arguments -> {
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

					EraceDisoNeuroProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("deformity").then(Commands.literal("on").executes(arguments -> {
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

					SwitchDesoToDeformProcedure.execute(arguments);
					return 0;
				})).then(Commands.literal("off").executes(arguments -> {
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

					EraceDisoDeformProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("all").then(Commands.literal("on").executes(arguments -> {
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

					SwitchAllDisoProcedure.execute(arguments);
					return 0;
				})).then(Commands.literal("off").executes(arguments -> {
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

					EraceAllDisoProcedure.execute(arguments);
					return 0;
				})))).then(Commands.literal("collectibles").then(Commands.literal("HandOfBranding").then(Commands.argument("lvl", DoubleArgumentType.doubleArg(0, 127)).executes(arguments -> {
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

					SetHandEngraveLayerProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("SurvivorContract").then(Commands.argument("lvl", DoubleArgumentType.doubleArg(0, 127)).executes(arguments -> {
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

					SetSurvivorCOntractLayerProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("GoldenChalise").then(Commands.argument("balance", DoubleArgumentType.doubleArg(0, 131072)).executes(arguments -> {
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

					SetGoldenChalisebalanceProcedure.execute(arguments);
					return 0;
				})))).then(Commands.literal("evolution").then(Commands.argument("unlock", BoolArgumentType.bool()).executes(arguments -> {
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

					SwitchPlayerEvolutionProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("call_of_we_many").then(Commands.argument("index", DoubleArgumentType.doubleArg(0, 7)).then(Commands.argument("duration", DoubleArgumentType.doubleArg(-1, 4800)).executes(arguments -> {
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

					SetEchoTimeProcedure.execute(arguments);
					return 0;
				}))).then(Commands.literal("all").then(Commands.argument("duration", DoubleArgumentType.doubleArg(-1, 4800)).executes(arguments -> {
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

					SetAllEchoTimeProcedure.execute(arguments);
					return 0;
				}))))));
	}
}
