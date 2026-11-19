
package net.apocalypse.careunite.entity;

import net.minecraftforge.network.PlayMessages;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.sounds.SoundSource;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import javax.annotation.Nullable;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.apocalypse.careunite.procedures.ElectMobsTridentProcedure;
import net.apocalypse.careunite.procedures.DeductPlayerSanityProcedure;

public class ComplexChitinTridentThrownEntity extends AbstractArrow {
	public float damage;

	public ComplexChitinTridentThrownEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CaerulaArborModEntities.COMPLEX_CHITIN_TRIDENT_THROWN.get(), world);
	}

	public ComplexChitinTridentThrownEntity(EntityType<? extends ComplexChitinTridentThrownEntity> type, Level world) {
		super(CaerulaArborModEntities.COMPLEX_CHITIN_TRIDENT_THROWN.get(), world);
	}

	public ComplexChitinTridentThrownEntity(EntityType<? extends ComplexChitinTridentThrownEntity> type, double x, double y, double z, Level world) {
		super(CaerulaArborModEntities.COMPLEX_CHITIN_TRIDENT_THROWN.get(), world);
	}

	public ComplexChitinTridentThrownEntity(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
      super(CaerulaArborModEntities.COMPLEX_CHITIN_TRIDENT_THROWN.get(), pShooter, pLevel);
      this.tridentItem = pStack.copy();
      this.entityData.set(ID_LOYALTY, (byte)EnchantmentHelper.getLoyalty(pStack));
      this.entityData.set(ID_FOIL, pStack.hasFoil());
      this.damage = getDamage(pShooter);
   }

   public static float getDamage(LivingEntity entity){
		return (float) entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
   }

	private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(ChitinTridentThrownEntity.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ChitinTridentThrownEntity.class, EntityDataSerializers.BOOLEAN);
   private ItemStack tridentItem = new ItemStack(CaerulaArborModItems.COMPLEX_CHITIN_TRIDENT.get());
   private int dealtDamage = 2;
   public int clientSideReturnTridentTickCount;
   
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(ID_LOYALTY, (byte)0);
      this.entityData.define(ID_FOIL, false);
   }

   public void tick() {
      if (this.inGroundTime > 4 || this.getY() < -64) {
         this.dealtDamage = 0;
         this.immediateReturn();
      }

      Entity entity = this.getOwner();
      int i = this.entityData.get(ID_LOYALTY);
      if (i > 0 && (this.dealtDamage <= 0 || this.isNoPhysics()) && entity != null) {
         if (!this.isAcceptibleReturnOwner()) {
            if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
               this.spawnAtLocation(this.getPickupItem(), 0.1F);
            }

            this.discard();
         } else {
            this.setNoPhysics(true);
            Vec3 vec3 = entity.getEyePosition().subtract(this.position());
            this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double)i, this.getZ());
            if (this.level().isClientSide) {
               this.yOld = this.getY();
            }

            double d0 = 0.05D * (double)i;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
            if (this.clientSideReturnTridentTickCount == 0) {
               this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
            }

            ++this.clientSideReturnTridentTickCount;
         }
      }
      if (this.inGroundTime <= 4 && this.isChanneling() && this.tickCount < 100) ElectMobsTridentProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this.damage * 0.5f, this);
      super.tick();
   }

   public void immediateReturn(){
		Entity owner = this.getOwner();
		if (!this.level().isClientSide() && owner instanceof Player player && this.pickup == AbstractArrow.Pickup.ALLOWED){
			if (player.isCreative()) return;
			if(player.getInventory().add(this.getPickupItem())) this.discard();
		}
	}

   private boolean isAcceptibleReturnOwner() {
      Entity entity = this.getOwner();
      if (entity != null && entity.isAlive()) {
         return !(entity instanceof ServerPlayer) || !entity.isSpectator();
      } else {
         return false;
      }
   }

   protected ItemStack getPickupItem() {
      return this.tridentItem.copy();
   }

   public boolean isFoil() {
      return this.entityData.get(ID_FOIL);
   }

   @Nullable
   protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
      return this.dealtDamage <= 0 ? null : super.findHitEntity(pStartVec, pEndVec);
   }

   protected void onHitEntity(EntityHitResult pResult) {
      Entity entity = pResult.getEntity();
      if (entity == this.getOwner()) return;
      float f = this.damage;
      if (entity instanceof LivingEntity livingentity) {
         f += EnchantmentHelper.getDamageBonus(this.tridentItem, livingentity.getMobType());
      }

      Entity entity1 = this.getOwner();
      DamageSource damagesource = this.damageSources().trident(this, (Entity)(entity1 == null ? this : entity1));
      this.dealtDamage --;
      SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
      if (entity.hurt(damagesource, f)) {
         if (entity.getType() == EntityType.ENDERMAN) {
            return;
         }

         if (entity instanceof LivingEntity) {
            LivingEntity livingentity1 = (LivingEntity)entity;
            if (entity1 instanceof LivingEntity) {
               EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
               EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
            }
			DeductPlayerSanityProcedure.execute(entity, f * 12);
            this.doPostHurtEffects(livingentity1);
         }
      }

      if (this.dealtDamage <= 0)this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
      float f1 = 1.0F;
      if (this.level() instanceof ServerLevel && this.level().isThundering() && this.isChanneling()) {
         BlockPos blockpos = entity.blockPosition();
         if (this.level().canSeeSky(blockpos)) {
            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(this.level());
            if (lightningbolt != null) {
               lightningbolt.moveTo(Vec3.atBottomCenterOf(blockpos));
               lightningbolt.setCause(entity1 instanceof ServerPlayer ? (ServerPlayer)entity1 : null);
               this.level().addFreshEntity(lightningbolt);
               soundevent = SoundEvents.TRIDENT_THUNDER;
               f1 = 5.0F;
            }
         }
      }

      this.playSound(soundevent, f1, 1.0F);
   }

   public boolean isChanneling() {
      return EnchantmentHelper.hasChanneling(this.tridentItem);
   }

   protected boolean tryPickup(Player pPlayer) {
      return super.tryPickup(pPlayer) || this.isNoPhysics() && this.ownedBy(pPlayer) && pPlayer.getInventory().add(this.getPickupItem());
   }

   protected SoundEvent getDefaultHitGroundSoundEvent() {
      return SoundEvents.TRIDENT_HIT_GROUND;
   }

   public void playerTouch(Player pEntity) {
      if (this.ownedBy(pEntity) || this.getOwner() == null) {
         super.playerTouch(pEntity);
      }
   }

   public void readAdditionalSaveData(CompoundTag pCompound) {
      super.readAdditionalSaveData(pCompound);
      if (pCompound.contains("Trident", 10)) {
         this.tridentItem = ItemStack.of(pCompound.getCompound("Trident"));
      }

      this.dealtDamage = pCompound.getInt("DealtDamage");
      this.damage = pCompound.getFloat("Damage");
      this.entityData.set(ID_LOYALTY, (byte)EnchantmentHelper.getLoyalty(this.tridentItem));
   }

   public void addAdditionalSaveData(CompoundTag pCompound) {
      super.addAdditionalSaveData(pCompound);
      pCompound.put("Trident", this.tridentItem.save(new CompoundTag()));
      pCompound.putInt("DealtDamage", this.dealtDamage);
      pCompound.putFloat("Damage", damage);
   }

   public void tickDespawn() {
      int i = this.entityData.get(ID_LOYALTY);
      if (this.pickup != AbstractArrow.Pickup.ALLOWED || i <= 0) {
         super.tickDespawn();
      }
   }

   protected float getWaterInertia() {
      return 0.999F;
   }

   public boolean shouldRender(double pX, double pY, double pZ) {
      return true;
   }

   public static ComplexChitinTridentThrownEntity shoot(LivingEntity entity, LivingEntity target) {
		ComplexChitinTridentThrownEntity entityarrow = new ComplexChitinTridentThrownEntity(entity.level(), entity, 
			new ItemStack(CaerulaArborModItems.COMPLEX_CHITIN_TRIDENT.get()));
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1.6f, 3F);
		entity.level().addFreshEntity(entityarrow);
		entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TRIDENT_THROW, SoundSource.HOSTILE, 1,
				1f / (entity.getRandom().nextFloat() * 0.5f + 1));
		entityarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
		return entityarrow;
	}

}
