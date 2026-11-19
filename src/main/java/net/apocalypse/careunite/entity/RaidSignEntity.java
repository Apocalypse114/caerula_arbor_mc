
package net.apocalypse.careunite.entity;

import net.minecraftforge.network.PlayMessages;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.procedures.EmrelySyncTeamProcedure;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IExtensibleEnum;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

import net.minecraft.ChatFormatting;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.ItemLike;

public class RaidSignEntity extends Mob {
	public static final EntityDataAccessor<Integer> DATA_WAVE = SynchedEntityData.defineId(RaidSignEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_LEFT_TIME = SynchedEntityData.defineId(RaidSignEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_IS_HARD = SynchedEntityData.defineId(RaidSignEntity.class, EntityDataSerializers.BOOLEAN);
	public final AnimationState animationState0 = new AnimationState();
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);

	public static final int[] durationPerWave = new int[]{100, 1000, 1600, 2000, 2400, 2600, 3200, 4000};
    public final List<Entity> activeRaiders = new ArrayList<>();
    private final Stack<EntityType<?>> commonRaiderStack = new Stack<>(), hardRaiderStack = new Stack<>();
	
	public RaidSignEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.RAID_SIGN.get(), world);
	}

	public RaidSignEntity(EntityType<RaidSignEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(0f);
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
    public boolean canBeSeenAsEnemy() {
        return false;
    }

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_WAVE, 0);
		this.entityData.define(DATA_LEFT_TIME, 100);
		this.entityData.define(DATA_IS_HARD, false);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("DataWAVE", this.entityData.get(DATA_WAVE));
        compound.putInt("DataLEFT_TIME", this.entityData.get(DATA_LEFT_TIME));
        compound.putBoolean("DataIS_HARD", this.entityData.get(DATA_IS_HARD));
        ListTag tagRaiders = new ListTag(), commonStack = new ListTag(), hardStack = new ListTag();
        if (!activeRaiders.isEmpty()) {
            for (Entity raider : activeRaiders) {
                tagRaiders.add(StringTag.valueOf(raider.getStringUUID()));
            }
            compound.put("activeRaidersUUID", tagRaiders);
        }
        if (!commonRaiderStack.empty()) {
            for (EntityType<?> raiderType: commonRaiderStack) {
                ResourceLocation res = ForgeRegistries.ENTITY_TYPES.getKey(raiderType);
                if (res == null) continue;
                commonStack.add(StringTag.valueOf(res.getPath()));
            }
            compound.put("commonRaiderStack", commonStack);
        }
        if (!hardRaiderStack.empty()) {
            for (EntityType<?> raiderType: hardRaiderStack) {
                ResourceLocation res = ForgeRegistries.ENTITY_TYPES.getKey(raiderType);
                if (res == null) continue;
                hardStack.add(StringTag.valueOf(res.getPath()));
            }
            compound.put("hardRaiderStack", hardStack);
        }
    }

    private ListTag tempList1 = null, tempList2 = null, tempList3 = null;

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("DataWAVE"))
            this.entityData.set(DATA_WAVE, compound.getInt("DataWAVE"));
        if (compound.contains("DataLEFT_TIME"))
            this.entityData.set(DATA_LEFT_TIME, compound.getInt("DataLEFT_TIME"));
        if (compound.contains("DataIS_HARD"))
            this.entityData.set(DATA_IS_HARD, compound.getBoolean("DataIS_HARD"));
        this.tempList1 = compound.getList("activeRaidersUUID", 8);
        this.tempList2 = compound.getList("commonRaiderStack", 8);
        this.tempList3 = compound.getList("hardRaiderStack", 8);
    }

    public void revertEntityData(){
        if (this.level() instanceof ServerLevel serverLevel) {
            if (tempList1 != null) {
                for (Tag raider : tempList1) {
                    if (raider instanceof StringTag raiderUUID) {
                        try {
                            UUID uuid = UUID.fromString(raiderUUID.getAsString());
                            Entity entity = serverLevel.getEntity(uuid);
                            if (entity != null) activeRaiders.add(entity);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
        if (tempList2 != null) {
            for (Tag raiderType1 : tempList2) {
                if (raiderType1 instanceof StringTag typeidTag) {
                    EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(typeidTag.getAsString()));
                    if (type != null) commonRaiderStack.push(type);
                }
            }
        }
        if (tempList3 != null) {
            for (Tag raiderType2 : tempList3) {
                if (raiderType2 instanceof StringTag typeidTag) {
                    EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(typeidTag.getAsString()));
                    if (type != null) hardRaiderStack.push(type);
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.animationState0.animateWhen(true, this.tickCount);
        }
        this.setYRot(0);
        if (this.level().getDifficulty() != Difficulty.PEACEFUL) tickWave();
        if (this.tickCount % 10 == 0){
            tickRaiders();
            this.removeAllEffects();
            this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 10, 0, false, false));
        }
    }
    @Override
    public void push(double x, double y, double z){
    }
    @Override
    public void setDeltaMovement(Vec3 vec3){
        super.setDeltaMovement(Vec3.ZERO);
    }
    @Override
    public boolean hurt(DamageSource pSource, float amount){
        return true;
    }
    @Override
    public void actuallyHurt(DamageSource pSource, float amount){
    }
    @Override
    public void setHealth(float pHealth){
        super.setHealth(10);
    }
    @Override
    public float getHealth(){
    	return 10;
    }
    @Override
    public void die(@NotNull DamageSource pSource){
    }
    public int MAX_DIST = 16777216;
    @Override
    public void setPos(double x, double y, double z){
        if (this.tickCount > 5 && this.distanceToSqr(x,y,z) > MAX_DIST) return;
        super.setPos(x,y,z);
    }
    @Override
    public void moveTo(double x, double y, double z, float pYRot, float pXRot){
        if (this.tickCount > 5 && this.distanceToSqr(x,y,z) > MAX_DIST) return;
        super.moveTo(x,y,z,pYRot,pXRot);
    }

    @Override
    public boolean isPushable() {
        return false;
    }
    @Override
    protected void doPush(Entity entityIn) {
    }
    @Override
    protected void pushEntities() {
    }
    @Override
    public boolean canChangeDimensions() {
        return false;
    }
    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
    }
    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        if (this.tickCount % 10 == 0){
            bossInfo.setName(getBossbarComponent());
        }
    }
    public static void init() {
    }
    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
        builder = builder.add(Attributes.MAX_HEALTH, 10);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }
    public boolean isHard(){
        return this.entityData.get(DATA_IS_HARD);
    }
    public int getWave(){
        return this.entityData.get(DATA_WAVE);
    }
    public int getFinalWave() {
        return isHard() ? 7 : 4;
    }
    public void boostWave(){
        int nextWave = getWave() +1;
        this.entityData.set(DATA_WAVE, nextWave);
        this.entityData.set(DATA_LEFT_TIME, durationPerWave[nextWave]);
    }
    public int getTickLeft(){
        return this.entityData.get(DATA_LEFT_TIME);
    }
    public void reduceTickLeft(){
        this.entityData.set(DATA_LEFT_TIME, getTickLeft() - 1);
    }
    public void waveSetUp(){
        int wave = getWave();
        commonRaiderStack.clear();
        hardRaiderStack.clear();
        if (wave == 0) return;;
        Level level = this.level();
        for (CAST cast: CAST.values()){
            int count = cast.getCountWithDifficultyBonus(wave, level);
            if (count <= 0) continue;
            EntityType<?> type = cast.getType();
            boolean hardOnly = cast.isHardOnly();
            for (int i = 0; i < count; i++){
                if (hardOnly) hardRaiderStack.push(type);
                else commonRaiderStack.push(type);
            }
        }
    }
    public void summonNextRaider(){
        if (this.level() instanceof ServerLevel serverLevel) {
        	EntityType<?> nextRaider = null;
        	if (!commonRaiderStack.empty()) nextRaider = commonRaiderStack.pop();
            int dx = Mth.nextInt(this.getRandom(), -1, 1),
                    dz = Mth.nextInt(this.getRandom(), -1, 1);
            BlockPos pos = this.blockPosition().offset(dx, 0, dz);
            Vec3 lookAtPosition = this.position().add(dx * 3, 0, dz * 3);
            if (nextRaider != null) {
                summonRaiderAt(nextRaider, serverLevel, pos, lookAtPosition);
            } else if (isHard()){
                if (!hardRaiderStack.empty()) nextRaider = hardRaiderStack.pop();
                if (nextRaider != null) {
                    summonRaiderAt(nextRaider, serverLevel, pos, lookAtPosition);
                }
            }
        }
    }
    public void summonRaiderAt(EntityType<?> raider, ServerLevel serverLevel, BlockPos pos, Vec3 pLookAtPos){
        Entity raider1 = raider.spawn(serverLevel, pos, MobSpawnType.MOB_SUMMONED);
        Vec3 particlePos = pos.getCenter().add(0, 0.25, 0);
        serverLevel.sendParticles(ParticleTypes.CLOUD,
                particlePos.x, particlePos.y, particlePos.z,
                8, 0.75, 0.75, 0.75, 0.1);
        if (raider1 != null){
            raider1.lookAt(EntityAnchorArgument.Anchor.FEET, pLookAtPos);
            activeRaiders.add(raider1);
            EmrelySyncTeamProcedure.execute(this, raider1);
        }
    }
    public void tickRaiders(){
        List<Entity> raiders = new ArrayList<>(activeRaiders);
        for (Entity raider: raiders){
            if (raider == null){
                activeRaiders.remove(null);
                continue;
            }
            if (!this.level().isLoaded(raider.blockPosition())) continue;;
            if (raider.isRemoved()) activeRaiders.remove(raider);
            if (raider instanceof PathfinderMob mob){
                mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false));
                mob.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ANGER_OF_TIDE.get(), 40, 0, false, false));
                if (mob.distanceTo(this) > 48 &&
                        (mob.getTarget() == null || !mob.getTarget().isAlive())){
                    mob.teleportTo(this.getX(), this.getY(), this.getZ());
                }
            }
        }
    }
    public void setBlockorDrop(int dy, Block block, ItemLike item){
    	BlockPos p = this.blockPosition().above(dy);
    	if (this.level().getBlockState(p).canBeReplaced()) {
			this.level().setBlock(p, block.defaultBlockState(), 3);
		} else {
			this.spawnAtLocation(new ItemStack(item));
		}
    }
    public void onRaidEnd(boolean success){
        if (this.level() instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + 0.5, this.getZ(), 4, 1, 1, 1, 0.1);
        if (success){
        	this.spawnAtLocation(new ItemStack(CaerulaArborModItems.REDSTONE_INGOT.get(), 32));
        	this.setBlockorDrop(1, CaerulaArborModBlocks.BANNER_TROPHY.get(), CaerulaArborModItems.ITEM_BANNERTROPHY.get());
        	this.setBlockorDrop(0, CaerulaArborModBlocks.NETHERSEA_EMERALD_BLOCK.get(), CaerulaArborModBlocks.NETHERSEA_EMERALD_BLOCK.get());
        } else {
            for(Entity raider: activeRaiders){
                if (raider instanceof LivingEntity living){
                    living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2400, 3));
                    living.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get(), 2400, 4));
                    living.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_HEALTH_PERCLY.get(), 2400, 4));
                    living.setHealth(living.getMaxHealth());
                    living.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 180, 0));
                }
            }
        }
        this.discard();
    }
    public Component getBossbarComponent(){
    	int t = getTickLeft(), w = getWave();
    	MutableComponent title = Component.translatable("entity.caerula_arbor_reunite.raid_sign.desc_1").append(""+w)
        	.append(Component.translatable("entity.caerula_arbor_reunite.raid_sign.desc_2"))
 			.append(""+(int)(t*0.05)).append(Component.translatable("entity.caerula_arbor_reunite.raid_sign.desc_3"));
 		if (w >= getFinalWave() && t % 20 < 10 && getRaidProgress(t, w) < 0.2) return title.withStyle(ChatFormatting.RED);
 		return title;
    }
    public float getRaidProgress(int left,int wave){
        return (float) left / durationPerWave[wave];
    }
    private boolean dataNotReversed = true;
    public void tickWave(){
        int wave = getWave(), tickLeft = getTickLeft();
        boolean currentlyFinalWave = wave >= getFinalWave();
        if (dataNotReversed){
            revertEntityData();
            dataNotReversed = false;
        }
        if (tickLeft > 0){
            reduceTickLeft();
            if (wave > 0 && tickLeft % 5 == 0){
            	summonNextRaider();
            	tickRaiders();
            }
            if (activeRaiders.isEmpty()){
                if (!this.level().isClientSide() && currentlyFinalWave) onRaidEnd(true);
                else if (tickLeft > 100) this.entityData.set(DATA_LEFT_TIME, 100);
            }
        } else if (currentlyFinalWave){
            tickRaiders();
            if (!this.level().isClientSide() && !activeRaiders.isEmpty()) onRaidEnd(false);
        } else {
            boostWave();
            waveSetUp();
        }
        bossInfo.setProgress(getRaidProgress(tickLeft, wave));
    }

    public Entity getARaider(){
    	if (activeRaiders.isEmpty()) return null;
    	return activeRaiders.get(0);
    }

    public enum CAST implements IExtensibleEnum {
        WOLF(CaerulaArborModEntities.OCEANIZED_WOLF.get(), 4, 3, 2, 1, 0, 0, 0),
        VILLAGER(CaerulaArborModEntities.OCEANIZED_VILLAGER.get(), 3, 3, 4, 3, 2, 1, 0),
        PILLAGER(CaerulaArborModEntities.OCEANIZED_PILLAGER.get(), 1, 2, 2, 2, 3, 3, 4),
        VINDICATOR(CaerulaArborModEntities.OCEANIZED_VINDICATOR.get(), 0, 1, 1, 2, 2, 3, 4),
        WITCH(CaerulaArborModEntities.OCEANIZED_WITCH.get(), 0, 1, 0, 2, 1, 2, 2),
        EVOKER(CaerulaArborModEntities.OCEANIZED_EVOKER.get(), 0, 0, 1, 2, 1, 2, 2),
        RAVAGER(CaerulaArborModEntities.OCEANIZED_RAVAGER.get(), 0, 0, 0, 1, 0, 1, 2),

        PRAYER(CaerulaArborModEntities.COMPASSION_PRAYER.get(), 0, 0, 0, 1, 0, 1, 1),

        SLIDER(CaerulaArborModEntities.SLIDER_FISH.get(), 4, 2, 0, 0, 4, 0, 0),
        SWARCALLER(CaerulaArborModEntities.UMBRELLA_ABYSSAL.get(), 0, 1, 0, 1, 1, 1, 2),
        NURSE(CaerulaArborModEntities.FEEDER_PROKARYOTE.get(), 0, 0, 1, 1, 0, 1, 2),

        ABANDONED(CaerulaArborModEntities.THE_ABANDONED.get(), 0, 0, 1, 1, 1, 1, 2, true),
        FIRST(CaerulaArborModEntities.FIRST_TO_TALK.get(), 0 ,0 ,0 ,1, 0 ,1, 1, true)
        ;

        private final EntityType<?> type;
        private final boolean hardOnly;
        private final int[] countPerWave;

        CAST(EntityType<?> type, int c0, int c1, int c2, int c3, int c4, int c5, int c6, boolean hardOnly){
            this.type = type;
            this.countPerWave = new int[]{c0, c1, c2, c3, c4, c5, c6};
            this.hardOnly = hardOnly;
        }
        CAST(EntityType<?> type, int c0, int c1, int c2, int c3, int c4, int c5, int c6){
            this(type, c0, c1, c2, c3, c4, c5, c6, false);
        }

        public EntityType<?> getType() {
            return type;
        }
        public int getCount(int wave) {
            wave -= 1;
            if (wave < 0 || wave >= countPerWave.length) return 0;
            return this.countPerWave[wave];
        }
        public int getCountWithDifficultyBonus(int wave, Level level){
            Difficulty difficulty = level.getDifficulty();
            int original = getCount(wave);
            return switch (difficulty){
                case HARD -> original * 2;
                case NORMAL -> Math.round(original * 1.5f);
                default -> original;
            };
        }
        public boolean isHardOnly(){
            return hardOnly;
        }

        public static CAST create(String name, EntityType<?> type, int c0, int c1, int c2, int c3, int c4, int c5, int c6, boolean hardOnly) {
            throw new IllegalStateException("Enum not extended");
        }
    }
}
