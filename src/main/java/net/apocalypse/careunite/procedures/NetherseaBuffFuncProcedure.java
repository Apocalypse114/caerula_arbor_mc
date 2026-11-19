package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class NetherseaBuffFuncProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		ItemStack boots = ItemStack.EMPTY;
		double lvl = 0;
		if (entity.isAlive()) {
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 2) {
					LivingHeal1Procedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.0025);
				} else if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting >= 3) {
					LivingHeal1Procedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.001);
				}
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.RUNNING_ON_TRAIL.get(), 5, 0, false, false));
			} else if (entity instanceof Player) {
				if ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization >= 3) {
					if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence >= 2) {
						LivingHeal1Procedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.0025);
					} else if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting >= 3) {
						LivingHeal1Procedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.001);
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.RUNNING_ON_TRAIL.get(), 5, 0, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 5, 0, false, false));
				}
			}
		}
	}
}
