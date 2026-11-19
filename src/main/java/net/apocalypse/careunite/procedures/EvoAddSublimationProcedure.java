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

public class EvoAddSublimationProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, CommandContext<CommandSourceStack> arguments) {
		String info = "";
		double doneLvl = 0;
		if (IfCanSublimeProcedure.execute(world)) {
			doneLvl = Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation + 1, 4);
			CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation = doneLvl;
			CaerulaArborModVariables.MapVariables.get(world).syncData(world);
			if (doneLvl >= 3) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sublimation_2")), SoundSource.NEUTRAL, 4, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sublimation_2")), SoundSource.NEUTRAL, 4, 1, false);
					}
				}
			} else if (doneLvl > 0) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sublimation_1")), SoundSource.NEUTRAL, 4, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sublimation_1")), SoundSource.NEUTRAL, 4, 1, false);
					}
				}
			}
			info = Component.translatable("command.evolution.sublimation").getString();
			info = info.replace("<num>", "" + Math.round(doneLvl));
			{
				final String _success = info;
				final boolean _informAdmins = true;
				arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
			}
		} else {
			info = Component.translatable("command.evolution.sublimation.fail").getString();
			{
				final String _success = info;
				final boolean _informAdmins = true;
				arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
			}
		}
	}
}
