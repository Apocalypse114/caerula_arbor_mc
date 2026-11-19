package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class CheckDefAndMagicProcedure {
	public static void execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double def = 0;
		double mgc = 0;
		double snt = 0;
		def = Math.round(Math.pow(10, 2) * (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
				? _livingEntity0.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getValue()
				: 0)) / Math.pow(10, 2);
		mgc = Math.round(Math.pow(10, 2) * (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
				? _livingEntity2.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getValue()
				: 0)) / Math.pow(10, 2);
		snt = Math.round(Math.pow(10, 2) * (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get())
				? _livingEntity4.getAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()).getValue()
				: 0)) / Math.pow(10, 2);
		if (sourceentity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal(("Defense: " + def)), false);
		if (sourceentity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal(("Magic Resiatance: " + mgc)), false);
		if (sourceentity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal(("Sanity Resistance: " + snt)), false);
	}
}
