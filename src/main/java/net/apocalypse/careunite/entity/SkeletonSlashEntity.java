
package net.apocalypse.careunite.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import java.util.Set;
import net.minecraft.world.entity.LivingEntity;
import java.util.HashSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.phys.AABB;
import java.util.List;
import net.minecraft.world.entity.MoverType;

public class SkeletonSlashEntity extends PathfinderMob {
	public SkeletonSlashEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.SKELETON_SLASH.get(), world);
	}

	private Mob owner = null;
    private float damage = 14;
    private final Set<LivingEntity> hurtEntities = new HashSet<>();
    private Vec3 orientation = Vec3.ZERO;

	public SkeletonSlashEntity(EntityType<SkeletonSlashEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(0.6f);
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
	}

	public void setup(Mob owner, Entity enemy, float damage, int index){
        this.owner = owner;
        this.damage = damage;
        this.orientation = owner.position().vectorTo(enemy.position()).normalize();
        if (index == 1) orientation = orientation.yRot(30 * Mth.DEG_TO_RAD);
        else if (index == -1) orientation = orientation.yRot(-30 * Mth.DEG_TO_RAD);
        this.setYRot(owner.getYRot());
        this.setPos(owner.position());
    }
    
    public static final TagKey<EntityType<?>> SEABORN = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring"));
    private static final ResourceKey<DamageType> TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_wither"));
    
    private DamageSource getSource(Entity direct, Entity source){
    	return new DamageSource(
    		direct.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(TYPE), 
    		direct, source == null ? direct : source);
    }
    private boolean canAttack(Entity entity){
    	if(entity.getType().is(SEABORN)){
    		return owner.getTarget() == entity;
    	}
    	return true;
    }

	@Override
	public void tick(){
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		if (orientation.lengthSqr() < 1e-6) orientation = this.getLookAngle();
        this.setDeltaMovement(orientation);
        if (this.level() instanceof ServerLevel serverLevel) {
            AABB aabb = new AABB(this.position().add(-3, 0, -3), this.position().add(3, 5, 3));
            List<LivingEntity> livingEntities = serverLevel.getEntitiesOfClass(LivingEntity.class, aabb, 
            	e -> !hurtEntities.contains(e) && canAttack(e));
            livingEntities.forEach(e->{
                e.hurt(getSource(this, owner), damage);
                hurtEntities.add(e);
            });
        }
        if (this.tickCount > 100) this.discard();
	}
	
	@Override
	public void move(MoverType pType, Vec3 pPos) {
      super.move(pType, pPos);
      this.checkInsideBlocks();
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
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	@Override
	public boolean hurt(DamageSource damagesource, float amount) {
		return true;
	}
	@Override
	public void actuallyHurt(DamageSource damagesource, float amount) {}
	@Override
	public void tickDeath(){}
	@Override
	public void setHealth(float pHealth){}
	@Override
	public void die(DamageSource pDamageSource){}
	@Override
	public float getHealth(){
		return 10;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	public void setNoGravity(boolean ignored) {
		super.setNoGravity(true);
	}

	public void aiStep() {
		super.aiStep();
		this.setNoGravity(true);
	}

	public static void init() {
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


	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 14);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 99);
		builder = builder.add(Attributes.FLYING_SPEED, 0.3);
		return builder;
	}
}
