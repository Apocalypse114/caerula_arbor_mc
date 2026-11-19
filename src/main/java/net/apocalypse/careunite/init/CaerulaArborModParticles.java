
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.apocalypse.careunite.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.apocalypse.careunite.client.particle.TrappedParticleParticle;
import net.apocalypse.careunite.client.particle.SpecterGlitterParticle;
import net.apocalypse.careunite.client.particle.SpecterCharsParticle;
import net.apocalypse.careunite.client.particle.ShieldlossParticle;
import net.apocalypse.careunite.client.particle.SeordsIconParticle;
import net.apocalypse.careunite.client.particle.SeaSplashParticle;
import net.apocalypse.careunite.client.particle.PurpleFlameParticle;
import net.apocalypse.careunite.client.particle.PresaintHitParticle;
import net.apocalypse.careunite.client.particle.NumbnessParticle;
import net.apocalypse.careunite.client.particle.NervousImpairmentParticle;
import net.apocalypse.careunite.client.particle.MutenessParticle;
import net.apocalypse.careunite.client.particle.MoistBoomParticle;
import net.apocalypse.careunite.client.particle.MissParticle;
import net.apocalypse.careunite.client.particle.MartusCharsParticle;
import net.apocalypse.careunite.client.particle.LumenIconParticle;
import net.apocalypse.careunite.client.particle.LivingBarrierShowParticle;
import net.apocalypse.careunite.client.particle.LifelossParticle;
import net.apocalypse.careunite.client.particle.KnifeptcParticle;
import net.apocalypse.careunite.client.particle.KingSlayRedParticle;
import net.apocalypse.careunite.client.particle.KingSlayParticle;
import net.apocalypse.careunite.client.particle.IsharmlaCurseParticleParticle;
import net.apocalypse.careunite.client.particle.InvPtcVoiletParticle;
import net.apocalypse.careunite.client.particle.InvPtcParticle;
import net.apocalypse.careunite.client.particle.InvPtcBlueParticle;
import net.apocalypse.careunite.client.particle.ImmortalPtcParticle;
import net.apocalypse.careunite.client.particle.IceFlakeParticle;
import net.apocalypse.careunite.client.particle.EwFlameParticle;
import net.apocalypse.careunite.client.particle.EndspeakerParticleParticle;
import net.apocalypse.careunite.client.particle.EndspeakerInvParticle;
import net.apocalypse.careunite.client.particle.ElectSparkParticle;
import net.apocalypse.careunite.client.particle.EdermanPtcParticle;
import net.apocalypse.careunite.client.particle.DizzinessParticle;
import net.apocalypse.careunite.client.particle.CrackerBuff1Particle;
import net.apocalypse.careunite.client.particle.CrackerBuff0Particle;
import net.apocalypse.careunite.client.particle.CorruptedFishParticle;
import net.apocalypse.careunite.client.particle.BulletsParticle;
import net.apocalypse.careunite.client.particle.BowIconParticle;
import net.apocalypse.careunite.client.particle.BloodoozeParticle;
import net.apocalypse.careunite.client.particle.ArchfiendResevParticle;
import net.apocalypse.careunite.client.particle.ArchfiendKeepParticle;
import net.apocalypse.careunite.client.particle.AlchemyParticleParticle;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CaerulaArborModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(CaerulaArborModParticleTypes.LIFELOSS.get(), LifelossParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.SHIELDLOSS.get(), ShieldlossParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.BLOODOOZE.get(), BloodoozeParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.KING_SLAY.get(), KingSlayParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.KING_SLAY_RED.get(), KingSlayRedParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.ARCHFIEND_KEEP.get(), ArchfiendKeepParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.ARCHFIEND_RESEV.get(), ArchfiendResevParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.DIZZINESS.get(), DizzinessParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.KNIFEPTC.get(), KnifeptcParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.MISS.get(), MissParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.CRACKER_BUFF_0.get(), CrackerBuff0Particle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.CRACKER_BUFF_1.get(), CrackerBuff1Particle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.INV_PTC.get(), InvPtcParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.MUTENESS.get(), MutenessParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.INV_PTC_BLUE.get(), InvPtcBlueParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.INV_PTC_VOILET.get(), InvPtcVoiletParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.PURPLE_FLAME.get(), PurpleFlameParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.EDERMAN_PTC.get(), EdermanPtcParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.NUMBNESS.get(), NumbnessParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.MARTUS_CHARS.get(), MartusCharsParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.ENDSPEAKER_PARTICLE.get(), EndspeakerParticleParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.ENDSPEAKER_INV.get(), EndspeakerInvParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.SEA_SPLASH.get(), SeaSplashParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.SPECTER_GLITTER.get(), SpecterGlitterParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.SPECTER_CHARS.get(), SpecterCharsParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.IMMORTAL_PTC.get(), ImmortalPtcParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.CORRUPTED_FISH.get(), CorruptedFishParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.ISHARMLA_CURSE_PARTICLE.get(), IsharmlaCurseParticleParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.BULLETS.get(), BulletsParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.MOIST_BOOM.get(), MoistBoomParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.LIVING_BARRIER_SHOW.get(), LivingBarrierShowParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.BOW_ICON.get(), BowIconParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.SEORDS_ICON.get(), SeordsIconParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get(), NervousImpairmentParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.EW_FLAME.get(), EwFlameParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.ELECT_SPARK.get(), ElectSparkParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.TRAPPED_PARTICLE.get(), TrappedParticleParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.LUMEN_ICON.get(), LumenIconParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.ICE_FLAKE.get(), IceFlakeParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.ALCHEMY_PARTICLE.get(), AlchemyParticleParticle::provider);
		event.registerSpriteSet(CaerulaArborModParticleTypes.PRESAINT_HIT.get(), PresaintHitParticle::provider);
	}
}
