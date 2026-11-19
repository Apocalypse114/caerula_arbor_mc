package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import javax.annotation.Nullable;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

@Mod.EventBusSubscriber
public class MagicResisFuncProcedure {
	@SubscribeEvent
(priority = EventPriority.LOW)
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity(), event.getAmount());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity, double amount) {
		execute(null, damagesource, entity, amount);
	}

	public static TagKey<DamageType> B_PROTECTION = TagKey.create(Registries.DAMAGE_TYPE,
						new ResourceLocation("caerula_arbor_reunite:bypass_protection"));
	public static TagKey<DamageType> IS_MAGIC = TagKey.create(Registries.DAMAGE_TYPE, 
						new ResourceLocation("caerula_arbor_reunite:is_magic"));
	public static TagKey<DamageType> B_DEFENSE = TagKey.create(Registries.DAMAGE_TYPE, 
						new ResourceLocation("caerula_arbor_reunite:bypass_defense"));

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity, double amount) {
		if (damagesource == null || entity == null)
			return;
		if (damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return;
		}
		if (damagesource.is(DamageTypeTags.BYPASSES_EFFECTS)) {
			return;
		}
		if (damagesource.is(B_PROTECTION)) {
			return;
		}
		if (damagesource.is(IS_MAGIC) && !damagesource.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
			double mgc_resis = entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
					? _livingEntity5.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getValue()
					: 0;
			if (mgc_resis > 0) {
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) Math.max(amount * 0.01 * (100 - mgc_resis), amount * 0.05));
				}
			}
		} else {
			double def = entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
					? _livingEntity7.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getValue()
					: 0;
			if (def > 0 && !damagesource.is(B_DEFENSE)) {
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) Math.max(amount - def, amount * 0.05));
				}
			}
		}
	}
}
