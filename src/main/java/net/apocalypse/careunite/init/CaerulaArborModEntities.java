
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.apocalypse.careunite.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.YirahelBeaconEntity;
import net.apocalypse.careunite.entity.XantisEntity;
import net.apocalypse.careunite.entity.WitherShootPreEntity;
import net.apocalypse.careunite.entity.WarriorPriestEntity;
import net.apocalypse.careunite.entity.VoyageLampEntity;
import net.apocalypse.careunite.entity.UsherVaseEntity;
import net.apocalypse.careunite.entity.UnquenchableFireEntity;
import net.apocalypse.careunite.entity.UmbrellaAbyssalEntity;
import net.apocalypse.careunite.entity.UlpiansEntity;
import net.apocalypse.careunite.entity.TyphoeusEntity;
import net.apocalypse.careunite.entity.TypheousSignEntity;
import net.apocalypse.careunite.entity.TuitionHandbowEntity;
import net.apocalypse.careunite.entity.TribunalHealerEntity;
import net.apocalypse.careunite.entity.TrailriteTridentThrownEntity;
import net.apocalypse.careunite.entity.TombstoneUndertidesEntity;
import net.apocalypse.careunite.entity.TombstoneUndertideSmallEntity;
import net.apocalypse.careunite.entity.TombstoneStultiferaSmallEntity;
import net.apocalypse.careunite.entity.TombstoneStultiferaEntity;
import net.apocalypse.careunite.entity.TombstoneLivingEntity;
import net.apocalypse.careunite.entity.TidutantExcrescenceEntity;
import net.apocalypse.careunite.entity.TideutantRockSpiderEntity;
import net.apocalypse.careunite.entity.TiderageDireswineEntity;
import net.apocalypse.careunite.entity.TideDeathrepellerEntity;
import net.apocalypse.careunite.entity.TideChimeraEntity;
import net.apocalypse.careunite.entity.TideBishopEntity;
import net.apocalypse.careunite.entity.ThrowablePotionEntity;
import net.apocalypse.careunite.entity.ThirsterEntity;
import net.apocalypse.careunite.entity.TheLastKnightEntity;
import net.apocalypse.careunite.entity.TheAbandonedEntity;
import net.apocalypse.careunite.entity.TellerShotEntity;
import net.apocalypse.careunite.entity.SuperSliderEntity;
import net.apocalypse.careunite.entity.SuperBigCatEntity;
import net.apocalypse.careunite.entity.SplasherAbyssalEntity;
import net.apocalypse.careunite.entity.SpikeChestEntity;
import net.apocalypse.careunite.entity.SpecterEntity;
import net.apocalypse.careunite.entity.SpecterDollEntity;
import net.apocalypse.careunite.entity.SonsEntity;
import net.apocalypse.careunite.entity.SliderFishEntity;
import net.apocalypse.careunite.entity.SliderBallHugeEntity;
import net.apocalypse.careunite.entity.SliderBallEntity;
import net.apocalypse.careunite.entity.SkeletonSlashEntity;
import net.apocalypse.careunite.entity.SkadiEntity;
import net.apocalypse.careunite.entity.SkadiCorruptedEntity;
import net.apocalypse.careunite.entity.ShotOceanArrowEntity;
import net.apocalypse.careunite.entity.ShooterFishEntity;
import net.apocalypse.careunite.entity.ShieldOfHonourEntity;
import net.apocalypse.careunite.entity.ShakingBellEntity;
import net.apocalypse.careunite.entity.ScreamChestFishEntity;
import net.apocalypse.careunite.entity.SaintValerioEntity;
import net.apocalypse.careunite.entity.SaintCarmenEntity;
import net.apocalypse.careunite.entity.RunvolutionWaveEntity;
import net.apocalypse.careunite.entity.RunvolutionEntity;
import net.apocalypse.careunite.entity.RunFishEntity;
import net.apocalypse.careunite.entity.RouteShaperEntity;
import net.apocalypse.careunite.entity.RouteFractalEntity;
import net.apocalypse.careunite.entity.RocinanteEntity;
import net.apocalypse.careunite.entity.ReaperPetEntity;
import net.apocalypse.careunite.entity.ReaperFishEntity;
import net.apocalypse.careunite.entity.RaidSignEntity;
import net.apocalypse.careunite.entity.QunyouWantedIsharmlaEntity;
import net.apocalypse.careunite.entity.PunctureFishEntity;
import net.apocalypse.careunite.entity.PumpkinCarriageEntity;
import net.apocalypse.careunite.entity.PromotedSquidEntity;
import net.apocalypse.careunite.entity.PromotedGuardianEntity;
import net.apocalypse.careunite.entity.PromotedGlowSquidEntity;
import net.apocalypse.careunite.entity.PromotedElderGuardianEntity;
import net.apocalypse.careunite.entity.PromotedAxolotlEntity;
import net.apocalypse.careunite.entity.PregnantFishEntity;
import net.apocalypse.careunite.entity.PredatorAbyssalEntity;
import net.apocalypse.careunite.entity.PrayerSplashEntity;
import net.apocalypse.careunite.entity.PenalBatallionCrossbowmanEntity;
import net.apocalypse.careunite.entity.OceanziedWitchEntity;
import net.apocalypse.careunite.entity.OceannizedWitheriaEntity;
import net.apocalypse.careunite.entity.OceanizedWolfEntity;
import net.apocalypse.careunite.entity.OceanizedWitherEntity;
import net.apocalypse.careunite.entity.OceanizedWardenisEntity;
import net.apocalypse.careunite.entity.OceanizedWardenEntity;
import net.apocalypse.careunite.entity.OceanizedWanderingTraderEntity;
import net.apocalypse.careunite.entity.OceanizedVindicatorEntity;
import net.apocalypse.careunite.entity.OceanizedVillagerEntity;
import net.apocalypse.careunite.entity.OceanizedVexEntity;
import net.apocalypse.careunite.entity.OceanizedSpiderEntity;
import net.apocalypse.careunite.entity.OceanizedSnifferEntity;
import net.apocalypse.careunite.entity.OceanizedSnifferEggEntity;
import net.apocalypse.careunite.entity.OceanizedSilverfishEntity;
import net.apocalypse.careunite.entity.OceanizedShulkerEntity;
import net.apocalypse.careunite.entity.OceanizedSheepEntity;
import net.apocalypse.careunite.entity.OceanizedRavagerEntity;
import net.apocalypse.careunite.entity.OceanizedPolarBearEntity;
import net.apocalypse.careunite.entity.OceanizedPillagerEntity;
import net.apocalypse.careunite.entity.OceanizedPiglinEntity;
import net.apocalypse.careunite.entity.OceanizedPigEntity;
import net.apocalypse.careunite.entity.OceanizedLlamaEntity;
import net.apocalypse.careunite.entity.OceanizedIllusionerEntity;
import net.apocalypse.careunite.entity.OceanizedHorseEntity;
import net.apocalypse.careunite.entity.OceanizedHoglinEntity;
import net.apocalypse.careunite.entity.OceanizedFoxEntity;
import net.apocalypse.careunite.entity.OceanizedEvokerEntity;
import net.apocalypse.careunite.entity.OceanizedEndermiteEntity;
import net.apocalypse.careunite.entity.OceanizedEndermanEntity;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;
import net.apocalypse.careunite.entity.OceanizedDogEntity;
import net.apocalypse.careunite.entity.OceanizedCowEntity;
import net.apocalypse.careunite.entity.OceanizedChickenEntity;
import net.apocalypse.careunite.entity.OceanizedCatEntity;
import net.apocalypse.careunite.entity.OceanizedBruteEntity;
import net.apocalypse.careunite.entity.OceanizedBeeEntity;
import net.apocalypse.careunite.entity.OceanizeRabbitEntity;
import net.apocalypse.careunite.entity.OceanWitherSkeletonEntity;
import net.apocalypse.careunite.entity.OceanIllusionEntity;
import net.apocalypse.careunite.entity.NucleicMaleficentEntity;
import net.apocalypse.careunite.entity.NoviceAlchemistEntity;
import net.apocalypse.careunite.entity.NetherseaSlimeEntity;
import net.apocalypse.careunite.entity.NetherseaChestBoatEntity;
import net.apocalypse.careunite.entity.NetherseaBoatEntity;
import net.apocalypse.careunite.entity.NautilusHeadhunterEntity;
import net.apocalypse.careunite.entity.MonstrosityRocketEntity;
import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;
import net.apocalypse.careunite.entity.MoistDragonBreathEntity;
import net.apocalypse.careunite.entity.MegaStonecutterEntity;
import net.apocalypse.careunite.entity.MegaChestEntity;
import net.apocalypse.careunite.entity.MartusEntity;
import net.apocalypse.careunite.entity.LumenEntity;
import net.apocalypse.careunite.entity.LittleHelperEntity;
import net.apocalypse.careunite.entity.LingeringFractalEntity;
import net.apocalypse.careunite.entity.LineringPathshaperEntity;
import net.apocalypse.careunite.entity.LastKnightAndHorseEntity;
import net.apocalypse.careunite.entity.LargeArrowEntity;
import net.apocalypse.careunite.entity.JuniorWarriorPriestEntity;
import net.apocalypse.careunite.entity.IzumilDollEntity;
import net.apocalypse.careunite.entity.IzumikOffspringEntity;
import net.apocalypse.careunite.entity.IzumikEntity;
import net.apocalypse.careunite.entity.IshmaelEntity;
import net.apocalypse.careunite.entity.IsharmlaTearEntity;
import net.apocalypse.careunite.entity.IsharmlaEntity;
import net.apocalypse.careunite.entity.IreneEntity;
import net.apocalypse.careunite.entity.InquisitionFireEntity;
import net.apocalypse.careunite.entity.IberiaMonstrosityEntity;
import net.apocalypse.careunite.entity.HollowArmorEntity;
import net.apocalypse.careunite.entity.HighmoreShootEntity;
import net.apocalypse.careunite.entity.HighmoreEntity;
import net.apocalypse.careunite.entity.HealBullletEntity;
import net.apocalypse.careunite.entity.GunmuEntity;
import net.apocalypse.careunite.entity.GuideAbyssalEntity;
import net.apocalypse.careunite.entity.GladiiaWhirlEntity;
import net.apocalypse.careunite.entity.GladiiaEntity;
import net.apocalypse.careunite.entity.FlyFishEntity;
import net.apocalypse.careunite.entity.FloaterProkaryoteEntity;
import net.apocalypse.careunite.entity.FleefishBulletEntity;
import net.apocalypse.careunite.entity.FleeFishEntity;
import net.apocalypse.careunite.entity.FlamarineStatueEntity;
import net.apocalypse.careunite.entity.FlamarineGolemEntity;
import net.apocalypse.careunite.entity.FishSplashEntity;
import net.apocalypse.careunite.entity.FishShootEntity;
import net.apocalypse.careunite.entity.FirstTellerEntity;
import net.apocalypse.careunite.entity.FeederProkaryoteEntity;
import net.apocalypse.careunite.entity.FakerggShootEntity;
import net.apocalypse.careunite.entity.FakeOffspringEntity;
import net.apocalypse.careunite.entity.Endspeaker3Entity;
import net.apocalypse.careunite.entity.Endspeaker2Entity;
import net.apocalypse.careunite.entity.Endspeaker1Entity;
import net.apocalypse.careunite.entity.Endspeaker0Entity;
import net.apocalypse.careunite.entity.ElectShootEntity;
import net.apocalypse.careunite.entity.DivicellularGoEntity;
import net.apocalypse.careunite.entity.DepositerProkaryoteEntity;
import net.apocalypse.careunite.entity.DamageTesterEntity;
import net.apocalypse.careunite.entity.CreeperPetEntity;
import net.apocalypse.careunite.entity.CreeperFishEntity;
import net.apocalypse.careunite.entity.CrackerAbyssalEntity;
import net.apocalypse.careunite.entity.CorrectionalPhalanxyInfantryEntity;
import net.apocalypse.careunite.entity.CorrectinalPhalaxVanguardEntity;
import net.apocalypse.careunite.entity.ConfessionCenserEntity;
import net.apocalypse.careunite.entity.ComplexChitinTridentThrownEntity;
import net.apocalypse.careunite.entity.ComplexChitinGolemEntity;
import net.apocalypse.careunite.entity.CompassionPrayerEntity;
import net.apocalypse.careunite.entity.CollectorProkaryoteEntity;
import net.apocalypse.careunite.entity.ChitinTridentThrownEntity;
import net.apocalypse.careunite.entity.ChitinGolemEntity;
import net.apocalypse.careunite.entity.ChiselerFishEntity;
import net.apocalypse.careunite.entity.ChieslerPetEntity;
import net.apocalypse.careunite.entity.ChestFishEntity;
import net.apocalypse.careunite.entity.ChemistWashbasinEntity;
import net.apocalypse.careunite.entity.CarmenBulletEntity;
import net.apocalypse.careunite.entity.CaerulaOffspringEntity;
import net.apocalypse.careunite.entity.BoneFishEntity;
import net.apocalypse.careunite.entity.BishopFishEntity;
import net.apocalypse.careunite.entity.BeeStingerEntity;
import net.apocalypse.careunite.entity.BeaconSignEntity;
import net.apocalypse.careunite.entity.BeaconSelectorEntity;
import net.apocalypse.careunite.entity.BaselayerAbyssalEntity;
import net.apocalypse.careunite.entity.ApostleProkaryoteEntity;
import net.apocalypse.careunite.entity.ApocataEntity;
import net.apocalypse.careunite.entity.AnchorFlyEntity;
import net.apocalypse.careunite.entity.AlchemyBaseEntity;
import net.apocalypse.careunite.entity.Al1SHelperEntity;
import net.apocalypse.careunite.entity.AegirMortarEntity;
import net.apocalypse.careunite.entity.AccumulatorProkaryoteEntity;
import net.apocalypse.careunite.entity.AbsorberLimbEntity;
import net.apocalypse.careunite.entity.AbandonedShootEntity;
import net.apocalypse.careunite.CaerulaArborMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaerulaArborModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CaerulaArborMod.MODID);
	public static final RegistryObject<EntityType<RunFishEntity>> RUN_FISH = register("run_fish",
			EntityType.Builder.<RunFishEntity>of(RunFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(6).setUpdateInterval(3).setCustomClientFactory(RunFishEntity::new)

					.sized(0.4f, 0.5f));
	public static final RegistryObject<EntityType<SliderFishEntity>> SLIDER_FISH = register("slider_fish",
			EntityType.Builder.<SliderFishEntity>of(SliderFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(6).setUpdateInterval(3).setCustomClientFactory(SliderFishEntity::new)

					.sized(0.5f, 0.8f));
	public static final RegistryObject<EntityType<SuperSliderEntity>> SUPER_SLIDER = register("super_slider",
			EntityType.Builder.<SuperSliderEntity>of(SuperSliderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(SuperSliderEntity::new)

					.sized(0.3f, 0.8f));
	public static final RegistryObject<EntityType<ShooterFishEntity>> SHOOTER_FISH = register("shooter_fish",
			EntityType.Builder.<ShooterFishEntity>of(ShooterFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(ShooterFishEntity::new)

					.sized(0.6f, 1.2f));
	public static final RegistryObject<EntityType<FishShootEntity>> FISH_SHOOT = register("fish_shoot",
			EntityType.Builder.<FishShootEntity>of(FishShootEntity::new, MobCategory.MISC).setCustomClientFactory(FishShootEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<FlyFishEntity>> FLY_FISH = register("fly_fish",
			EntityType.Builder.<FlyFishEntity>of(FlyFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(FlyFishEntity::new)

					.sized(0.6f, 0.9f));
	public static final RegistryObject<EntityType<ReaperFishEntity>> REAPER_FISH = register("reaper_fish",
			EntityType.Builder.<ReaperFishEntity>of(ReaperFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10).setUpdateInterval(3).setCustomClientFactory(ReaperFishEntity::new)

					.sized(1.2f, 2.8f));
	public static final RegistryObject<EntityType<CreeperFishEntity>> CREEPER_FISH = register("creeper_fish",
			EntityType.Builder.<CreeperFishEntity>of(CreeperFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(CreeperFishEntity::new)

					.sized(0.8f, 1.5f));
	public static final RegistryObject<EntityType<PunctureFishEntity>> PUNCTURE_FISH = register("puncture_fish",
			EntityType.Builder.<PunctureFishEntity>of(PunctureFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(PunctureFishEntity::new)

					.sized(0.9f, 2.7f));
	public static final RegistryObject<EntityType<BaselayerAbyssalEntity>> BASELAYER_ABYSSAL = register("baselayer_abyssal",
			EntityType.Builder.<BaselayerAbyssalEntity>of(BaselayerAbyssalEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(BaselayerAbyssalEntity::new)

					.sized(0.7f, 1.5f));
	public static final RegistryObject<EntityType<PredatorAbyssalEntity>> PREDATOR_ABYSSAL = register("predator_abyssal",
			EntityType.Builder.<PredatorAbyssalEntity>of(PredatorAbyssalEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(PredatorAbyssalEntity::new)

					.sized(0.6f, 1.4f));
	public static final RegistryObject<EntityType<GuideAbyssalEntity>> GUIDE_ABYSSAL = register("guide_abyssal",
			EntityType.Builder.<GuideAbyssalEntity>of(GuideAbyssalEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(GuideAbyssalEntity::new)

					.sized(1f, 2.5f));
	public static final RegistryObject<EntityType<SplasherAbyssalEntity>> SPLASHER_ABYSSAL = register("splasher_abyssal",
			EntityType.Builder.<SplasherAbyssalEntity>of(SplasherAbyssalEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(SplasherAbyssalEntity::new)

					.sized(0.6f, 1.1f));
	public static final RegistryObject<EntityType<FishSplashEntity>> FISH_SPLASH = register("fish_splash",
			EntityType.Builder.<FishSplashEntity>of(FishSplashEntity::new, MobCategory.MISC).setCustomClientFactory(FishSplashEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<UmbrellaAbyssalEntity>> UMBRELLA_ABYSSAL = register("umbrella_abyssal",
			EntityType.Builder.<UmbrellaAbyssalEntity>of(UmbrellaAbyssalEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(UmbrellaAbyssalEntity::new)

					.sized(0.8f, 1.5f));
	public static final RegistryObject<EntityType<CrackerAbyssalEntity>> CRACKER_ABYSSAL = register("cracker_abyssal",
			EntityType.Builder.<CrackerAbyssalEntity>of(CrackerAbyssalEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(CrackerAbyssalEntity::new)

					.sized(0.7f, 2f));
	public static final RegistryObject<EntityType<CollectorProkaryoteEntity>> COLLECTOR_PROKARYOTE = register("collector_prokaryote",
			EntityType.Builder.<CollectorProkaryoteEntity>of(CollectorProkaryoteEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(6).setUpdateInterval(3)
					.setCustomClientFactory(CollectorProkaryoteEntity::new)

					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<BoneFishEntity>> BONE_FISH = register("bone_fish",
			EntityType.Builder.<BoneFishEntity>of(BoneFishEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(6).setUpdateInterval(3).setCustomClientFactory(BoneFishEntity::new)

					.sized(0.7f, 0.7f));
	public static final RegistryObject<EntityType<ChiselerFishEntity>> CHISELER_FISH = register("chiseler_fish",
			EntityType.Builder.<ChiselerFishEntity>of(ChiselerFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(ChiselerFishEntity::new)

					.sized(0.6f, 0.6f));
	public static final RegistryObject<EntityType<FakerggShootEntity>> FAKERGG_SHOOT = register("fakergg_shoot",
			EntityType.Builder.<FakerggShootEntity>of(FakerggShootEntity::new, MobCategory.MISC).setCustomClientFactory(FakerggShootEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.4f, 0.4f));
	public static final RegistryObject<EntityType<PregnantFishEntity>> PREGNANT_FISH = register("pregnant_fish",
			EntityType.Builder.<PregnantFishEntity>of(PregnantFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(PregnantFishEntity::new)

					.sized(0.7f, 1.1f));
	public static final RegistryObject<EntityType<FakeOffspringEntity>> FAKE_OFFSPRING = register("fake_offspring",
			EntityType.Builder.<FakeOffspringEntity>of(FakeOffspringEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(6).setUpdateInterval(3).setCustomClientFactory(FakeOffspringEntity::new)

					.sized(0.6f, 0.6f));
	public static final RegistryObject<EntityType<FleefishBulletEntity>> FLEEFISH_BULLET = register("fleefish_bullet",
			EntityType.Builder.<FleefishBulletEntity>of(FleefishBulletEntity::new, MobCategory.MISC).setCustomClientFactory(FleefishBulletEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.4f, 0.4f));
	public static final RegistryObject<EntityType<FleeFishEntity>> FLEE_FISH = register("flee_fish",
			EntityType.Builder.<FleeFishEntity>of(FleeFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(FleeFishEntity::new)

					.sized(0.8f, 1.1f));
	public static final RegistryObject<EntityType<RouteShaperEntity>> ROUTE_SHAPER = register("route_shaper",
			EntityType.Builder.<RouteShaperEntity>of(RouteShaperEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(RouteShaperEntity::new)

					.sized(1.8f, 4f));
	public static final RegistryObject<EntityType<RouteFractalEntity>> ROUTE_FRACTAL = register("route_fractal",
			EntityType.Builder.<RouteFractalEntity>of(RouteFractalEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(RouteFractalEntity::new)

					.sized(0.7f, 1.5f));
	public static final RegistryObject<EntityType<TellerShotEntity>> TELLER_SHOT = register("teller_shot",
			EntityType.Builder.<TellerShotEntity>of(TellerShotEntity::new, MobCategory.MISC).setCustomClientFactory(TellerShotEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.4f, 0.4f));
	public static final RegistryObject<EntityType<FirstTellerEntity>> FIRST_TO_TALK = register("first_to_talk",
			EntityType.Builder.<FirstTellerEntity>of(FirstTellerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(FirstTellerEntity::new)

					.sized(0.9f, 2.7f));
	public static final RegistryObject<EntityType<ReaperPetEntity>> REAPER_PET = register("reaper_pet",
			EntityType.Builder.<ReaperPetEntity>of(ReaperPetEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(ReaperPetEntity::new)

					.sized(0.6f, 1.3f));
	public static final RegistryObject<EntityType<BishopFishEntity>> BISHOP_FISH = register("bishop_fish",
			EntityType.Builder.<BishopFishEntity>of(BishopFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(BishopFishEntity::new)

					.sized(1.4f, 2.2f));
	public static final RegistryObject<EntityType<TideBishopEntity>> TIDE_BISHOP = register("tide_bishop",
			EntityType.Builder.<TideBishopEntity>of(TideBishopEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(TideBishopEntity::new)

					.sized(1.1f, 2.2f));
	public static final RegistryObject<EntityType<SonsEntity>> SONS = register("sons",
			EntityType.Builder.<SonsEntity>of(SonsEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(6).setUpdateInterval(3).setCustomClientFactory(SonsEntity::new)

					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<FloaterProkaryoteEntity>> FLOATER_PROKARYOTE = register("floater_prokaryote",
			EntityType.Builder.<FloaterProkaryoteEntity>of(FloaterProkaryoteEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(FloaterProkaryoteEntity::new)

					.sized(0.6f, 1.5f));
	public static final RegistryObject<EntityType<ChitinGolemEntity>> CHITIN_GOLEM = register("chitin_golem",
			EntityType.Builder.<ChitinGolemEntity>of(ChitinGolemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(ChitinGolemEntity::new)

					.sized(1.75f, 4f));
	public static final RegistryObject<EntityType<TideDeathrepellerEntity>> TIDE_DEATHREPELLER = register("tide_deathrepeller",
			EntityType.Builder.<TideDeathrepellerEntity>of(TideDeathrepellerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(TideDeathrepellerEntity::new)

					.sized(1.5f, 2f));
	public static final RegistryObject<EntityType<MegaChestEntity>> MEGA_CHEST = register("mega_chest",
			EntityType.Builder.<MegaChestEntity>of(MegaChestEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(14).setUpdateInterval(3).setCustomClientFactory(MegaChestEntity::new)

					.sized(0.8f, 0.9f));
	public static final RegistryObject<EntityType<ApostleProkaryoteEntity>> APOSTLE_PROKARYOTE = register("apostle_prokaryote",
			EntityType.Builder.<ApostleProkaryoteEntity>of(ApostleProkaryoteEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(ApostleProkaryoteEntity::new)

					.sized(0.6f, 1.7f));
	public static final RegistryObject<EntityType<HighmoreShootEntity>> HIGHMORE_SHOOT = register("highmore_shoot",
			EntityType.Builder.<HighmoreShootEntity>of(HighmoreShootEntity::new, MobCategory.MISC).setCustomClientFactory(HighmoreShootEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.4f, 0.4f));
	public static final RegistryObject<EntityType<HighmoreEntity>> HIGHMORE = register("highmore",
			EntityType.Builder.<HighmoreEntity>of(HighmoreEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(HighmoreEntity::new)

					.sized(1.4f, 1.6f));
	public static final RegistryObject<EntityType<AccumulatorProkaryoteEntity>> ACCUMULATOR_PROKARYOTE = register("accumulator_prokaryote",
			EntityType.Builder.<AccumulatorProkaryoteEntity>of(AccumulatorProkaryoteEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3)
					.setCustomClientFactory(AccumulatorProkaryoteEntity::new)

					.sized(0.5f, 1f));
	public static final RegistryObject<EntityType<FeederProkaryoteEntity>> FEEDER_PROKARYOTE = register("feeder_prokaryote",
			EntityType.Builder.<FeederProkaryoteEntity>of(FeederProkaryoteEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(FeederProkaryoteEntity::new)

					.sized(0.8f, 1.1f));
	public static final RegistryObject<EntityType<ChestFishEntity>> CHEST_FISH = register("chest_fish",
			EntityType.Builder.<ChestFishEntity>of(ChestFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(ChestFishEntity::new)

					.sized(0.9f, 0.9f));
	public static final RegistryObject<EntityType<SpikeChestEntity>> SPIKE_CHEST = register("spike_chest",
			EntityType.Builder.<SpikeChestEntity>of(SpikeChestEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(SpikeChestEntity::new)

					.sized(0.9f, 0.9f));
	public static final RegistryObject<EntityType<SkadiEntity>> SKADI = register("skadi",
			EntityType.Builder.<SkadiEntity>of(SkadiEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(SkadiEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<DepositerProkaryoteEntity>> DEPOSITER_PROKARYOTE = register("depositer_prokaryote",
			EntityType.Builder.<DepositerProkaryoteEntity>of(DepositerProkaryoteEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3)
					.setCustomClientFactory(DepositerProkaryoteEntity::new)

					.sized(0.625f, 1f));
	public static final RegistryObject<EntityType<OceanizedVillagerEntity>> OCEANIZED_VILLAGER = register("oceanized_villager",
			EntityType.Builder.<OceanizedVillagerEntity>of(OceanizedVillagerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedVillagerEntity::new)

					.sized(0.6f, 2f));
	public static final RegistryObject<EntityType<OceanizedVindicatorEntity>> OCEANIZED_VINDICATOR = register("oceanized_vindicator",
			EntityType.Builder.<OceanizedVindicatorEntity>of(OceanizedVindicatorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedVindicatorEntity::new)

					.sized(0.6f, 2f));
	public static final RegistryObject<EntityType<ShotOceanArrowEntity>> SHOT_OCEAN_ARROW = register("shot_ocean_arrow",
			EntityType.Builder.<ShotOceanArrowEntity>of(ShotOceanArrowEntity::new, MobCategory.MISC).setCustomClientFactory(ShotOceanArrowEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<OceanizedPillagerEntity>> OCEANIZED_PILLAGER = register("oceanized_pillager",
			EntityType.Builder.<OceanizedPillagerEntity>of(OceanizedPillagerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedPillagerEntity::new)

					.sized(0.6f, 1.9f));
	public static final RegistryObject<EntityType<AnchorFlyEntity>> ANCHOR_FLY = register("anchor_fly",
			EntityType.Builder.<AnchorFlyEntity>of(AnchorFlyEntity::new, MobCategory.MISC).setCustomClientFactory(AnchorFlyEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.2f, 0.2f));
	public static final RegistryObject<EntityType<OceanizedPigEntity>> OCEANIZED_PIG = register("oceanized_pig",
			EntityType.Builder.<OceanizedPigEntity>of(OceanizedPigEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(6).setUpdateInterval(3).setCustomClientFactory(OceanizedPigEntity::new)

					.sized(0.6f, 1f));
	public static final RegistryObject<EntityType<OceanizedCowEntity>> OCEANIZED_COW = register("oceanized_cow",
			EntityType.Builder.<OceanizedCowEntity>of(OceanizedCowEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(6).setUpdateInterval(3).setCustomClientFactory(OceanizedCowEntity::new)

					.sized(0.6f, 1.375f));
	public static final RegistryObject<EntityType<OceanizedSheepEntity>> OCEANIZED_SHEEP = register("oceanized_sheep",
			EntityType.Builder.<OceanizedSheepEntity>of(OceanizedSheepEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(6).setUpdateInterval(3).setCustomClientFactory(OceanizedSheepEntity::new)

					.sized(0.6f, 1f));
	public static final RegistryObject<EntityType<OceanizedHorseEntity>> OCEANIZED_HORSE = register("oceanized_horse",
			EntityType.Builder.<OceanizedHorseEntity>of(OceanizedHorseEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedHorseEntity::new)

					.sized(1.25f, 1.8f));
	public static final RegistryObject<EntityType<OceanizedPiglinEntity>> OCEANIZED_PIGLIN = register("oceanized_piglin", EntityType.Builder.<OceanizedPiglinEntity>of(OceanizedPiglinEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedPiglinEntity::new).fireImmune().sized(0.6f, 2f));
	public static final RegistryObject<EntityType<OceanizedBruteEntity>> OCEANIZED_BRUTE = register("oceanized_brute", EntityType.Builder.<OceanizedBruteEntity>of(OceanizedBruteEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(10).setUpdateInterval(3).setCustomClientFactory(OceanizedBruteEntity::new).fireImmune().sized(0.6f, 2f));
	public static final RegistryObject<EntityType<OceanizedSpiderEntity>> OCEANIZED_SPIDER = register("oceanized_spider",
			EntityType.Builder.<OceanizedSpiderEntity>of(OceanizedSpiderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(7).setUpdateInterval(3).setCustomClientFactory(OceanizedSpiderEntity::new)

					.sized(0.9f, 0.7f));
	public static final RegistryObject<EntityType<OceanizedEndermanEntity>> OCEANIZED_ENDERMAN = register("oceanized_enderman",
			EntityType.Builder.<OceanizedEndermanEntity>of(OceanizedEndermanEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10).setUpdateInterval(3).setCustomClientFactory(OceanizedEndermanEntity::new)

					.sized(0.6f, 3f));
	public static final RegistryObject<EntityType<OceanizedWolfEntity>> OCEANIZED_WOLF = register("oceanized_wolf",
			EntityType.Builder.<OceanizedWolfEntity>of(OceanizedWolfEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(7).setUpdateInterval(3).setCustomClientFactory(OceanizedWolfEntity::new)

					.sized(0.7f, 0.8f));
	public static final RegistryObject<EntityType<OceanizedDogEntity>> OCEANIZED_DOG = register("oceanized_dog",
			EntityType.Builder.<OceanizedDogEntity>of(OceanizedDogEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedDogEntity::new)

					.sized(0.7f, 0.8f));
	public static final RegistryObject<EntityType<OceanizedRavagerEntity>> OCEANIZED_RAVAGER = register("oceanized_ravager",
			EntityType.Builder.<OceanizedRavagerEntity>of(OceanizedRavagerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10).setUpdateInterval(3).setCustomClientFactory(OceanizedRavagerEntity::new)

					.sized(1.9f, 2.5f));
	public static final RegistryObject<EntityType<OceanziedWitchEntity>> OCEANIZED_WITCH = register("oceanized_witch",
			EntityType.Builder.<OceanziedWitchEntity>of(OceanziedWitchEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanziedWitchEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ThrowablePotionEntity>> THROWABLE_POTION = register("throwable_potion", EntityType.Builder.<ThrowablePotionEntity>of(ThrowablePotionEntity::new, MobCategory.MISC)
			.setCustomClientFactory(ThrowablePotionEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.2f, 0.2f));
	public static final RegistryObject<EntityType<IzumikOffspringEntity>> IZUMIK_OFFSPRING = register("izumik_offspring",
			EntityType.Builder.<IzumikOffspringEntity>of(IzumikOffspringEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(IzumikOffspringEntity::new)

					.sized(0.5f, 0.9f));
	public static final RegistryObject<EntityType<IzumikEntity>> IZUMIK = register("izumik",
			EntityType.Builder.<IzumikEntity>of(IzumikEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(21).setUpdateInterval(3).setCustomClientFactory(IzumikEntity::new).fireImmune().sized(3.3f, 9f));
	public static final RegistryObject<EntityType<DivicellularGoEntity>> DIVICELLULAR_GO = register("divicellular_go",
			EntityType.Builder.<DivicellularGoEntity>of(DivicellularGoEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(DivicellularGoEntity::new)

					.sized(0.5f, 1f));
	public static final RegistryObject<EntityType<OceanizedEvokerEntity>> OCEANIZED_EVOKER = register("oceanized_evoker",
			EntityType.Builder.<OceanizedEvokerEntity>of(OceanizedEvokerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedEvokerEntity::new)

					.sized(0.6f, 1.9f));
	public static final RegistryObject<EntityType<JuniorWarriorPriestEntity>> JUNIOR_WARRIOR_PRIEST = register("junior_warrior_priest",
			EntityType.Builder.<JuniorWarriorPriestEntity>of(JuniorWarriorPriestEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(JuniorWarriorPriestEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<WarriorPriestEntity>> WARRIOR_PRIEST = register("warrior_priest",
			EntityType.Builder.<WarriorPriestEntity>of(WarriorPriestEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(WarriorPriestEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<CorrectionalPhalanxyInfantryEntity>> CORRECTIONAL_PHALANXY_INFANTRY = register("correctional_phalanxy_infantry",
			EntityType.Builder.<CorrectionalPhalanxyInfantryEntity>of(CorrectionalPhalanxyInfantryEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3)
					.setCustomClientFactory(CorrectionalPhalanxyInfantryEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<CorrectinalPhalaxVanguardEntity>> CORRECTIONAL_PHALAX_VANGUARD = register("correctional_phalax_vanguard",
			EntityType.Builder.<CorrectinalPhalaxVanguardEntity>of(CorrectinalPhalaxVanguardEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(CorrectinalPhalaxVanguardEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<TribunalHealerEntity>> TRIBUNAL_HEALER = register("tribunal_healer",
			EntityType.Builder.<TribunalHealerEntity>of(TribunalHealerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(TribunalHealerEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<HealBullletEntity>> HEAL_BULLLET = register("heal_bulllet",
			EntityType.Builder.<HealBullletEntity>of(HealBullletEntity::new, MobCategory.MISC).setCustomClientFactory(HealBullletEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.1f, 0.1f));
	public static final RegistryObject<EntityType<MartusEntity>> MARTUS = register("martus",
			EntityType.Builder.<MartusEntity>of(MartusEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(MartusEntity::new).fireImmune().sized(0.7f, 2.8f));
	public static final RegistryObject<EntityType<TheAbandonedEntity>> THE_ABANDONED = register("the_abandoned",
			EntityType.Builder.<TheAbandonedEntity>of(TheAbandonedEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(TheAbandonedEntity::new)

					.sized(0.7f, 2.25f));
	public static final RegistryObject<EntityType<AbandonedShootEntity>> ABANDONED_SHOOT = register("abandoned_shoot",
			EntityType.Builder.<AbandonedShootEntity>of(AbandonedShootEntity::new, MobCategory.MISC).setCustomClientFactory(AbandonedShootEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<GunmuEntity>> GUNMU = register("gunmu",
			EntityType.Builder.<GunmuEntity>of(GunmuEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(GunmuEntity::new).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<OceanizedWardenEntity>> OCEANIZED_WARDEN = register("oceanized_warden", EntityType.Builder.<OceanizedWardenEntity>of(OceanizedWardenEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(OceanizedWardenEntity::new).fireImmune().sized(1.2f, 3.1f));
	public static final RegistryObject<EntityType<OceanizedCatEntity>> OCEANIZED_CAT = register("oceanized_cat",
			EntityType.Builder.<OceanizedCatEntity>of(OceanizedCatEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedCatEntity::new)

					.sized(0.5f, 0.625f));
	public static final RegistryObject<EntityType<SuperBigCatEntity>> SUPER_BIG_CAT = register("super_big_cat",
			EntityType.Builder.<SuperBigCatEntity>of(SuperBigCatEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(SuperBigCatEntity::new)

					.sized(3.5f, 4f));
	public static final RegistryObject<EntityType<ComplexChitinGolemEntity>> COMPLEX_CHITIN_GOLEM = register("complex_chitin_golem", EntityType.Builder.<ComplexChitinGolemEntity>of(ComplexChitinGolemEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ComplexChitinGolemEntity::new).fireImmune().sized(1.75f, 4f));
	public static final RegistryObject<EntityType<OceanizedWardenisEntity>> OCEANIZED_WARDENIS = register("oceanized_wardenis", EntityType.Builder.<OceanizedWardenisEntity>of(OceanizedWardenisEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(OceanizedWardenisEntity::new).fireImmune().sized(0.6f, 1.85f));
	public static final RegistryObject<EntityType<NucleicMaleficentEntity>> NUCLEIC_MALEFICENT = register("nucleic_maleficent",
			EntityType.Builder.<NucleicMaleficentEntity>of(NucleicMaleficentEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(NucleicMaleficentEntity::new)

					.sized(0.7f, 1.5f));
	public static final RegistryObject<EntityType<OceanizedWitherEntity>> OCEANIZED_WITHER = register("oceanized_wither", EntityType.Builder.<OceanizedWitherEntity>of(OceanizedWitherEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(OceanizedWitherEntity::new).fireImmune().sized(1.1f, 3.5f));
	public static final RegistryObject<EntityType<WitherShootPreEntity>> WITHER_SHOOT_PRE = register("wither_shoot_pre",
			EntityType.Builder.<WitherShootPreEntity>of(WitherShootPreEntity::new, MobCategory.MISC).setCustomClientFactory(WitherShootPreEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.1f, 0.1f));
	public static final RegistryObject<EntityType<OceannizedWitheriaEntity>> OCEANIZED_WITHERIA = register("oceanized_witheria", EntityType.Builder.<OceannizedWitheriaEntity>of(OceannizedWitheriaEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(OceannizedWitheriaEntity::new).fireImmune().sized(0.7f, 2.5f));
	public static final RegistryObject<EntityType<TheLastKnightEntity>> THE_LAST_KNIGHT = register("the_last_knight", EntityType.Builder.<TheLastKnightEntity>of(TheLastKnightEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(TheLastKnightEntity::new).fireImmune().sized(1f, 3.6f));
	public static final RegistryObject<EntityType<LastKnightAndHorseEntity>> LAST_KNIGHT_AND_HORSE = register("last_knight_and_horse", EntityType.Builder.<LastKnightAndHorseEntity>of(LastKnightAndHorseEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(LastKnightAndHorseEntity::new).fireImmune().sized(1.2f, 4f));
	public static final RegistryObject<EntityType<RocinanteEntity>> ROCINANTE = register("rocinante",
			EntityType.Builder.<RocinanteEntity>of(RocinanteEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(RocinanteEntity::new)

					.sized(1.2f, 3f));
	public static final RegistryObject<EntityType<ApocataEntity>> APOCATA = register("apocata",
			EntityType.Builder.<ApocataEntity>of(ApocataEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ApocataEntity::new)

					.sized(0.6f, 1.85f));
	public static final RegistryObject<EntityType<OceanizedFoxEntity>> OCEANIZED_FOX = register("oceanized_fox",
			EntityType.Builder.<OceanizedFoxEntity>of(OceanizedFoxEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedFoxEntity::new)

					.sized(0.6f, 0.7f));
	public static final RegistryObject<EntityType<TidutantExcrescenceEntity>> TIDUTANT_EXCRESCENCE = register("tidutant_excrescence",
			EntityType.Builder.<TidutantExcrescenceEntity>of(TidutantExcrescenceEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(TidutantExcrescenceEntity::new)

					.sized(0.5f, 0.4f));
	public static final RegistryObject<EntityType<OceanizedPolarBearEntity>> OCEANIZED_POLAR_BEAR = register("oceanized_polar_bear",
			EntityType.Builder.<OceanizedPolarBearEntity>of(OceanizedPolarBearEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedPolarBearEntity::new)

					.sized(1f, 1.25f));
	public static final RegistryObject<EntityType<TideutantRockSpiderEntity>> TIDUTANT_ROCK_SPIDER = register("tidutant_rock_spider",
			EntityType.Builder.<TideutantRockSpiderEntity>of(TideutantRockSpiderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(TideutantRockSpiderEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Endspeaker0Entity>> ENDSPEAKER_0 = register("endspeaker_0",
			EntityType.Builder.<Endspeaker0Entity>of(Endspeaker0Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(Endspeaker0Entity::new)

					.sized(0.65f, 0.7f));
	public static final RegistryObject<EntityType<Endspeaker1Entity>> ENDSPEAKER_1 = register("endspeaker_1",
			EntityType.Builder.<Endspeaker1Entity>of(Endspeaker1Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(Endspeaker1Entity::new)

					.sized(0.75f, 1.5f));
	public static final RegistryObject<EntityType<Endspeaker2Entity>> ENDSPEAKER_2 = register("endspeaker_2",
			EntityType.Builder.<Endspeaker2Entity>of(Endspeaker2Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(Endspeaker2Entity::new)

					.sized(1.2f, 2.8f));
	public static final RegistryObject<EntityType<Endspeaker3Entity>> ENDSPEAKER_3 = register("endspeaker_3",
			EntityType.Builder.<Endspeaker3Entity>of(Endspeaker3Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(14).setUpdateInterval(3).setCustomClientFactory(Endspeaker3Entity::new)

					.sized(1f, 3.375f));
	public static final RegistryObject<EntityType<LineringPathshaperEntity>> LINGERING_PATHSHAPER = register("lingering_pathshaper",
			EntityType.Builder.<LineringPathshaperEntity>of(LineringPathshaperEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(LineringPathshaperEntity::new)

					.sized(1.8f, 4f));
	public static final RegistryObject<EntityType<LingeringFractalEntity>> LINGERING_FRACTAL = register("lingering_fractal",
			EntityType.Builder.<LingeringFractalEntity>of(LingeringFractalEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(LingeringFractalEntity::new)

					.sized(0.7f, 1.5f));
	public static final RegistryObject<EntityType<LittleHelperEntity>> LITTLE_HELPER = register("little_helper", EntityType.Builder.<LittleHelperEntity>of(LittleHelperEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(LittleHelperEntity::new).fireImmune().sized(0.875f, 0.2f));
	public static final RegistryObject<EntityType<Al1SHelperEntity>> AL_1_S_HELPER = register("al_1_s_helper", EntityType.Builder.<Al1SHelperEntity>of(Al1SHelperEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(Al1SHelperEntity::new).fireImmune().sized(0.875f, 0.2f));
	public static final RegistryObject<EntityType<DamageTesterEntity>> DAMAGE_TESTER = register("damage_tester",
			EntityType.Builder.<DamageTesterEntity>of(DamageTesterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(DamageTesterEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<UlpiansEntity>> ULPIANS = register("ulpians",
			EntityType.Builder.<UlpiansEntity>of(UlpiansEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(UlpiansEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<GladiiaEntity>> GLADIIA = register("gladiia",
			EntityType.Builder.<GladiiaEntity>of(GladiiaEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(GladiiaEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<GladiiaWhirlEntity>> GLADIIA_WHIRL = register("gladiia_whirl",
			EntityType.Builder.<GladiiaWhirlEntity>of(GladiiaWhirlEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(GladiiaWhirlEntity::new)

					.sized(2f, 2f));
	public static final RegistryObject<EntityType<SpecterEntity>> SPECTER = register("specter",
			EntityType.Builder.<SpecterEntity>of(SpecterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(SpecterEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SpecterDollEntity>> SPECTER_DOLL = register("specter_doll",
			EntityType.Builder.<SpecterDollEntity>of(SpecterDollEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(SpecterDollEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<IreneEntity>> IRENE = register("irene",
			EntityType.Builder.<IreneEntity>of(IreneEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(IreneEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<TideChimeraEntity>> TIDE_CHIMERA = register("tide_chimera",
			EntityType.Builder.<TideChimeraEntity>of(TideChimeraEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(TideChimeraEntity::new)

					.sized(1.8f, 4.65f));
	public static final RegistryObject<EntityType<SkadiCorruptedEntity>> SKADI_CORRUPTED = register("skadi_corrupted", EntityType.Builder.<SkadiCorruptedEntity>of(SkadiCorruptedEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(SkadiCorruptedEntity::new).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<OceanizeRabbitEntity>> OCEANIZE_RABBIT = register("oceanize_rabbit",
			EntityType.Builder.<OceanizeRabbitEntity>of(OceanizeRabbitEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizeRabbitEntity::new)

					.sized(0.4f, 0.5f));
	public static final RegistryObject<EntityType<SaintCarmenEntity>> SAINT_CARMEN = register("saint_carmen",
			EntityType.Builder.<SaintCarmenEntity>of(SaintCarmenEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(SaintCarmenEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<CarmenBulletEntity>> CARMEN_BULLET = register("carmen_bullet",
			EntityType.Builder.<CarmenBulletEntity>of(CarmenBulletEntity::new, MobCategory.MISC).setCustomClientFactory(CarmenBulletEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<OceanizedIllusionerEntity>> OCEANIZED_ILLUSIONER = register("oceanized_illusioner",
			EntityType.Builder.<OceanizedIllusionerEntity>of(OceanizedIllusionerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(10).setUpdateInterval(3).setCustomClientFactory(OceanizedIllusionerEntity::new)

					.sized(0.6f, 1.9f));
	public static final RegistryObject<EntityType<OceanIllusionEntity>> OCEAN_ILLUSION = register("ocean_illusion",
			EntityType.Builder.<OceanIllusionEntity>of(OceanIllusionEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(9).setUpdateInterval(3).setCustomClientFactory(OceanIllusionEntity::new)

					.sized(0.6f, 1.9f));
	public static final RegistryObject<EntityType<FlamarineStatueEntity>> FLAMARINE_STATUE = register("flamarine_statue", EntityType.Builder.<FlamarineStatueEntity>of(FlamarineStatueEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(9).setUpdateInterval(3).setCustomClientFactory(FlamarineStatueEntity::new).fireImmune().sized(0.7f, 2f));
	public static final RegistryObject<EntityType<NautilusHeadhunterEntity>> NAUTILUS_HEADHUNTER = register("nautilus_headhunter",
			EntityType.Builder.<NautilusHeadhunterEntity>of(NautilusHeadhunterEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(9).setUpdateInterval(3)
					.setCustomClientFactory(NautilusHeadhunterEntity::new)

					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<XantisEntity>> XANTIS = register("xantis",
			EntityType.Builder.<XantisEntity>of(XantisEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(XantisEntity::new)

					.sized(0.5f, 0.68f));
	public static final RegistryObject<EntityType<FlamarineGolemEntity>> FLAMARINE_GOLEM = register("flamarine_golem", EntityType.Builder.<FlamarineGolemEntity>of(FlamarineGolemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(FlamarineGolemEntity::new).fireImmune().sized(1f, 2.75f));
	public static final RegistryObject<EntityType<OceanizedVexEntity>> OCEANIZED_VEX = register("oceanized_vex",
			EntityType.Builder.<OceanizedVexEntity>of(OceanizedVexEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(8).setUpdateInterval(3).setCustomClientFactory(OceanizedVexEntity::new)

					.sized(0.4f, 0.8f));
	public static final RegistryObject<EntityType<IsharmlaEntity>> ISHARMLA = register("isharmla",
			EntityType.Builder.<IsharmlaEntity>of(IsharmlaEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(IsharmlaEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<QunyouWantedIsharmlaEntity>> QUNYOU_WANTED_ISHARMLA = register("qunyou_wanted_isharmla",
			EntityType.Builder.<QunyouWantedIsharmlaEntity>of(QunyouWantedIsharmlaEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3)
					.setCustomClientFactory(QunyouWantedIsharmlaEntity::new)

					.sized(20f, 20f));
	public static final RegistryObject<EntityType<IsharmlaTearEntity>> ISHARMLA_TEAR = register("isharmla_tear",
			EntityType.Builder.<IsharmlaTearEntity>of(IsharmlaTearEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(IsharmlaTearEntity::new)

					.sized(1f, 0.5f));
	public static final RegistryObject<EntityType<PrayerSplashEntity>> PRAYER_SPLASH = register("prayer_splash",
			EntityType.Builder.<PrayerSplashEntity>of(PrayerSplashEntity::new, MobCategory.MISC).setCustomClientFactory(PrayerSplashEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.3f, 0.3f));
	public static final RegistryObject<EntityType<CompassionPrayerEntity>> COMPASSION_PRAYER = register("compassion_prayer",
			EntityType.Builder.<CompassionPrayerEntity>of(CompassionPrayerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(9).setUpdateInterval(3).setCustomClientFactory(CompassionPrayerEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<OceanizedEnderinaEntity>> OCEANIZED_ENDERINA = register("oceanized_enderina",
			EntityType.Builder.<OceanizedEnderinaEntity>of(OceanizedEnderinaEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(OceanizedEnderinaEntity::new)

					.sized(0.75f, 1.95f));
	public static final RegistryObject<EntityType<MoistDragonBreathEntity>> MOIST_DRAGON_BREATH = register("moist_dragon_breath",
			EntityType.Builder.<MoistDragonBreathEntity>of(MoistDragonBreathEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(MoistDragonBreathEntity::new)

					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<MoistEnderCrystalEntity>> MOIST_ENDER_CRYSTAL = register("moist_ender_crystal",
			EntityType.Builder.<MoistEnderCrystalEntity>of(MoistEnderCrystalEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(MoistEnderCrystalEntity::new)

					.sized(2f, 2f));
	public static final RegistryObject<EntityType<ThirsterEntity>> THIRSTER = register("thirster",
			EntityType.Builder.<ThirsterEntity>of(ThirsterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(ThirsterEntity::new)

					.sized(1.5f, 1.75f));
	public static final RegistryObject<EntityType<AbsorberLimbEntity>> ABSORBER_LIMB = register("absorber_limb",
			EntityType.Builder.<AbsorberLimbEntity>of(AbsorberLimbEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(AbsorberLimbEntity::new)

					.sized(0.4f, 1f));
	public static final RegistryObject<EntityType<ScreamChestFishEntity>> SCREAM_CHEST_FISH = register("scream_chest_fish",
			EntityType.Builder.<ScreamChestFishEntity>of(ScreamChestFishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ScreamChestFishEntity::new)

					.sized(0.9f, 0.9f));
	public static final RegistryObject<EntityType<OceanizedChickenEntity>> OCEANIZED_CHICKEN = register("oceanized_chicken",
			EntityType.Builder.<OceanizedChickenEntity>of(OceanizedChickenEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(9).setUpdateInterval(3).setCustomClientFactory(OceanizedChickenEntity::new)

					.sized(0.5f, 0.875f));
	public static final RegistryObject<EntityType<NetherseaSlimeEntity>> NETHERSEA_SLIME = register("nethersea_slime",
			EntityType.Builder.<NetherseaSlimeEntity>of(NetherseaSlimeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(9).setUpdateInterval(3).setCustomClientFactory(NetherseaSlimeEntity::new)

					.sized(1f, 1f));
	public static final RegistryObject<EntityType<OceanizedShulkerEntity>> OCEANIZED_SHULKER = register("oceanized_shulker",
			EntityType.Builder.<OceanizedShulkerEntity>of(OceanizedShulkerEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(9).setUpdateInterval(3).setCustomClientFactory(OceanizedShulkerEntity::new)

					.sized(1f, 1f));
	public static final RegistryObject<EntityType<OceanizedSnifferEntity>> OCEANIZED_SNIFFER = register("oceanized_sniffer",
			EntityType.Builder.<OceanizedSnifferEntity>of(OceanizedSnifferEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(9).setUpdateInterval(3).setCustomClientFactory(OceanizedSnifferEntity::new)

					.sized(2f, 2f));
	public static final RegistryObject<EntityType<OceanizedSnifferEggEntity>> OCEANIZED_SNIFFER_EGG = register("oceanized_sniffer_egg",
			EntityType.Builder.<OceanizedSnifferEggEntity>of(OceanizedSnifferEggEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(OceanizedSnifferEggEntity::new)

					.sized(0.6f, 1f));
	public static final RegistryObject<EntityType<CaerulaOffspringEntity>> CAERULA_OFFSPRING = register("caerula_offspring",
			EntityType.Builder.<CaerulaOffspringEntity>of(CaerulaOffspringEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(CaerulaOffspringEntity::new)

					.sized(0.4f, 0.75f));
	public static final RegistryObject<EntityType<RunvolutionEntity>> RUNVOLUTION = register("runvolution", EntityType.Builder.<RunvolutionEntity>of(RunvolutionEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(RunvolutionEntity::new).fireImmune().sized(2f, 5.25f));
	public static final RegistryObject<EntityType<RunvolutionWaveEntity>> RUNVOLUTION_WAVE = register("runvolution_wave",
			EntityType.Builder.<RunvolutionWaveEntity>of(RunvolutionWaveEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(RunvolutionWaveEntity::new)

					.sized(16f, 0.5f));
	public static final RegistryObject<EntityType<TombstoneUndertidesEntity>> TOMBSTONE_UNDERTIDES = register("tombstone_undertides",
			EntityType.Builder.<TombstoneUndertidesEntity>of(TombstoneUndertidesEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(TombstoneUndertidesEntity::new)

					.sized(0.75f, 0.5f));
	public static final RegistryObject<EntityType<TombstoneUndertideSmallEntity>> TOMBSTONE_UNDERTIDE_SMALL = register("tombstone_undertide_small",
			EntityType.Builder.<TombstoneUndertideSmallEntity>of(TombstoneUndertideSmallEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(TombstoneUndertideSmallEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<TombstoneStultiferaEntity>> TOMBSTONE_STULTIFERA = register("tombstone_stultifera",
			EntityType.Builder.<TombstoneStultiferaEntity>of(TombstoneStultiferaEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(TombstoneStultiferaEntity::new)

					.sized(0.75f, 0.5f));
	public static final RegistryObject<EntityType<TombstoneStultiferaSmallEntity>> TOMBSTONE_STULTIFERA_SMALL = register("tombstone_stultifera_small",
			EntityType.Builder.<TombstoneStultiferaSmallEntity>of(TombstoneStultiferaSmallEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(TombstoneStultiferaSmallEntity::new)

					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<TombstoneLivingEntity>> TOMBSTONE_LIVING = register("tombstone_living",
			EntityType.Builder.<TombstoneLivingEntity>of(TombstoneLivingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(TombstoneLivingEntity::new)

					.sized(0.625f, 1f));
	public static final RegistryObject<EntityType<OceanizedEndermiteEntity>> OCEANIZED_ENDERMITE = register("oceanized_endermite",
			EntityType.Builder.<OceanizedEndermiteEntity>of(OceanizedEndermiteEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(OceanizedEndermiteEntity::new)

					.sized(0.5f, 0.375f));
	public static final RegistryObject<EntityType<ChieslerPetEntity>> CHIESLER_PET = register("chiesler_pet",
			EntityType.Builder.<ChieslerPetEntity>of(ChieslerPetEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(ChieslerPetEntity::new)

					.sized(0.75f, 0.75f));
	public static final RegistryObject<EntityType<CreeperPetEntity>> CREEPER_PET = register("creeper_pet",
			EntityType.Builder.<CreeperPetEntity>of(CreeperPetEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(CreeperPetEntity::new)

					.sized(0.625f, 1f));
	public static final RegistryObject<EntityType<OceanizedSilverfishEntity>> OCEANIZED_SILVERFISH = register("oceanized_silverfish",
			EntityType.Builder.<OceanizedSilverfishEntity>of(OceanizedSilverfishEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(9).setUpdateInterval(3).setCustomClientFactory(OceanizedSilverfishEntity::new)

					.sized(0.5f, 0.35f));
	public static final RegistryObject<EntityType<IzumilDollEntity>> IZUMIL_DOLL = register("izumil_doll",
			EntityType.Builder.<IzumilDollEntity>of(IzumilDollEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(24).setUpdateInterval(3).setCustomClientFactory(IzumilDollEntity::new)

					.sized(0.75f, 0.8f));
	public static final RegistryObject<EntityType<AegirMortarEntity>> AEGIR_MORTAR = register("aegir_mortar",
			EntityType.Builder.<AegirMortarEntity>of(AegirMortarEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(24).setUpdateInterval(3).setCustomClientFactory(AegirMortarEntity::new)

					.sized(3f, 4f));
	public static final RegistryObject<EntityType<SliderBallEntity>> SLIDER_BALL = register("slider_ball",
			EntityType.Builder.<SliderBallEntity>of(SliderBallEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(24).setUpdateInterval(3).setCustomClientFactory(SliderBallEntity::new)

					.sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<OceanizedLlamaEntity>> OCEANIZED_LLAMA = register("oceanized_llama",
			EntityType.Builder.<OceanizedLlamaEntity>of(OceanizedLlamaEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(9).setUpdateInterval(3).setCustomClientFactory(OceanizedLlamaEntity::new)

					.sized(1f, 1.8f));
	public static final RegistryObject<EntityType<ElectShootEntity>> ELECT_SHOOT = register("elect_shoot",
			EntityType.Builder.<ElectShootEntity>of(ElectShootEntity::new, MobCategory.MISC).setCustomClientFactory(ElectShootEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<NetherseaBoatEntity>> NETHERSEA_BOAT = register("nethersea_boat", EntityType.Builder.<NetherseaBoatEntity>of(NetherseaBoatEntity::new, MobCategory.MISC)
			.setCustomClientFactory(NetherseaBoatEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(1.375f, 0.5625f));
	public static final RegistryObject<EntityType<NetherseaChestBoatEntity>> NETHERSEA_CHEST_BOAT = register("nethersea_chest_boat", EntityType.Builder.<NetherseaChestBoatEntity>of(NetherseaChestBoatEntity::new, MobCategory.MISC)
			.setCustomClientFactory(NetherseaChestBoatEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(1.375f, 0.562f));
	public static final RegistryObject<EntityType<ChitinTridentThrownEntity>> CHITIN_TRIDENT_THROWN = register("chitin_trident_thrown", EntityType.Builder.<ChitinTridentThrownEntity>of(ChitinTridentThrownEntity::new, MobCategory.MISC)
			.setCustomClientFactory(ChitinTridentThrownEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ComplexChitinTridentThrownEntity>> COMPLEX_CHITIN_TRIDENT_THROWN = register("complex_chitin_trident_thrown",
			EntityType.Builder.<ComplexChitinTridentThrownEntity>of(ComplexChitinTridentThrownEntity::new, MobCategory.MISC).setCustomClientFactory(ComplexChitinTridentThrownEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<TrailriteTridentThrownEntity>> TRAILRITE_TRIDENT_THROWN = register("trailrite_trident_thrown", EntityType.Builder.<TrailriteTridentThrownEntity>of(TrailriteTridentThrownEntity::new, MobCategory.MISC)
			.setCustomClientFactory(TrailriteTridentThrownEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<SliderBallHugeEntity>> SLIDER_BALL_HUGE = register("slider_ball_huge",
			EntityType.Builder.<SliderBallHugeEntity>of(SliderBallHugeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(SliderBallHugeEntity::new)

					.sized(2f, 2f));
	public static final RegistryObject<EntityType<IshmaelEntity>> ISHMAEL = register("ishmael",
			EntityType.Builder.<IshmaelEntity>of(IshmaelEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(IshmaelEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<IberiaMonstrosityEntity>> IBERIA_MONSTROSITY = register("iberia_monstrosity",
			EntityType.Builder.<IberiaMonstrosityEntity>of(IberiaMonstrosityEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(24).setUpdateInterval(3).setCustomClientFactory(IberiaMonstrosityEntity::new)

					.sized(3f, 5f));
	public static final RegistryObject<EntityType<MonstrosityRocketEntity>> MONSTROSITY_ROCKET = register("monstrosity_rocket", EntityType.Builder.<MonstrosityRocketEntity>of(MonstrosityRocketEntity::new, MobCategory.MISC)
			.setCustomClientFactory(MonstrosityRocketEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<RaidSignEntity>> RAID_SIGN = register("raid_sign",
			EntityType.Builder.<RaidSignEntity>of(RaidSignEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(RaidSignEntity::new)

					.sized(1f, 2f));
	public static final RegistryObject<EntityType<OceanizedBeeEntity>> OCEANIZED_BEE = register("oceanized_bee",
			EntityType.Builder.<OceanizedBeeEntity>of(OceanizedBeeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(112).setUpdateInterval(3).setCustomClientFactory(OceanizedBeeEntity::new)

					.sized(0.5f, 0.56f));
	public static final RegistryObject<EntityType<BeeStingerEntity>> BEE_STINGER = register("bee_stinger",
			EntityType.Builder.<BeeStingerEntity>of(BeeStingerEntity::new, MobCategory.MISC).setCustomClientFactory(BeeStingerEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.2f, 0.2f));
	public static final RegistryObject<EntityType<OceanWitherSkeletonEntity>> OCEAN_WITHER_SKELETON = register("ocean_wither_skeleton", EntityType.Builder.<OceanWitherSkeletonEntity>of(OceanWitherSkeletonEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(OceanWitherSkeletonEntity::new).fireImmune().sized(0.8f, 2.5f));
	public static final RegistryObject<EntityType<SkeletonSlashEntity>> SKELETON_SLASH = register("skeleton_slash", EntityType.Builder.<SkeletonSlashEntity>of(SkeletonSlashEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(SkeletonSlashEntity::new).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<TiderageDireswineEntity>> TIDERAGE_DIRESWINE = register("tiderage_direswine",
			EntityType.Builder.<TiderageDireswineEntity>of(TiderageDireswineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(12).setUpdateInterval(3).setCustomClientFactory(TiderageDireswineEntity::new)

					.sized(1f, 1.4f));
	public static final RegistryObject<EntityType<LumenEntity>> LUMEN = register("lumen",
			EntityType.Builder.<LumenEntity>of(LumenEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(LumenEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<MegaStonecutterEntity>> MEGA_STONECUTTER = register("mega_stonecutter", EntityType.Builder.<MegaStonecutterEntity>of(MegaStonecutterEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(MegaStonecutterEntity::new).fireImmune().sized(2f, 2f));
	public static final RegistryObject<EntityType<OceanizedHoglinEntity>> OCEANIZED_HOGLIN = register("oceanized_hoglin", EntityType.Builder.<OceanizedHoglinEntity>of(OceanizedHoglinEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(OceanizedHoglinEntity::new).fireImmune().sized(0.6f, 1.6f));
	public static final RegistryObject<EntityType<PromotedGuardianEntity>> PROMOTED_GUARDIAN = register("promoted_guardian",
			EntityType.Builder.<PromotedGuardianEntity>of(PromotedGuardianEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(PromotedGuardianEntity::new)

					.sized(1f, 1f));
	public static final RegistryObject<EntityType<PromotedElderGuardianEntity>> PROMOTED_ELDER_GUARDIAN = register("promoted_elder_guardian",
			EntityType.Builder.<PromotedElderGuardianEntity>of(PromotedElderGuardianEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(PromotedElderGuardianEntity::new)

					.sized(2f, 2f));
	public static final RegistryObject<EntityType<PromotedSquidEntity>> PROMOTED_SQUID = register("promoted_squid",
			EntityType.Builder.<PromotedSquidEntity>of(PromotedSquidEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(24).setUpdateInterval(3).setCustomClientFactory(PromotedSquidEntity::new)

					.sized(0.75f, 0.75f));
	public static final RegistryObject<EntityType<PromotedGlowSquidEntity>> PROMOTED_GLOW_SQUID = register("promoted_glow_squid",
			EntityType.Builder.<PromotedGlowSquidEntity>of(PromotedGlowSquidEntity::new, MobCategory.UNDERGROUND_WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3)
					.setCustomClientFactory(PromotedGlowSquidEntity::new)

					.sized(0.75f, 0.75f));
	public static final RegistryObject<EntityType<PromotedAxolotlEntity>> PROMOTED_AXOLOTL = register("promoted_axolotl",
			EntityType.Builder.<PromotedAxolotlEntity>of(PromotedAxolotlEntity::new, MobCategory.AXOLOTLS).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(PromotedAxolotlEntity::new)

					.sized(0.625f, 0.25f));
	public static final RegistryObject<EntityType<OceanizedWanderingTraderEntity>> OCEANIZED_WANDERING_TRADER = register("oceanized_wandering_trader",
			EntityType.Builder.<OceanizedWanderingTraderEntity>of(OceanizedWanderingTraderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(24).setUpdateInterval(3)
					.setCustomClientFactory(OceanizedWanderingTraderEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<PenalBatallionCrossbowmanEntity>> PENAL_BATALLION_CROSSBOWMAN = register("penal_batallion_crossbowman",
			EntityType.Builder.<PenalBatallionCrossbowmanEntity>of(PenalBatallionCrossbowmanEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(PenalBatallionCrossbowmanEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<LargeArrowEntity>> LARGE_ARROW = register("large_arrow",
			EntityType.Builder.<LargeArrowEntity>of(LargeArrowEntity::new, MobCategory.MISC).setCustomClientFactory(LargeArrowEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<TyphoeusEntity>> TYPHOEUS = register("typhoeus",
			EntityType.Builder.<TyphoeusEntity>of(TyphoeusEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(20).setUpdateInterval(3).setCustomClientFactory(TyphoeusEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<TypheousSignEntity>> TYPHEOUS_SIGN = register("typheous_sign",
			EntityType.Builder.<TypheousSignEntity>of(TypheousSignEntity::new, MobCategory.MISC).setCustomClientFactory(TypheousSignEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<AlchemyBaseEntity>> ALCHEMY_BASE = register("alchemy_base", EntityType.Builder.<AlchemyBaseEntity>of(AlchemyBaseEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AlchemyBaseEntity::new).fireImmune().sized(0.4f, 0.4f));
	public static final RegistryObject<EntityType<VoyageLampEntity>> VOYAGE_LAMP = register("voyage_lamp", EntityType.Builder.<VoyageLampEntity>of(VoyageLampEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18)
			.setUpdateInterval(3).setCustomClientFactory(VoyageLampEntity::new).fireImmune().sized(0.6f, 0.4f));
	public static final RegistryObject<EntityType<TuitionHandbowEntity>> TUITION_HANDBOW = register("tuition_handbow",
			EntityType.Builder.<TuitionHandbowEntity>of(TuitionHandbowEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(24).setUpdateInterval(3).setCustomClientFactory(TuitionHandbowEntity::new)

					.sized(0.7f, 0.5f));
	public static final RegistryObject<EntityType<ShieldOfHonourEntity>> SHIELD_OF_HONOUR = register("shield_of_honour",
			EntityType.Builder.<ShieldOfHonourEntity>of(ShieldOfHonourEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ShieldOfHonourEntity::new)

					.sized(1.3f, 2f));
	public static final RegistryObject<EntityType<UsherVaseEntity>> USHER_VASE = register("usher_vase",
			EntityType.Builder.<UsherVaseEntity>of(UsherVaseEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(UsherVaseEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ConfessionCenserEntity>> CONFESSION_CENSER = register("confession_censer",
			EntityType.Builder.<ConfessionCenserEntity>of(ConfessionCenserEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(ConfessionCenserEntity::new)

					.sized(0.8f, 2f));
	public static final RegistryObject<EntityType<HollowArmorEntity>> HOLLOW_ARMOR = register("hollow_armor",
			EntityType.Builder.<HollowArmorEntity>of(HollowArmorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(HollowArmorEntity::new)

					.sized(1f, 2.6f));
	public static final RegistryObject<EntityType<ChemistWashbasinEntity>> CHEMIST_WASHBASIN = register("chemist_washbasin",
			EntityType.Builder.<ChemistWashbasinEntity>of(ChemistWashbasinEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(ChemistWashbasinEntity::new)

					.sized(1f, 1.8f));
	public static final RegistryObject<EntityType<NoviceAlchemistEntity>> NOVICE_ALCHEMIST = register("novice_alchemist",
			EntityType.Builder.<NoviceAlchemistEntity>of(NoviceAlchemistEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(NoviceAlchemistEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<PumpkinCarriageEntity>> PUMPKIN_CARRIAGE = register("pumpkin_carriage",
			EntityType.Builder.<PumpkinCarriageEntity>of(PumpkinCarriageEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(PumpkinCarriageEntity::new)

					.sized(1f, 1.125f));
	public static final RegistryObject<EntityType<ShakingBellEntity>> SHAKING_BELL = register("shaking_bell",
			EntityType.Builder.<ShakingBellEntity>of(ShakingBellEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(ShakingBellEntity::new)

					.sized(0.4f, 1f));
	public static final RegistryObject<EntityType<YirahelBeaconEntity>> YIRAHEL_BEACON = register("yirahel_beacon",
			EntityType.Builder.<YirahelBeaconEntity>of(YirahelBeaconEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(18).setUpdateInterval(3).setCustomClientFactory(YirahelBeaconEntity::new)

					.sized(1.1f, 1.8f));
	public static final RegistryObject<EntityType<BeaconSelectorEntity>> BEACON_SELECTOR = register("beacon_selector",
			EntityType.Builder.<BeaconSelectorEntity>of(BeaconSelectorEntity::new, MobCategory.MISC).setCustomClientFactory(BeaconSelectorEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<BeaconSignEntity>> BEACON_SIGN = register("beacon_sign",
			EntityType.Builder.<BeaconSignEntity>of(BeaconSignEntity::new, MobCategory.MISC).setCustomClientFactory(BeaconSignEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<SaintValerioEntity>> SAINT_VALERIO = register("saint_valerio",
			EntityType.Builder.<SaintValerioEntity>of(SaintValerioEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(19).setUpdateInterval(3).setCustomClientFactory(SaintValerioEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<UnquenchableFireEntity>> UNQUENCHABLE_FIRE = register("unquenchable_fire",
			EntityType.Builder.<UnquenchableFireEntity>of(UnquenchableFireEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(16).setUpdateInterval(3).setCustomClientFactory(UnquenchableFireEntity::new)

					.sized(0.6f, 0.8f));
	public static final RegistryObject<EntityType<InquisitionFireEntity>> INQUISITION_FIRE = register("inquisition_fire", EntityType.Builder.<InquisitionFireEntity>of(InquisitionFireEntity::new, MobCategory.MISC)
			.setCustomClientFactory(InquisitionFireEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(4f, 0.5f));

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			RunFishEntity.init();
			SliderFishEntity.init();
			SuperSliderEntity.init();
			ShooterFishEntity.init();
			FlyFishEntity.init();
			ReaperFishEntity.init();
			CreeperFishEntity.init();
			PunctureFishEntity.init();
			BaselayerAbyssalEntity.init();
			PredatorAbyssalEntity.init();
			GuideAbyssalEntity.init();
			SplasherAbyssalEntity.init();
			UmbrellaAbyssalEntity.init();
			CrackerAbyssalEntity.init();
			CollectorProkaryoteEntity.init();
			BoneFishEntity.init();
			ChiselerFishEntity.init();
			PregnantFishEntity.init();
			FakeOffspringEntity.init();
			FleeFishEntity.init();
			RouteShaperEntity.init();
			RouteFractalEntity.init();
			FirstTellerEntity.init();
			ReaperPetEntity.init();
			BishopFishEntity.init();
			TideBishopEntity.init();
			SonsEntity.init();
			FloaterProkaryoteEntity.init();
			ChitinGolemEntity.init();
			TideDeathrepellerEntity.init();
			MegaChestEntity.init();
			ApostleProkaryoteEntity.init();
			HighmoreEntity.init();
			AccumulatorProkaryoteEntity.init();
			FeederProkaryoteEntity.init();
			ChestFishEntity.init();
			SpikeChestEntity.init();
			SkadiEntity.init();
			DepositerProkaryoteEntity.init();
			OceanizedVillagerEntity.init();
			OceanizedVindicatorEntity.init();
			OceanizedPillagerEntity.init();
			OceanizedPigEntity.init();
			OceanizedCowEntity.init();
			OceanizedSheepEntity.init();
			OceanizedHorseEntity.init();
			OceanizedPiglinEntity.init();
			OceanizedBruteEntity.init();
			OceanizedSpiderEntity.init();
			OceanizedEndermanEntity.init();
			OceanizedWolfEntity.init();
			OceanizedDogEntity.init();
			OceanizedRavagerEntity.init();
			OceanziedWitchEntity.init();
			IzumikOffspringEntity.init();
			IzumikEntity.init();
			DivicellularGoEntity.init();
			OceanizedEvokerEntity.init();
			JuniorWarriorPriestEntity.init();
			WarriorPriestEntity.init();
			CorrectionalPhalanxyInfantryEntity.init();
			CorrectinalPhalaxVanguardEntity.init();
			TribunalHealerEntity.init();
			MartusEntity.init();
			TheAbandonedEntity.init();
			GunmuEntity.init();
			OceanizedWardenEntity.init();
			OceanizedCatEntity.init();
			SuperBigCatEntity.init();
			ComplexChitinGolemEntity.init();
			OceanizedWardenisEntity.init();
			NucleicMaleficentEntity.init();
			OceanizedWitherEntity.init();
			OceannizedWitheriaEntity.init();
			TheLastKnightEntity.init();
			LastKnightAndHorseEntity.init();
			RocinanteEntity.init();
			ApocataEntity.init();
			OceanizedFoxEntity.init();
			TidutantExcrescenceEntity.init();
			OceanizedPolarBearEntity.init();
			TideutantRockSpiderEntity.init();
			Endspeaker0Entity.init();
			Endspeaker1Entity.init();
			Endspeaker2Entity.init();
			Endspeaker3Entity.init();
			LineringPathshaperEntity.init();
			LingeringFractalEntity.init();
			LittleHelperEntity.init();
			Al1SHelperEntity.init();
			DamageTesterEntity.init();
			UlpiansEntity.init();
			GladiiaEntity.init();
			GladiiaWhirlEntity.init();
			SpecterEntity.init();
			SpecterDollEntity.init();
			IreneEntity.init();
			TideChimeraEntity.init();
			SkadiCorruptedEntity.init();
			OceanizeRabbitEntity.init();
			SaintCarmenEntity.init();
			OceanizedIllusionerEntity.init();
			OceanIllusionEntity.init();
			FlamarineStatueEntity.init();
			NautilusHeadhunterEntity.init();
			XantisEntity.init();
			FlamarineGolemEntity.init();
			OceanizedVexEntity.init();
			IsharmlaEntity.init();
			QunyouWantedIsharmlaEntity.init();
			IsharmlaTearEntity.init();
			CompassionPrayerEntity.init();
			OceanizedEnderinaEntity.init();
			MoistDragonBreathEntity.init();
			MoistEnderCrystalEntity.init();
			ThirsterEntity.init();
			AbsorberLimbEntity.init();
			ScreamChestFishEntity.init();
			OceanizedChickenEntity.init();
			NetherseaSlimeEntity.init();
			OceanizedShulkerEntity.init();
			OceanizedSnifferEntity.init();
			OceanizedSnifferEggEntity.init();
			CaerulaOffspringEntity.init();
			RunvolutionEntity.init();
			RunvolutionWaveEntity.init();
			TombstoneUndertidesEntity.init();
			TombstoneUndertideSmallEntity.init();
			TombstoneStultiferaEntity.init();
			TombstoneStultiferaSmallEntity.init();
			TombstoneLivingEntity.init();
			OceanizedEndermiteEntity.init();
			ChieslerPetEntity.init();
			CreeperPetEntity.init();
			OceanizedSilverfishEntity.init();
			IzumilDollEntity.init();
			AegirMortarEntity.init();
			SliderBallEntity.init();
			OceanizedLlamaEntity.init();
			SliderBallHugeEntity.init();
			IshmaelEntity.init();
			IberiaMonstrosityEntity.init();
			RaidSignEntity.init();
			OceanizedBeeEntity.init();
			OceanWitherSkeletonEntity.init();
			SkeletonSlashEntity.init();
			TiderageDireswineEntity.init();
			LumenEntity.init();
			MegaStonecutterEntity.init();
			OceanizedHoglinEntity.init();
			PromotedGuardianEntity.init();
			PromotedElderGuardianEntity.init();
			PromotedSquidEntity.init();
			PromotedGlowSquidEntity.init();
			PromotedAxolotlEntity.init();
			OceanizedWanderingTraderEntity.init();
			PenalBatallionCrossbowmanEntity.init();
			TyphoeusEntity.init();
			AlchemyBaseEntity.init();
			VoyageLampEntity.init();
			TuitionHandbowEntity.init();
			ShieldOfHonourEntity.init();
			UsherVaseEntity.init();
			ConfessionCenserEntity.init();
			HollowArmorEntity.init();
			ChemistWashbasinEntity.init();
			NoviceAlchemistEntity.init();
			PumpkinCarriageEntity.init();
			ShakingBellEntity.init();
			YirahelBeaconEntity.init();
			SaintValerioEntity.init();
			UnquenchableFireEntity.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(RUN_FISH.get(), RunFishEntity.createAttributes().build());
		event.put(SLIDER_FISH.get(), SliderFishEntity.createAttributes().build());
		event.put(SUPER_SLIDER.get(), SuperSliderEntity.createAttributes().build());
		event.put(SHOOTER_FISH.get(), ShooterFishEntity.createAttributes().build());
		event.put(FLY_FISH.get(), FlyFishEntity.createAttributes().build());
		event.put(REAPER_FISH.get(), ReaperFishEntity.createAttributes().build());
		event.put(CREEPER_FISH.get(), CreeperFishEntity.createAttributes().build());
		event.put(PUNCTURE_FISH.get(), PunctureFishEntity.createAttributes().build());
		event.put(BASELAYER_ABYSSAL.get(), BaselayerAbyssalEntity.createAttributes().build());
		event.put(PREDATOR_ABYSSAL.get(), PredatorAbyssalEntity.createAttributes().build());
		event.put(GUIDE_ABYSSAL.get(), GuideAbyssalEntity.createAttributes().build());
		event.put(SPLASHER_ABYSSAL.get(), SplasherAbyssalEntity.createAttributes().build());
		event.put(UMBRELLA_ABYSSAL.get(), UmbrellaAbyssalEntity.createAttributes().build());
		event.put(CRACKER_ABYSSAL.get(), CrackerAbyssalEntity.createAttributes().build());
		event.put(COLLECTOR_PROKARYOTE.get(), CollectorProkaryoteEntity.createAttributes().build());
		event.put(BONE_FISH.get(), BoneFishEntity.createAttributes().build());
		event.put(CHISELER_FISH.get(), ChiselerFishEntity.createAttributes().build());
		event.put(PREGNANT_FISH.get(), PregnantFishEntity.createAttributes().build());
		event.put(FAKE_OFFSPRING.get(), FakeOffspringEntity.createAttributes().build());
		event.put(FLEE_FISH.get(), FleeFishEntity.createAttributes().build());
		event.put(ROUTE_SHAPER.get(), RouteShaperEntity.createAttributes().build());
		event.put(ROUTE_FRACTAL.get(), RouteFractalEntity.createAttributes().build());
		event.put(FIRST_TO_TALK.get(), FirstTellerEntity.createAttributes().build());
		event.put(REAPER_PET.get(), ReaperPetEntity.createAttributes().build());
		event.put(BISHOP_FISH.get(), BishopFishEntity.createAttributes().build());
		event.put(TIDE_BISHOP.get(), TideBishopEntity.createAttributes().build());
		event.put(SONS.get(), SonsEntity.createAttributes().build());
		event.put(FLOATER_PROKARYOTE.get(), FloaterProkaryoteEntity.createAttributes().build());
		event.put(CHITIN_GOLEM.get(), ChitinGolemEntity.createAttributes().build());
		event.put(TIDE_DEATHREPELLER.get(), TideDeathrepellerEntity.createAttributes().build());
		event.put(MEGA_CHEST.get(), MegaChestEntity.createAttributes().build());
		event.put(APOSTLE_PROKARYOTE.get(), ApostleProkaryoteEntity.createAttributes().build());
		event.put(HIGHMORE.get(), HighmoreEntity.createAttributes().build());
		event.put(ACCUMULATOR_PROKARYOTE.get(), AccumulatorProkaryoteEntity.createAttributes().build());
		event.put(FEEDER_PROKARYOTE.get(), FeederProkaryoteEntity.createAttributes().build());
		event.put(CHEST_FISH.get(), ChestFishEntity.createAttributes().build());
		event.put(SPIKE_CHEST.get(), SpikeChestEntity.createAttributes().build());
		event.put(SKADI.get(), SkadiEntity.createAttributes().build());
		event.put(DEPOSITER_PROKARYOTE.get(), DepositerProkaryoteEntity.createAttributes().build());
		event.put(OCEANIZED_VILLAGER.get(), OceanizedVillagerEntity.createAttributes().build());
		event.put(OCEANIZED_VINDICATOR.get(), OceanizedVindicatorEntity.createAttributes().build());
		event.put(OCEANIZED_PILLAGER.get(), OceanizedPillagerEntity.createAttributes().build());
		event.put(OCEANIZED_PIG.get(), OceanizedPigEntity.createAttributes().build());
		event.put(OCEANIZED_COW.get(), OceanizedCowEntity.createAttributes().build());
		event.put(OCEANIZED_SHEEP.get(), OceanizedSheepEntity.createAttributes().build());
		event.put(OCEANIZED_HORSE.get(), OceanizedHorseEntity.createAttributes().build());
		event.put(OCEANIZED_PIGLIN.get(), OceanizedPiglinEntity.createAttributes().build());
		event.put(OCEANIZED_BRUTE.get(), OceanizedBruteEntity.createAttributes().build());
		event.put(OCEANIZED_SPIDER.get(), OceanizedSpiderEntity.createAttributes().build());
		event.put(OCEANIZED_ENDERMAN.get(), OceanizedEndermanEntity.createAttributes().build());
		event.put(OCEANIZED_WOLF.get(), OceanizedWolfEntity.createAttributes().build());
		event.put(OCEANIZED_DOG.get(), OceanizedDogEntity.createAttributes().build());
		event.put(OCEANIZED_RAVAGER.get(), OceanizedRavagerEntity.createAttributes().build());
		event.put(OCEANIZED_WITCH.get(), OceanziedWitchEntity.createAttributes().build());
		event.put(IZUMIK_OFFSPRING.get(), IzumikOffspringEntity.createAttributes().build());
		event.put(IZUMIK.get(), IzumikEntity.createAttributes().build());
		event.put(DIVICELLULAR_GO.get(), DivicellularGoEntity.createAttributes().build());
		event.put(OCEANIZED_EVOKER.get(), OceanizedEvokerEntity.createAttributes().build());
		event.put(JUNIOR_WARRIOR_PRIEST.get(), JuniorWarriorPriestEntity.createAttributes().build());
		event.put(WARRIOR_PRIEST.get(), WarriorPriestEntity.createAttributes().build());
		event.put(CORRECTIONAL_PHALANXY_INFANTRY.get(), CorrectionalPhalanxyInfantryEntity.createAttributes().build());
		event.put(CORRECTIONAL_PHALAX_VANGUARD.get(), CorrectinalPhalaxVanguardEntity.createAttributes().build());
		event.put(TRIBUNAL_HEALER.get(), TribunalHealerEntity.createAttributes().build());
		event.put(MARTUS.get(), MartusEntity.createAttributes().build());
		event.put(THE_ABANDONED.get(), TheAbandonedEntity.createAttributes().build());
		event.put(GUNMU.get(), GunmuEntity.createAttributes().build());
		event.put(OCEANIZED_WARDEN.get(), OceanizedWardenEntity.createAttributes().build());
		event.put(OCEANIZED_CAT.get(), OceanizedCatEntity.createAttributes().build());
		event.put(SUPER_BIG_CAT.get(), SuperBigCatEntity.createAttributes().build());
		event.put(COMPLEX_CHITIN_GOLEM.get(), ComplexChitinGolemEntity.createAttributes().build());
		event.put(OCEANIZED_WARDENIS.get(), OceanizedWardenisEntity.createAttributes().build());
		event.put(NUCLEIC_MALEFICENT.get(), NucleicMaleficentEntity.createAttributes().build());
		event.put(OCEANIZED_WITHER.get(), OceanizedWitherEntity.createAttributes().build());
		event.put(OCEANIZED_WITHERIA.get(), OceannizedWitheriaEntity.createAttributes().build());
		event.put(THE_LAST_KNIGHT.get(), TheLastKnightEntity.createAttributes().build());
		event.put(LAST_KNIGHT_AND_HORSE.get(), LastKnightAndHorseEntity.createAttributes().build());
		event.put(ROCINANTE.get(), RocinanteEntity.createAttributes().build());
		event.put(APOCATA.get(), ApocataEntity.createAttributes().build());
		event.put(OCEANIZED_FOX.get(), OceanizedFoxEntity.createAttributes().build());
		event.put(TIDUTANT_EXCRESCENCE.get(), TidutantExcrescenceEntity.createAttributes().build());
		event.put(OCEANIZED_POLAR_BEAR.get(), OceanizedPolarBearEntity.createAttributes().build());
		event.put(TIDUTANT_ROCK_SPIDER.get(), TideutantRockSpiderEntity.createAttributes().build());
		event.put(ENDSPEAKER_0.get(), Endspeaker0Entity.createAttributes().build());
		event.put(ENDSPEAKER_1.get(), Endspeaker1Entity.createAttributes().build());
		event.put(ENDSPEAKER_2.get(), Endspeaker2Entity.createAttributes().build());
		event.put(ENDSPEAKER_3.get(), Endspeaker3Entity.createAttributes().build());
		event.put(LINGERING_PATHSHAPER.get(), LineringPathshaperEntity.createAttributes().build());
		event.put(LINGERING_FRACTAL.get(), LingeringFractalEntity.createAttributes().build());
		event.put(LITTLE_HELPER.get(), LittleHelperEntity.createAttributes().build());
		event.put(AL_1_S_HELPER.get(), Al1SHelperEntity.createAttributes().build());
		event.put(DAMAGE_TESTER.get(), DamageTesterEntity.createAttributes().build());
		event.put(ULPIANS.get(), UlpiansEntity.createAttributes().build());
		event.put(GLADIIA.get(), GladiiaEntity.createAttributes().build());
		event.put(GLADIIA_WHIRL.get(), GladiiaWhirlEntity.createAttributes().build());
		event.put(SPECTER.get(), SpecterEntity.createAttributes().build());
		event.put(SPECTER_DOLL.get(), SpecterDollEntity.createAttributes().build());
		event.put(IRENE.get(), IreneEntity.createAttributes().build());
		event.put(TIDE_CHIMERA.get(), TideChimeraEntity.createAttributes().build());
		event.put(SKADI_CORRUPTED.get(), SkadiCorruptedEntity.createAttributes().build());
		event.put(OCEANIZE_RABBIT.get(), OceanizeRabbitEntity.createAttributes().build());
		event.put(SAINT_CARMEN.get(), SaintCarmenEntity.createAttributes().build());
		event.put(OCEANIZED_ILLUSIONER.get(), OceanizedIllusionerEntity.createAttributes().build());
		event.put(OCEAN_ILLUSION.get(), OceanIllusionEntity.createAttributes().build());
		event.put(FLAMARINE_STATUE.get(), FlamarineStatueEntity.createAttributes().build());
		event.put(NAUTILUS_HEADHUNTER.get(), NautilusHeadhunterEntity.createAttributes().build());
		event.put(XANTIS.get(), XantisEntity.createAttributes().build());
		event.put(FLAMARINE_GOLEM.get(), FlamarineGolemEntity.createAttributes().build());
		event.put(OCEANIZED_VEX.get(), OceanizedVexEntity.createAttributes().build());
		event.put(ISHARMLA.get(), IsharmlaEntity.createAttributes().build());
		event.put(QUNYOU_WANTED_ISHARMLA.get(), QunyouWantedIsharmlaEntity.createAttributes().build());
		event.put(ISHARMLA_TEAR.get(), IsharmlaTearEntity.createAttributes().build());
		event.put(COMPASSION_PRAYER.get(), CompassionPrayerEntity.createAttributes().build());
		event.put(OCEANIZED_ENDERINA.get(), OceanizedEnderinaEntity.createAttributes().build());
		event.put(MOIST_DRAGON_BREATH.get(), MoistDragonBreathEntity.createAttributes().build());
		event.put(MOIST_ENDER_CRYSTAL.get(), MoistEnderCrystalEntity.createAttributes().build());
		event.put(THIRSTER.get(), ThirsterEntity.createAttributes().build());
		event.put(ABSORBER_LIMB.get(), AbsorberLimbEntity.createAttributes().build());
		event.put(SCREAM_CHEST_FISH.get(), ScreamChestFishEntity.createAttributes().build());
		event.put(OCEANIZED_CHICKEN.get(), OceanizedChickenEntity.createAttributes().build());
		event.put(NETHERSEA_SLIME.get(), NetherseaSlimeEntity.createAttributes().build());
		event.put(OCEANIZED_SHULKER.get(), OceanizedShulkerEntity.createAttributes().build());
		event.put(OCEANIZED_SNIFFER.get(), OceanizedSnifferEntity.createAttributes().build());
		event.put(OCEANIZED_SNIFFER_EGG.get(), OceanizedSnifferEggEntity.createAttributes().build());
		event.put(CAERULA_OFFSPRING.get(), CaerulaOffspringEntity.createAttributes().build());
		event.put(RUNVOLUTION.get(), RunvolutionEntity.createAttributes().build());
		event.put(RUNVOLUTION_WAVE.get(), RunvolutionWaveEntity.createAttributes().build());
		event.put(TOMBSTONE_UNDERTIDES.get(), TombstoneUndertidesEntity.createAttributes().build());
		event.put(TOMBSTONE_UNDERTIDE_SMALL.get(), TombstoneUndertideSmallEntity.createAttributes().build());
		event.put(TOMBSTONE_STULTIFERA.get(), TombstoneStultiferaEntity.createAttributes().build());
		event.put(TOMBSTONE_STULTIFERA_SMALL.get(), TombstoneStultiferaSmallEntity.createAttributes().build());
		event.put(TOMBSTONE_LIVING.get(), TombstoneLivingEntity.createAttributes().build());
		event.put(OCEANIZED_ENDERMITE.get(), OceanizedEndermiteEntity.createAttributes().build());
		event.put(CHIESLER_PET.get(), ChieslerPetEntity.createAttributes().build());
		event.put(CREEPER_PET.get(), CreeperPetEntity.createAttributes().build());
		event.put(OCEANIZED_SILVERFISH.get(), OceanizedSilverfishEntity.createAttributes().build());
		event.put(IZUMIL_DOLL.get(), IzumilDollEntity.createAttributes().build());
		event.put(AEGIR_MORTAR.get(), AegirMortarEntity.createAttributes().build());
		event.put(SLIDER_BALL.get(), SliderBallEntity.createAttributes().build());
		event.put(OCEANIZED_LLAMA.get(), OceanizedLlamaEntity.createAttributes().build());
		event.put(SLIDER_BALL_HUGE.get(), SliderBallHugeEntity.createAttributes().build());
		event.put(ISHMAEL.get(), IshmaelEntity.createAttributes().build());
		event.put(IBERIA_MONSTROSITY.get(), IberiaMonstrosityEntity.createAttributes().build());
		event.put(RAID_SIGN.get(), RaidSignEntity.createAttributes().build());
		event.put(OCEANIZED_BEE.get(), OceanizedBeeEntity.createAttributes().build());
		event.put(OCEAN_WITHER_SKELETON.get(), OceanWitherSkeletonEntity.createAttributes().build());
		event.put(SKELETON_SLASH.get(), SkeletonSlashEntity.createAttributes().build());
		event.put(TIDERAGE_DIRESWINE.get(), TiderageDireswineEntity.createAttributes().build());
		event.put(LUMEN.get(), LumenEntity.createAttributes().build());
		event.put(MEGA_STONECUTTER.get(), MegaStonecutterEntity.createAttributes().build());
		event.put(OCEANIZED_HOGLIN.get(), OceanizedHoglinEntity.createAttributes().build());
		event.put(PROMOTED_GUARDIAN.get(), PromotedGuardianEntity.createAttributes().build());
		event.put(PROMOTED_ELDER_GUARDIAN.get(), PromotedElderGuardianEntity.createAttributes().build());
		event.put(PROMOTED_SQUID.get(), PromotedSquidEntity.createAttributes().build());
		event.put(PROMOTED_GLOW_SQUID.get(), PromotedGlowSquidEntity.createAttributes().build());
		event.put(PROMOTED_AXOLOTL.get(), PromotedAxolotlEntity.createAttributes().build());
		event.put(OCEANIZED_WANDERING_TRADER.get(), OceanizedWanderingTraderEntity.createAttributes().build());
		event.put(PENAL_BATALLION_CROSSBOWMAN.get(), PenalBatallionCrossbowmanEntity.createAttributes().build());
		event.put(TYPHOEUS.get(), TyphoeusEntity.createAttributes().build());
		event.put(ALCHEMY_BASE.get(), AlchemyBaseEntity.createAttributes().build());
		event.put(VOYAGE_LAMP.get(), VoyageLampEntity.createAttributes().build());
		event.put(TUITION_HANDBOW.get(), TuitionHandbowEntity.createAttributes().build());
		event.put(SHIELD_OF_HONOUR.get(), ShieldOfHonourEntity.createAttributes().build());
		event.put(USHER_VASE.get(), UsherVaseEntity.createAttributes().build());
		event.put(CONFESSION_CENSER.get(), ConfessionCenserEntity.createAttributes().build());
		event.put(HOLLOW_ARMOR.get(), HollowArmorEntity.createAttributes().build());
		event.put(CHEMIST_WASHBASIN.get(), ChemistWashbasinEntity.createAttributes().build());
		event.put(NOVICE_ALCHEMIST.get(), NoviceAlchemistEntity.createAttributes().build());
		event.put(PUMPKIN_CARRIAGE.get(), PumpkinCarriageEntity.createAttributes().build());
		event.put(SHAKING_BELL.get(), ShakingBellEntity.createAttributes().build());
		event.put(YIRAHEL_BEACON.get(), YirahelBeaconEntity.createAttributes().build());
		event.put(SAINT_VALERIO.get(), SaintValerioEntity.createAttributes().build());
		event.put(UNQUENCHABLE_FIRE.get(), UnquenchableFireEntity.createAttributes().build());
	}
}
