
package net.apocalypse.careunite.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
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

import net.apocalypse.careunite.procedures.OceanArrowHitProcedure;
import net.apocalypse.careunite.procedures.BombArrowProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.minecraft.util.Mth;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class ShotOceanArrowEntity extends AbstractArrow implements ItemSupplier {
	public static final ItemStack PROJECTILE_ITEM = new ItemStack(CaerulaArborModItems.OCEAN_ARROW.get());
	private int age;

	public ShotOceanArrowEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CaerulaArborModEntities.SHOT_OCEAN_ARROW.get(), world);
		this.age = Mth.nextInt(world.getRandom(), 10, 16);
	}

	public ShotOceanArrowEntity(EntityType<? extends ShotOceanArrowEntity> type, Level world) {
		super(type, world);
		this.age = Mth.nextInt(world.getRandom(), 10, 16);
	}

	public ShotOceanArrowEntity(EntityType<? extends ShotOceanArrowEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
		this.age = Mth.nextInt(world.getRandom(), 10, 16);
	}

	public ShotOceanArrowEntity(EntityType<? extends ShotOceanArrowEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
		this.age = Mth.nextInt(world.getRandom(), 10, 16);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
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
		OceanArrowHitProcedure.execute(this.level(), entityHitResult.getEntity(), this, this.getOwner());
	}

	@Override
	public void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		BombArrowProcedure.execute(this.level(), this);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.inGround && this.inGroundTime > age)
{
			this.discard();
		}
		//if (isSkill) this.setDeltaMovement(this.getDeltaMovement().add(0, 0.05, 0));
	}

	public static ShotOceanArrowEntity shoot(Level world, LivingEntity entity, RandomSource source) {
		return shoot(world, entity, source, 2.5f, 2.5, 0);
	}

	public static ShotOceanArrowEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
		return shoot(world, entity, source, pullingPower * 2.5f, 2.5, 0);
	}

	public static ShotOceanArrowEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		ShotOceanArrowEntity entityarrow = new ShotOceanArrowEntity(CaerulaArborModEntities.SHOT_OCEAN_ARROW.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(true);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.crossbow.shoot")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static ShotOceanArrowEntity shoot(LivingEntity entity, LivingEntity target) {
		ShotOceanArrowEntity entityarrow = new ShotOceanArrowEntity(CaerulaArborModEntities.SHOT_OCEAN_ARROW.get(), entity, entity.level());
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 2f, 1F);
		entityarrow.setBaseDamage(entity.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2);
		entityarrow.setKnockback(0);
		entityarrow.setCritArrow(true);
		entity.level().addFreshEntity(entityarrow);
		entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1, 1f / (entity.getRandom().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
	public static ShotOceanArrowEntity shoot(LivingEntity entity, LivingEntity target, boolean inSkill) {
		ShotOceanArrowEntity arrow = new ShotOceanArrowEntity(CaerulaArborModEntities.SHOT_OCEAN_ARROW.get(), entity, entity.level());
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		if (inSkill) arrow.setPos(arrow.getX(), arrow.getY() - 0.5, arrow.getZ());
		double shootY = dy - arrow.getY() + Math.hypot(dx, dz) * 0.2F;
		if (inSkill) shootY = target.getY()+ target.getBbHeight()* 0.5 - arrow.getY();
		
		arrow.setBaseDamage(entity.getAttributeValue(Attributes.ATTACK_DAMAGE) / 1.5);
		arrow.setKnockback(0);

		if (inSkill){
			arrow.setBaseDamage(arrow.getBaseDamage() * 1.35);
			arrow.getPersistentData().putBoolean("batallionSkill", true);
			arrow.setPierceLevel((byte) 8);
			entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
				getSound(SPECIAL), SoundSource.NEUTRAL, 1.0F, entity.getRandom().nextFloat() * 0.5f + 0.75f);
			arrow.setCritArrow(true);
			arrow.setNoGravity(true);
			arrow.shoot(dx, shootY, dz, 2f, 0.1F);
		} else {
			entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
				SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1, 1f / (entity.getRandom().nextFloat() * 0.5f + 1));
			arrow.shoot(dx, shootY, dz, 1.5f, 1F);
		}
		entity.level().addFreshEntity(arrow);
		return arrow;
	}

	public static SoundEvent getSound(ResourceLocation res){
    	return CaerulaArborModSounds.CROSSBOW_SPECIAL_SHOT.get();
    }
    public static final ResourceLocation SPECIAL = new ResourceLocation("caerula_arbor_reunite:crossbow_special_shot");
}
