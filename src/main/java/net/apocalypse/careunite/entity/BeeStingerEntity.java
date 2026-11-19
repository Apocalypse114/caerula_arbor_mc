
package net.apocalypse.careunite.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.apocalypse.careunite.procedures.GiveLessArmorProcedure;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.entity.ai.attributes.Attributes;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class BeeStingerEntity extends AbstractArrow implements ItemSupplier {
	public static final ItemStack PROJECTILE_ITEM = new ItemStack(Items.TIPPED_ARROW);

	private Vec3 targetPos = Vec3.ZERO;

	public BeeStingerEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CaerulaArborModEntities.BEE_STINGER.get(), world);
	}

	public BeeStingerEntity(EntityType<? extends BeeStingerEntity> type, Level world) {
		super(type, world);
	}

	public BeeStingerEntity(EntityType<? extends BeeStingerEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
	}

	public BeeStingerEntity(EntityType<? extends BeeStingerEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void setRocketTarget(Entity target){
		if (target == null){
			this.targetPos = this.position();
			return;
		}
		this.targetPos = target.position().add(0, target.getBbHeight() * 0.5, 0);
	}

	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("targetX")){
			this.targetPos = new Vec3(
				pCompound.getDouble("targetX"),
				pCompound.getDouble("targetY"),
				pCompound.getDouble("targetZ"));
		}
   }

   public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putDouble("targetX", targetPos.x);
		pCompound.putDouble("targetY", targetPos.y);
		pCompound.putDouble("targetZ", targetPos.z);
   }

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return PROJECTILE_ITEM;
	}

	@Override
	protected ItemStack getPickupItem() {
		return PROJECTILE_ITEM;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	private int tickCloseToTarget = 0;

	@Override
	public void tick() {
		super.tick();
		if (this.inGround || this.tickCount > 200)
			this.discard();
		if (this.distanceToSqr(targetPos) < 0.25){
			tickCloseToTarget ++;
			if (tickCloseToTarget > 10) this.discard();
		}
        if (this.tickCount % 2 == 0) this.level().addParticle(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
		Vec3 orientedMovement = this.position().vectorTo(targetPos).normalize().scale(0.85);
    	this.setDeltaMovement(this.getDeltaMovement().lerp(orientedMovement, 0.2).add(0, 0.05, 0));
	}

	@Override
	public void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()){
			_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
			_entity.invulnerableTime = 0;
		}
		GiveLessArmorProcedure.execute(entity, 8);
	}

	public static BeeStingerEntity shoot(Level world, LivingEntity entity, RandomSource source) {
		return shoot(world, entity, source, 1f, 4, 0);
	}

	public static BeeStingerEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
		return shoot(world, entity, source, pullingPower * 1f, 4, 0);
	}

	public static BeeStingerEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		BeeStingerEntity entityarrow = new BeeStingerEntity(CaerulaArborModEntities.BEE_STINGER.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static BeeStingerEntity shoot(LivingEntity entity, LivingEntity target) {
		BeeStingerEntity entityarrow = new BeeStingerEntity(CaerulaArborModEntities.BEE_STINGER.get(), entity, entity.level());
		entityarrow.setRocketTarget(target);
		Vec3 look = entity.getLookAngle().reverse();
		Vec3 offset = entity.getRandom().nextDouble() < 0.5? new Vec3(look.z, 0, -look.x): new Vec3(-look.z, 0, look.x);
		look = look.add(offset).add(0, entity.getRandom().nextDouble() - 0.5, 0);
		entityarrow.shoot(look.x, look.y , look.z, 1F, 32F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(entity.getAttributeValue(Attributes.ATTACK_DAMAGE)/0.85);
		entityarrow.setKnockback(0);
		entityarrow.setCritArrow(false);
		entityarrow.setPierceLevel((byte)3);
		entity.level().addFreshEntity(entityarrow);
		entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BEE_STING, SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}
