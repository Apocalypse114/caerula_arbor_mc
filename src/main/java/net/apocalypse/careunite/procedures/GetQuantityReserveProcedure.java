package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class GetQuantityReserveProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return Component.translatable("p_evo.caerula_arbor_reunite.quantity_reserve").getString() + ""
				+ (int) ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).reserve_quantity);
	}
}
