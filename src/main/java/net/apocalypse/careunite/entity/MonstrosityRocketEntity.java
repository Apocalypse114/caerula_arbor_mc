
package net.apocalypse.careunite.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
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

import net.apocalypse.careunite.procedures.RocketExplodeProcedure;
import net.apocalypse.careunite.procedures.GetLightProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class MonstrosityRocketEntity extends AbstractArrow implements ItemSupplier {
	public static final ItemStack PROJECTILE_ITEM = new ItemStack(Items.FIREWORK_ROCKET);

	private double dama = 0;
    private Vec3 targetPos = Vec3.ZERO;

	public MonstrosityRocketEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CaerulaArborModEntities.MONSTROSITY_ROCKET.get(), world);
	}

	public MonstrosityRocketEntity(EntityType<? extends MonstrosityRocketEntity> type, Level world) {
		super(type, world);
	}

	public MonstrosityRocketEntity(EntityType<? extends MonstrosityRocketEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
	}

	public MonstrosityRocketEntity(EntityType<? extends MonstrosityRocketEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("damage")) {
			this.dama = pCompound.getDouble("damage");
		}
		if (pCompound.contains("targetX")){
			this.targetPos = new Vec3(
				pCompound.getDouble("targetX"),
				pCompound.getDouble("targetY"),
				pCompound.getDouble("targetZ"));
		}
   }

   public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putDouble("damage", dama);
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

	@Override
	public void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		RocketExplodeProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this.getOwner(), this, this.dama);
		this.discard();
	}

	@Override
	public void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		BlockPos pos = blockHitResult.getBlockPos();
		Vec3 center = pos.getCenter();
		RocketExplodeProcedure.execute(this.level(), center.x, center.y, center.z, this.getOwner(), this, this.dama);
		this.discard();
	}

	@Override
	public void tick() {
		super.tick();
        this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
		if (this.inGround)
			this.discard();
		if (targetPos.distanceToSqr(this.position()) < 0.25){
			RocketExplodeProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this.getOwner(), this, this.dama);
			this.discard();
		} else {
			Vec3 orientedMovement = this.position().vectorTo(targetPos.add(0, 0.25, 0)).normalize().scale(1.5);
        	this.setDeltaMovement(this.getDeltaMovement().lerp(orientedMovement, 0.25).add(0, 0.05, 0));
		}
	}

	public void setRocketDamage(double d){
		this.dama = d;
	}
	public void setRocketTarget(Entity target){
		if (target == null){
			this.targetPos = this.position();
			return;
		}
		this.targetPos = target.position();
	}

	public static MonstrosityRocketEntity shoot(Level world, LivingEntity entity, RandomSource source) {
		return shoot(world, entity, source, 1f, 5, 5);
	}

	public static MonstrosityRocketEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
		return shoot(world, entity, source, pullingPower * 1f, 5, 5);
	}

	public static MonstrosityRocketEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		MonstrosityRocketEntity entityarrow = new MonstrosityRocketEntity(CaerulaArborModEntities.MONSTROSITY_ROCKET.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_shoot")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static MonstrosityRocketEntity shoot(LivingEntity entity, LivingEntity target) {
		MonstrosityRocketEntity entityarrow = new MonstrosityRocketEntity(CaerulaArborModEntities.MONSTROSITY_ROCKET.get(), entity, entity.level());
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(5);
		entityarrow.setKnockback(5);
		entityarrow.setCritArrow(false);
		entity.level().addFreshEntity(entityarrow);
		entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_shoot")), SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}
