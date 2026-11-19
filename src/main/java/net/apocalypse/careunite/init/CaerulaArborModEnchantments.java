
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.apocalypse.careunite.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.enchantment.Enchantment;

import net.apocalypse.careunite.enchantment.SynesthesiaEnchantment;
import net.apocalypse.careunite.enchantment.SanityReaperEnchantment;
import net.apocalypse.careunite.enchantment.SanityInjuryCurseEnchantment;
import net.apocalypse.careunite.enchantment.SanityDefendEnchantment;
import net.apocalypse.careunite.enchantment.RejectionCurseEnchantment;
import net.apocalypse.careunite.enchantment.ReflectionEnchantment;
import net.apocalypse.careunite.enchantment.OceanosprKillerEnchantment;
import net.apocalypse.careunite.enchantment.NetherseaWalkerEnchantment;
import net.apocalypse.careunite.enchantment.MuteAttackEnchantment;
import net.apocalypse.careunite.enchantment.MetabolismEnchantment;
import net.apocalypse.careunite.enchantment.MagicToleranceEnchantment;
import net.apocalypse.careunite.enchantment.HazardProtectionEnchantment;
import net.apocalypse.careunite.enchantment.FlexibilityEnchantment;
import net.apocalypse.careunite.CaerulaArborMod;

public class CaerulaArborModEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, CaerulaArborMod.MODID);
	public static final RegistryObject<Enchantment> OCEANOSPR_KILLER = REGISTRY.register("oceanospr_killer", () -> new OceanosprKillerEnchantment());
	public static final RegistryObject<Enchantment> SANITY_REAPER = REGISTRY.register("sanity_reaper", () -> new SanityReaperEnchantment());
	public static final RegistryObject<Enchantment> SANITY_DEFEND = REGISTRY.register("sanity_defend", () -> new SanityDefendEnchantment());
	public static final RegistryObject<Enchantment> REFLECTION = REGISTRY.register("reflection", () -> new ReflectionEnchantment());
	public static final RegistryObject<Enchantment> SYNESTHESIA = REGISTRY.register("synesthesia", () -> new SynesthesiaEnchantment());
	public static final RegistryObject<Enchantment> METABOLISM = REGISTRY.register("metabolism", () -> new MetabolismEnchantment());
	public static final RegistryObject<Enchantment> MUTE_ATTACK = REGISTRY.register("mute_attack", () -> new MuteAttackEnchantment());
	public static final RegistryObject<Enchantment> NETHERSEA_WALKER = REGISTRY.register("nethersea_walker", () -> new NetherseaWalkerEnchantment());
	public static final RegistryObject<Enchantment> FLEXIBILITY = REGISTRY.register("flexibility", () -> new FlexibilityEnchantment());
	public static final RegistryObject<Enchantment> MAGIC_TOLERANCE = REGISTRY.register("magic_tolerance", () -> new MagicToleranceEnchantment());
	public static final RegistryObject<Enchantment> HAZARD_PROTECTION = REGISTRY.register("hazard_protection", () -> new HazardProtectionEnchantment());
	public static final RegistryObject<Enchantment> SANITY_INJURY_CURSE = REGISTRY.register("sanity_injury_curse", () -> new SanityInjuryCurseEnchantment());
	public static final RegistryObject<Enchantment> REJECTION_CURSE = REGISTRY.register("rejection_curse", () -> new RejectionCurseEnchantment());
}
