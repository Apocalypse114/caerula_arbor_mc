package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class InquiryAllAbilitiesProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		String res_line_1 = "";
		String res_line_2 = "";
		String code = "";
		String res_line_3 = "";
		code = "" + Math.round(CaerulaArborModVariables.MapVariables.get(world).endspeaker_abolities);
		res_line_1 = Component.translatable("command.endspeaker.inquiry.0").getString();
		res_line_1 = res_line_1.replace("{code}", code);
		res_line_2 = Component.translatable("command.endspeaker.inquiry.1").getString();
		for (int index0 = 0; index0 < 3; index0++) {
			res_line_2 = res_line_2.replace("{a" + (index0 + 1) + "}", "" + InquirybilityProcedure.execute(world, index0));
		}
		res_line_3 = Component.translatable("command.endspeaker.inquiry.2").getString();
		for (int index1 = 0; index1 < 3; index1++) {
			res_line_3 = res_line_3.replace("{a" + (index1 + 4) + "}", "" + InquirybilityProcedure.execute(world, index1 + 3));
		}
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal(res_line_1), false);
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal(res_line_2), false);
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal(res_line_3), false);
	}
}
