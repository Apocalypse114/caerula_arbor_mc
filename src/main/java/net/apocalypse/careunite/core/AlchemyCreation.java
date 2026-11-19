package net.apocalypse.careunite.core;

import net.apocalypse.careunite.entity.AlchemyBaseEntity;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.apocalypse.careunite.procedures.EmrelySyncTeamProcedure;
import net.apocalypse.careunite.entity.AlchemyBaseEntity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.EmrelySyncTeamProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import java.util.List;

import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.minecraft.world.effect.MobEffectInstance;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.AgeableMob;

public class AlchemyCreation extends TamableAnimal{
	public AlchemyCreation(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return isStunned() ? SoundEvents.ARMOR_STAND_HIT: CaerulaArborModSounds.ALCHEMY_HIT.get();
	}

	@Override
	public SoundEvent getDeathSound() {
		return isStunned() ? SoundEvents.ARMOR_STAND_BREAK: CaerulaArborModSounds.ALCHEMY_CHANGE.get();
	}


    public static final EntityDataAccessor<Boolean> STUNNED = SynchedEntityData.defineId(AlchemyCreation.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> BSHD = SynchedEntityData.defineId(AlchemyCreation.class, EntityDataSerializers.INT);

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(STUNNED, false);
		this.entityData.define(BSHD, 1);
	}

    private int baseHeld = 1;
    public int tickInPara = 0;


    @Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		return null;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	public void spawnedFromInjection(BlockState state, Player pPlayer){
		this.tame(pPlayer);
		this.setPersistenceRequired();
		this.onRegainBase();
	}

    public void aiStep() {
		super.aiStep();
		if (isStunned()){
			if (!this.hasEffect(CaerulaArborModMobEffects.PARALYZED.get()))
				this.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.PARALYZED.get(), 15, 0, false, false));
			tickInPara ++;
			if (tickInPara > 10000) this.discard();
		} else {
			tickInPara = 0;
		}
		if (overflowTick > 0){
			overflowTick --;
			if (overflowTick == 0){
				setStunned(false);
		        this.level().playSound(null, this.blockPosition(), CaerulaArborModSounds.ALCHEMY_REVIVE.get(), SoundSource.HOSTILE, 
		            	1, Mth.nextFloat(this.getRandom(), 0.8f, 1.2f));
			}
		}
	}

	public boolean shouldProtectWhenStunned(){
		return !canOverflow();
	}

	public boolean canOverflow(){
		return false;
	}

	public int maxBase(){
		return 1;
	}

	public void actuallyHurt(DamageSource pDamageSource,float pDamageAmount){
		super.actuallyHurt(pDamageSource, pDamageAmount);
		if (!this.isDeadOrDying() && isStunned() && shouldProtectWhenStunned() && !this.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get()))
			this.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 8, 9, false, false));
	}

	public void tick(){
		super.tick();
		if (this.tickCount % 10 == 0) tickAlchemy();
		//if (this.tickCount == 2) gainBase(0);
	}

    @Override
	public void baseTick() {
		super.baseTick();
		if (!this.level().isClientSide()) this.entityData.set(BSHD, baseHeld);
		//if (!this.level().isClientSide() && isStunned()) this.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.PARALYZED.get(), 10, 0, false, false));
	}

	@Override
    public boolean canBeSeenAsEnemy() {
        return shouldProtectWhenStunned() ? !isStunned(): super.canBeSeenAsEnemy();
    }

    public boolean canInject(){
    	if (canOverflow()) return baseHeld < maxBase() + 1 && !isStunned();
    	return baseHeld < 1;
    }
    
    public void tickAlchemy(){
        if (!this.isDeadOrDying() && shouldTryAbsorb() && this.level() instanceof ServerLevel serverLevel) {
            List<AlchemyBaseEntity> base = serverLevel.getEntitiesOfClass(AlchemyBaseEntity.class,
                    new AABB(this.position(),this.position()).inflate(2),
                    e -> !e.isDeadOrDying() && this.distanceToSqr(e) <= 4);
            if (base.isEmpty()) return;
            absorbBase(base.get(0));
        }
        //if (!this.level().isClientSide()) this.entityData.set(BSHD, baseHeld);
    }

    public int getClientBaseHeld(){
    	return Mth.clamp(entityData.get(BSHD), 0, maxBase());
    }

    @Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("alchemyBase", baseHeld);
		compound.putInt("tickInPara", tickInPara);
		compound.putBoolean("paralyzed", isStunned());
		compound.putInt("overflowTick", overflowTick);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		baseHeld = compound.getInt("alchemyBase");
		gainBase(0);
		tickInPara = compound.getInt("tickInPara");
		setStunned(compound.getBoolean("paralyzed"));
		this.overflowTick = compound.getInt("overflowTick");
	}


    public int getBaseHeld(){return baseHeld;}
    public void setBaseHeld(int v){baseHeld = v;}
    public boolean shouldTryAbsorb(){
    	if (canOverflow()) return getBaseHeld() < maxBase() && !isStunned();
    	return isStunned();
    }
    public boolean isStunned(){return this.entityData.get(STUNNED);}
    public void setStunned(boolean st){this.entityData.set(STUNNED, st);}

    public int extractionCooldown(){
    	return 40;
    }
    public int injectionType(){
    	return 0;
    }

    protected boolean shouldDropLoot() {
      return true;
   }

    public void heal(float pHealAmount){
    	if (isStunned()) return;
    	super.heal(pHealAmount);
    }

    public void setHealth(float pHealth){
        if (pHealth <= 0 && shouldReviveIntoFurnance() && !isStunned()){
            lostBase(999, false);
            return;
        }
        super.setHealth(pHealth);
    }

    public int furnaceHealth(){return 3;}

    public boolean shouldReviveIntoFurnance(){
    	return !canOverflow();
    }

	public int overflowTick = 0;

	public void gainBase(int count){
		int beforeBase = baseHeld;
		this.baseHeld += count;
		if (this.level() instanceof ServerLevel serverLevel){
			if (count > 0)
            	serverLevel.sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY()+0.8, this.getZ(),
                    32, 0.8, 0.8, 0.8, 0.1);
            if (beforeBase <= 0 && baseHeld > 0 && isStunned()){
	            onRegainBase();
	            overflowTick = 0;
	            this.setHealth(this.getMaxHealth() * 0.75f);
       		 }
       		 entityData.set(BSHD, baseHeld);
        }
        if (canOverflow()){
			if (baseHeld > maxBase()){
				onOverflow();
			}
        }
	}

	public void onOverflow(){
		baseHeld = 0;
		setStunned(true);
		overflowTick = 300;
        this.level().playSound(null, this.blockPosition(), CaerulaArborModSounds.ALCHEMY_OVERFLOW.get(), SoundSource.HOSTILE, 
            	1, Mth.nextFloat(this.getRandom(), 0.8f, 1.2f));
	}
	
    public void absorbBase(LivingEntity base){
        if (base == null) return;
        base.removeAllEffects();
        base.discard();
        gainBase(1);
    }

    public void lostBase(int count, boolean release){
        if (this.level() instanceof ServerLevel serverLevel) {
        	if (release){
                for (int i = 0; i < count; i++) {
                    Entity entity = CaerulaArborModEntities.ALCHEMY_BASE.get().spawn(serverLevel, this.blockPosition(), MobSpawnType.MOB_SUMMONED);
                    if (entity != null){
                    	baseHeld --;
                    	EmrelySyncTeamProcedure.execute(this, entity);
                    }
                    if (baseHeld <= 0) break;
                }
        	} else {
        		baseHeld = Math.max(baseHeld - count, 0);
        	}
            if (baseHeld <= 0 && !canOverflow()){
            	onExhaustBase();
            }
            entityData.set(BSHD, baseHeld);
        }
    }

    public void onRegainBase(){
		setStunned(false);
        this.level().playSound(null, this.blockPosition(), CaerulaArborModSounds.ALCHEMY_REVIVE.get(), SoundSource.HOSTILE, 
        	1, Mth.nextFloat(this.getRandom(), 0.8f, 1.2f));
	}
	public void onExhaustBase(){
		baseHeld = 0;
		setStunned(true);
		super.setHealth(furnaceHealth());
        this.level().playSound(null, this.blockPosition(), CaerulaArborModSounds.ALCHEMY_CHANGE.get(), SoundSource.HOSTILE, 
            	1, Mth.nextFloat(this.getRandom(), 0.8f, 1.2f));
	}

	public static interface Furnace{
		int injectionType();
		EntityType<?> injectionCreation();
	}
	public static interface InjectableEntity{
		int injectionType();
		int injectionCost();
		boolean onInjection(Player player);
	}
}
