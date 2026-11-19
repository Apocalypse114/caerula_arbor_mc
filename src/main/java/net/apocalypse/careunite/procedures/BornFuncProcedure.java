package net.apocalypse.careunite.procedures;

import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.CaerulaArborMod;

public class BornFuncProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, boolean clone) {
		if (entity == null)
			return;
		double stra = 0;
		double health_index = 0;
		double attack_index = 0;
		double armor_index = 0;
		double n = 0;
		double coef = 0;
		double coef_cur = 0;
		double mult = 0;
		double percentage = 0;
		String prefix = "";
		String num = "";
		String extendN18 = "";
		boolean isSeaborn = false;
		if (entity.getPersistentData().getBoolean("caerulaEvolved")) {
			return;
		}
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:tombstone")))) {
			return;
		}
		entity.getPersistentData().putBoolean("caerulaEvolved", true);
		extendN18 = CaerulaConfigsConfiguration.EXTEND_N18.get();
		health_index = 1;
		attack_index = 1;
		armor_index = 1;
		isSeaborn = IsSeabornProcedure.execute(entity);
		if (isSeaborn || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:golems")))) {
			if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ARMOR))
				_livingEntity6.getAttribute(Attributes.ARMOR)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity5.getAttribute(Attributes.ARMOR).getBaseValue() : 0)
								+ 2 * CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting));
			if (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
				_livingEntity8.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
						.setBaseValue(((entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
								? _livingEntity7.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
								: 0) + CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting));
			if (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
				_livingEntity10.getAttribute(Attributes.ARMOR_TOUGHNESS)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS) ? _livingEntity9.getAttribute(Attributes.ARMOR_TOUGHNESS).getBaseValue() : 0)
								+ 2 * CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting));
			health_index = 1 + 0.3 * CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting;
			attack_index = 1 + 0.25 * CaerulaArborModVariables.MapVariables.get(world).strategy_grow;
			armor_index = 1;
			health_index = Math.max((double) CaerulaConfigsConfiguration.HEALTH_MULT.get(), 0.1) * health_index;
			attack_index = Math.max((double) CaerulaConfigsConfiguration.ATTACK_MULT.get(), 0.1) * attack_index;
			armor_index = Math.max((double) CaerulaConfigsConfiguration.ARMOR_MULT.get(), 0.1) * armor_index;
		}
		if (isSeaborn) {
			if (!entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:marinemobs")))) {
				if (entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()))
					_livingEntity17.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(
							(10 * (entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()) ? _livingEntity16.getAttribute(ForgeMod.SWIM_SPEED.get()).getBaseValue() : 0)));
			}
			if (clone) {
				CaerulaArborMod.queueServerWork(10, () -> {
					BornAndCloneProcedure.execute(world, x, y, z, entity);
				});
			}
			OnSublimationSpawnProcedure.execute(world, entity);
		}
		if (!entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bypasses_surging_waves")))
				&& (isSeaborn || !(extendN18).equals("off") && ((extendN18).equals("on") || (extendN18).equals("animal_only") && entity instanceof Animal || (extendN18).equals("exclude_monster") && !(entity instanceof Monster)
						|| (extendN18).equals("monster_only") && entity instanceof Monster || (extendN18).equals("exclude_animal") && !(entity instanceof Animal)))) {
			n = GetN18BonusProcedure.execute(world, Math.min((world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.SURGING_WAVES)), 18));
			if (n > 1) {
				health_index = n * health_index;
				attack_index = n * attack_index;
				armor_index = n * armor_index;
			}
		}
		percentage = (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
		if (health_index > 1) {
			if (entity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				_livingEntity28.getAttribute(Attributes.MAX_HEALTH)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity27.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * health_index));
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((entity instanceof LivingEntity _livingEntity29 && _livingEntity29.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity29.getAttribute(Attributes.MAX_HEALTH).getValue() : 0) * percentage));
		}
		if (armor_index > 1) {
			if (entity instanceof LivingEntity _livingEntity32 && _livingEntity32.getAttributes().hasAttribute(Attributes.ARMOR))
				_livingEntity32.getAttribute(Attributes.ARMOR)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity31.getAttribute(Attributes.ARMOR).getBaseValue() : 0) * armor_index));
			if (entity instanceof LivingEntity _livingEntity34 && _livingEntity34.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
				_livingEntity34.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
						.setBaseValue(((entity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
								? _livingEntity33.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
								: 0) * armor_index));
			if (entity instanceof LivingEntity _livingEntity36 && _livingEntity36.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
				_livingEntity36.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(
						((entity instanceof LivingEntity _livingEntity35 && _livingEntity35.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS) ? _livingEntity35.getAttribute(Attributes.ARMOR_TOUGHNESS).getBaseValue() : 0) * armor_index));
		}
		if (attack_index > 1) {
			if (entity instanceof LivingEntity _livingEntity38 && _livingEntity38.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity38.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
						((entity instanceof LivingEntity _livingEntity37 && _livingEntity37.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity37.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * attack_index));
		}
	}
}
