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

public class EvoAddMigraProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, CommandContext<CommandSourceStack> arguments) {
		String info = "";
		double doneLvl = 0;
		doneLvl = Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_migration + 1, 4);
		CaerulaArborModVariables.MapVariables.get(world).strategy_migration = doneLvl;
		CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		if (doneLvl >= 3) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:migration2")), SoundSource.NEUTRAL, 4, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:migration2")), SoundSource.NEUTRAL, 4, 1, false);
				}
			}
		} else if (doneLvl > 0) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:migration1")), SoundSource.NEUTRAL, 4, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:migration1")), SoundSource.NEUTRAL, 4, 1, false);
				}
			}
		}
		if (doneLvl < 4) {
			CaerulaArborModVariables.MapVariables.get(world).strategy_silence = 0;
			CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		}
		info = Component.translatable("command.evolution.migration").getString();
		info = info.replace("<num>", "" + Math.round(doneLvl));
		{
			final String _success = info;
			final boolean _informAdmins = true;
			arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
		}
	}
}
