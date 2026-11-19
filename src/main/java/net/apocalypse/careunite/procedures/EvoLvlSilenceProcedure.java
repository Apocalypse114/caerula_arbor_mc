package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class EvoLvlSilenceProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		String info = "";
		double ss = 0;
		if (IfCanSilenceProcedure.execute(world)) {
			ss = Math.round(DoubleArgumentType.getDouble(arguments, "lvl"));
			CaerulaArborModVariables.MapVariables.get(world).strategy_silence = ss;
			CaerulaArborModVariables.MapVariables.get(world).syncData(world);
			if (ss == 1) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence1")), SoundSource.NEUTRAL, 4, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence1")), SoundSource.NEUTRAL, 4, 1, false);
					}
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.language_key.description_6").getString())), true);
			} else if (ss == 2) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence2")), SoundSource.NEUTRAL, 4, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence2")), SoundSource.NEUTRAL, 4, 1, false);
					}
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.language_key.description_7").getString())), true);
			} else if (ss == 3) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence3")), SoundSource.NEUTRAL, 6, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence3")), SoundSource.NEUTRAL, 6, 1, false);
					}
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.language_key.description_8").getString())), true);
			} else if (ss == 4) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence4")), SoundSource.NEUTRAL, 6, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence4")), SoundSource.NEUTRAL, 6, 1, false);
					}
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.language_key.description_9").getString())), true);
			}
			info = Component.translatable("command.evolution.silence").getString();
			info = info.replace("<num>", "" + Math.round(DoubleArgumentType.getDouble(arguments, "lvl")));
			{
				final String _success = info;
				final boolean _informAdmins = true;
				arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
			}
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.language_key.description_5").getString())), true);
		}
	}
}
