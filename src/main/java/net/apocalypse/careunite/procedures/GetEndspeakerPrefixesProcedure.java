package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class GetEndspeakerPrefixesProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		String prefixes = "";
		double index = 0;
		double count = 0;
		{
			String[] _array = (Component.translatable("entity.caerula_arbor_reunite.endspeaker.prefix").getString()).split((","));
			if (_array.length != 0) {
				for (String stringiterator : _array) {
					if (InquirybilityProcedure.execute(world, index)) {
						prefixes = prefixes + "" + stringiterator;
						count = count + 1;
					}
					index = index + 1;
				}
			}
		}
		if (count > 5) {
			prefixes = Component.translatable("entity.caerula_arbor_reunite.endspeaker.prefix.all").getString();
		}
		if (count >= 4) {
			prefixes = "\u00A7b" + prefixes;
		} else if (count >= 2) {
			prefixes = "\u00A7e" + prefixes;
		}
		if (!(prefixes).isEmpty()) {
			entity.setCustomName(Component.literal((prefixes + "" + entity.getDisplayName().getString())));
		}
	}
}
