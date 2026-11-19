package net.apocalypse.careunite.procedures;


import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.CaerulaArborMod;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.TribunalHealerEntity;
import net.apocalypse.careunite.entity.OceanizedLlamaEntity;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.Seaborn;
import java.util.function.Predicate;
import java.util.Set;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.Mob;

public class TransformIndexProcedure {

	public static final TagKey<EntityType<?>> NO_TRANSFORM = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:cannot_transform"));
	public static final TagKey<EntityType<?>> BOSSES = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:bosses"));
	public static final TagKey<EntityType<?>> HUMAN = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:homo_sapiens"));
	
	private static final Set<Pair<Predicate<LivingEntity>, EntityType<?>>> INDEX = new HashSet<>();
	private static final Map<EntityType<?>, Float> PERCENTAGE_MAP = new HashMap<>();
	private static final Map<EntityType<?>, EntityType<?>> CACHE = new HashMap<>();

    public static void addTransformIndex(Predicate<LivingEntity> predicate, EntityType<?> result, float percentage){
        INDEX.add(Pair.of(predicate, result));
        PERCENTAGE_MAP.put(result, percentage);
    }

    public static EntityType<?> tryExecute(LivingEntity entityIn){
        Predicate<LivingEntity> p;
        EntityType<?> t = null;
        if (CACHE.containsKey(entityIn.getType())) return CACHE.get(entityIn.getType());
        for (Pair<Predicate<LivingEntity>, EntityType<?>> pair: INDEX){
            p = pair.getFirst();
            t = pair.getSecond();
            if (p.test(entityIn)) {
            	if (t == CaerulaArborModEntities.OCEANIZED_WARDEN.get() && entityIn.getRandom().nextFloat() < 0.02) return CaerulaArborModEntities.OCEANIZED_WARDENIS.get();
            	return pair.getSecond();
            }
        }
        return null;
    }

    public static float getPossibility(EntityType<?> type){
    	if (PERCENTAGE_MAP.containsKey(type)) return PERCENTAGE_MAP.get(type);
    	return 0;
    }

    public static boolean shouldLog(){
		return CaerulaConfigsConfiguration.DEBUG.get();
	}

	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (INDEX.isEmpty()) putDefaultIndex();
		
		if (entity == null) return false;
		if (entity instanceof Player) return false;
		if ((ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("touhou_little_maid:maid")) return false;
		if (entity.getType().is(NO_TRANSFORM)) return false;
		if (!world.getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.OCEANIZATION_MODE)) return false;

		if (entity instanceof LivingEntity living && world instanceof ServerLevel _level){
			if (GetSeabornAroundProcedure.execute(world, x, y, z, entity) >= Math.min((world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT)), (double) CaerulaConfigsConfiguration.CLONE_NUM.get()) * 2) {
				return false;
			}
			EntityType<?> toSpawn = tryExecute(living);
			
			if ((toSpawn == null && living.getMobType() == MobType.UNDEAD) || living.isBaby()) return false;
			
			float f = world.getRandom().nextFloat();
			
			if (toSpawn != null) {
				CACHE.put(entity.getType(), toSpawn);
				if (f < getPossibility(toSpawn)){
					Entity entityToSpawn = toSpawn.spawn(_level, entity.blockPosition(), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn instanceof Mob) {
						Mob liv = (Mob) entityToSpawn;
						entityToSpawn.setYRot(entity.getYRot());
						entityToSpawn.setYBodyRot(entity.getYRot());
						entityToSpawn.setYHeadRot(entity.getYRot());
						entityToSpawn.setXRot(entity.getXRot());
						if (entityToSpawn instanceof Seaborn seaborn){
							if(shouldLog()) CaerulaArborMod.LOGGER.info("Recording converted seaborn: {} from ", 
								entityToSpawn.getDisplayName().getString(), 
								entity.getDisplayName().getString());
							seaborn.setOriginalEntity(living);
							liv.setPersistenceRequired();
						}
					}
					_level.playSound(null, entity.blockPosition(), SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.HOSTILE, 1, 1);
					_level.sendParticles(ParticleTypes.EXPLOSION, x, (y + 0.2), z, 2, 0.1, 0.1, 0.1, 0.15);
					return true;
				}
			}
 else if (world.getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.EXTERNAL_OCEANIZATION)){
				if (f < 0.25f) {
					float rate = 0.15f;
					double h = CaerulaConfigsConfiguration.OCEANIZE_HEALTH.get();
					if (living.getMaxHealth() < h) rate = 0;
					if (living.getMaxHealth() > h * 4) rate = 0.75f;
					if (entity.getType().is(BOSSES)) {
						if (CaerulaConfigsConfiguration.TRANS_BOSS.get()) rate = 1;
						else return false;
					}
					Entity summoned = SummonRandomSeabornProcedure.execute(world, rate, x, y, z);
					_level.playSound(null, entity.blockPosition(), SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.HOSTILE, 1, 1);
					_level.sendParticles(ParticleTypes.EXPLOSION, x, (y + 0.2), z, 2, 0.1, 0.1, 0.1, 0.15);
					return summoned != null && !summoned.isRemoved();
				}
			}
		}
		return false;
	}

	private static void putDefaultIndex(){
		addTransformIndex(isVillager, CaerulaArborModEntities.OCEANIZED_VILLAGER.get(), 0.375f);
		addTransformIndex(isLlama, CaerulaArborModEntities.OCEANIZED_LLAMA.get(), 0.15f);
		addTransformIndex(isSilverfish, CaerulaArborModEntities.OCEANIZED_SILVERFISH.get(), 0.33f);
		addTransformIndex(isShulker, CaerulaArborModEntities.OCEANIZED_SHULKER.get(), 0.25f);
		addTransformIndex(isKunkun, CaerulaArborModEntities.OCEANIZED_CHICKEN.get(), 0.45f);
		addTransformIndex(isHealer, CaerulaArborModEntities.COMPASSION_PRAYER.get(), 0.15f);
		addTransformIndex(isRabbit, CaerulaArborModEntities.OCEANIZE_RABBIT.get(), 0.45f);
		addTransformIndex(isPolarBear, CaerulaArborModEntities.OCEANIZED_POLAR_BEAR.get(), 0.35f);
		addTransformIndex(isRockSpider, CaerulaArborModEntities.TIDUTANT_ROCK_SPIDER.get(), 0.32f);
		addTransformIndex(isOriQu, CaerulaArborModEntities.TIDUTANT_EXCRESCENCE.get(), 0.5f);
		addTransformIndex(isFox, CaerulaArborModEntities.OCEANIZED_FOX.get(), 0.5f);
		addTransformIndex(isEvoker, CaerulaArborModEntities.OCEANIZED_EVOKER.get(), 0.25f);
		addTransformIndex(isVindicator, CaerulaArborModEntities.OCEANIZED_VINDICATOR.get(), 0.3f);
		addTransformIndex(isPillager, CaerulaArborModEntities.OCEANIZED_PILLAGER.get(), 0.3f);
		addTransformIndex(isPig, CaerulaArborModEntities.OCEANIZED_PIG.get(), 0.5f);
		addTransformIndex(isCow, CaerulaArborModEntities.OCEANIZED_COW.get(), 0.45f);
		addTransformIndex(isSheep, CaerulaArborModEntities.OCEANIZED_SHEEP.get(), 0.45f);
		addTransformIndex(isHorse, CaerulaArborModEntities.OCEANIZED_HORSE.get(), 0.35f);
		addTransformIndex(isPiglin, CaerulaArborModEntities.OCEANIZED_PIGLIN.get(), 0.4f);
		addTransformIndex(isBrute, CaerulaArborModEntities.OCEANIZED_BRUTE.get(), 0.2f);
		addTransformIndex(isSpider, CaerulaArborModEntities.OCEANIZED_SPIDER.get(), 0.65f);
		addTransformIndex(isEnderman, CaerulaArborModEntities.OCEANIZED_ENDERMAN.get(), 0.2f);
		addTransformIndex(isWolf, CaerulaArborModEntities.OCEANIZED_WOLF.get(), 0.2f);
		addTransformIndex(isDog, CaerulaArborModEntities.OCEANIZED_DOG.get(), 1f);
		addTransformIndex(isWitch, CaerulaArborModEntities.OCEANIZED_WITCH.get(), 0.33f);
		addTransformIndex(isRavager, CaerulaArborModEntities.OCEANIZED_RAVAGER.get(), 0.25f);
		addTransformIndex(isWarden, CaerulaArborModEntities.OCEANIZED_WARDEN.get(), 0.1f);
		addTransformIndex(isCat, CaerulaArborModEntities.OCEANIZED_CAT.get(), 0.5f);
		addTransformIndex(isBee, CaerulaArborModEntities.OCEANIZED_BEE.get(), 0.33f);
		addTransformIndex(isFoxiercePig, CaerulaArborModEntities.TIDERAGE_DIRESWINE.get(), 0.25f);
		addTransformIndex(isHoglin, CaerulaArborModEntities.OCEANIZED_HOGLIN.get(), 0.25f);
		
		addTransformIndex(isHuman, CaerulaArborModEntities.THE_ABANDONED.get(), 0.25f);

		AddOceanizationEntryEvent event = new AddOceanizationEntryEvent();
		MinecraftForge.EVENT_BUS.post(event);
	}

	public static final Predicate<LivingEntity> isVillager = (new SimpleTypePredicate<>(Villager.class)).or(v->ForgeRegistries.ENTITY_TYPES.getKey(v.getType()).toString().equals("guardvillagers:guard"));
	public static final Predicate<LivingEntity> isLlama = new SimpleTypePredicate<>(Llama.class);
	public static final Predicate<LivingEntity> isSilverfish = new SimpleTypePredicate<>(Silverfish.class);
	public static final Predicate<LivingEntity> isShulker = new SimpleTypePredicate<>(Shulker.class);
	public static final Predicate<LivingEntity> isKunkun = new SimpleTypePredicate<>(Chicken.class);
	public static final Predicate<LivingEntity> isHealer = new SimpleTypePredicate<>(TribunalHealerEntity.class);
	public static final Predicate<LivingEntity> isRabbit = new SimpleTypePredicate<>(Rabbit.class);
	public static final Predicate<LivingEntity> isPolarBear = new SimpleTypePredicate<>(PolarBear.class);
	public static final Predicate<LivingEntity> isRockSpider = e -> ForgeRegistries.ENTITY_TYPES.getKey(e.getType())
		.toString().equals("bobsoriginiumdream:mutant_giant_rock_spider");
	public static final Predicate<LivingEntity> isOriQu = e -> ForgeRegistries.ENTITY_TYPES.getKey(e.getType())
		.toString().equals("bobsoriginiumdream:originiutant_excrescence");
	public static final Predicate<LivingEntity> isFoxiercePig = e -> ForgeRegistries.ENTITY_TYPES.getKey(e.getType())
		.toString().equals("bobsoriginiumdream:fiercepig");
	public static final Predicate<LivingEntity> isFox = new SimpleTypePredicate<>(Fox.class);
	public static final Predicate<LivingEntity> isEvoker = new SimpleTypePredicate<>(Evoker.class);
	public static final Predicate<LivingEntity> isVindicator = new SimpleTypePredicate<>(Vindicator.class);
	public static final Predicate<LivingEntity> isPillager = new SimpleTypePredicate<>(Pillager.class);
	public static final Predicate<LivingEntity> isPig = new SimpleTypePredicate<>(Pig.class);
	public static final Predicate<LivingEntity> isCow = (new SimpleTypePredicate<>(Cow.class)).or(new SimpleTypePredicate<>(MushroomCow.class));
	public static final Predicate<LivingEntity> isSheep = new SimpleTypePredicate<>(Sheep.class);
	public static final Predicate<LivingEntity> isHorse = new SimpleTypePredicate<>(Horse.class);
	public static final Predicate<LivingEntity> isPiglin = new SimpleTypePredicate<>(Piglin.class);
	public static final Predicate<LivingEntity> isBrute = new SimpleTypePredicate<>(PiglinBrute.class);
	public static final Predicate<LivingEntity> isSpider = (new SimpleTypePredicate<>(CaveSpider.class)).or(new SimpleTypePredicate<>(Spider.class)).or(e->{
		String name = ForgeRegistries.ENTITY_TYPES.getKey(e.getType()).toString();
		return name.equals("twilightforest:king_spider") || name.equals("twilightforest:hedge_spider");
	});
	public static final Predicate<LivingEntity> isEnderman = new SimpleTypePredicate<>(EnderMan.class);
	public static final Predicate<LivingEntity> isWolf = new SimpleTypePredicate<>(Wolf.class).and(e-> e instanceof TamableAnimal t && !t.isTame());
	public static final Predicate<LivingEntity> isDog = new SimpleTypePredicate<>(Wolf.class).and(e-> e instanceof TamableAnimal t && t.isTame());
	public static final Predicate<LivingEntity> isWitch = new SimpleTypePredicate<>(Witch.class);
	public static final Predicate<LivingEntity> isRavager = new SimpleTypePredicate<>(Ravager.class);
	public static final Predicate<LivingEntity> isWarden = new SimpleTypePredicate<>(Warden.class);
	public static final Predicate<LivingEntity> isCat = (new SimpleTypePredicate<>(Cat.class)).or(new SimpleTypePredicate<>(Ocelot.class));
	public static final Predicate<LivingEntity> isHuman = e -> e.getType().is(HUMAN) && !(e instanceof TribunalHealerEntity);
	public static final Predicate<LivingEntity> isBee = new SimpleTypePredicate<>(Bee.class);
	public static final Predicate<LivingEntity> isHoglin = new SimpleTypePredicate<>(Hoglin.class);

	public static class SimpleTypePredicate<T extends LivingEntity> implements Predicate<LivingEntity>{
        private final Class<T> type;
        public SimpleTypePredicate(Class<T> type){
            this.type = type;
        }

        public boolean test(LivingEntity o){
            return type.isInstance(o);
        }
    }

    public static class AddOceanizationEntryEvent extends Event{
    }
}
