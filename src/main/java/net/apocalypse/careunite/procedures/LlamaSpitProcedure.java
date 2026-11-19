package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.OceanizedLlamaEntity;
import net.apocalypse.careunite.entity.ElectShootEntity;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.util.Mth;

public class LlamaSpitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
				SoundEvents.LLAMA_SPIT, SoundSource.HOSTILE, 1.0F, 
				0.9F + entity.level().getRandom().nextFloat() * 0.2F);
		if (entity instanceof OceanizedLlamaEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedLlamaEntity.DATA_CHARGED)) {
			Level projectileLevel = entity.level();
			if (!projectileLevel.isClientSide() && entity instanceof LivingEntity _shootFrom) {
				LivingEntity pSpitter = _shootFrom;
				AbstractArrow entityToSpawn = new ElectShootEntity(CaerulaArborModEntities.ELECT_SHOOT.get(), projectileLevel);
				entityToSpawn.setPos(
				 	pSpitter.getX() - 
				 		(double)(pSpitter.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(pSpitter.yBodyRot * ((float)Math.PI / 180F)), 
				 	pSpitter.getEyeY() - (double)0.1F, 
				 	pSpitter.getZ() + 
				 		(double)(pSpitter.getBbWidth() + 1.0F) * 0.5D * (double)Mth.cos(pSpitter.yBodyRot * ((float)Math.PI / 180F)));
				entityToSpawn.setOwner(_shootFrom);
				entityToSpawn.setBaseDamage(4);
				entityToSpawn.setKnockback(0);
				entityToSpawn.setSilent(true);
				entityToSpawn.setPierceLevel((byte)4);
				entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y+0.15, _shootFrom.getLookAngle().z, 2, 15f);
				projectileLevel.addFreshEntity(entityToSpawn);
			}
		} else {
			llamaSpit(entity);
		}
	}

	public static void llamaSpit(Entity entity){
		if (entity instanceof LivingEntity pSpitter){
			LlamaSpit llamaspit = new LlamaSpit(EntityType.LLAMA_SPIT, entity.level());
			 llamaspit.setPos(
			 	pSpitter.getX() - 
			 		(double)(pSpitter.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(pSpitter.yBodyRot * ((float)Math.PI / 180F)), 
			 	pSpitter.getEyeY() - (double)0.1F, 
			 	pSpitter.getZ() + 
			 		(double)(pSpitter.getBbWidth() + 1.0F) * 0.5D * (double)Mth.cos(pSpitter.yBodyRot * ((float)Math.PI / 180F)));
			llamaspit.setOwner(entity);
			Vec3 look = entity.getLookAngle();
			double d0 = look.x;
			double d1 = look.y;
			double d2 = look.z;
			double d3 = Math.sqrt(d0 * d0 + d2 * d2) * (double)0.2F;
			llamaspit.shoot(d0, d1 + d3, d2, 1.5F, 10.0F);
			entity.level().addFreshEntity(llamaspit);
		}
	}
}
