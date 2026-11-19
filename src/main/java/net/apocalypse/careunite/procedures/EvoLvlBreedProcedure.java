package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class EvoLvlBreedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, CommandContext<CommandSourceStack> arguments) {
		String info = "";
		CaerulaArborModVariables.MapVariables.get(world).strategy_breed = Math.round(DoubleArgumentType.getDouble(arguments, "lvl"));
		CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		if (DoubleArgumentType.getDouble(arguments, "lvl") >= 3) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:breed2")), SoundSource.NEUTRAL, 2, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:breed2")), SoundSource.NEUTRAL, 2, 1, false);
				}
			}
		} else if (DoubleArgumentType.getDouble(arguments, "lvl") > 0) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:breed1")), SoundSource.NEUTRAL, 2, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:breed1")), SoundSource.NEUTRAL, 2, 1, false);
				}
			}
		}
		if (DoubleArgumentType.getDouble(arguments, "lvl") < 4) {
			CaerulaArborModVariables.MapVariables.get(world).strategy_silence = 0;
			CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		}
		info = Component.translatable("command.evolution.breed").getString();
		info = info.replace("<num>", "" + Math.round(DoubleArgumentType.getDouble(arguments, "lvl")));
		{
			final String _success = info;
			final boolean _informAdmins = true;
			arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
		}
	}
}
