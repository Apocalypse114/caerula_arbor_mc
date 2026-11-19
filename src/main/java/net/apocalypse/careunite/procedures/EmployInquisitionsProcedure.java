package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.util.Mth;

public class EmployInquisitionsProcedure {

	private static boolean isCreative(Entity entity){
		if (entity instanceof Player player){
			return player.isCreative();
		}
		return false;
	}
	
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity) {
		if (direction == null || entity == null)
			return InteractionResult.PASS;
		ItemStack treaty = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).copy();
		if (entity instanceof Player _plrCldCheck1 && _plrCldCheck1.getCooldowns().isOnCooldown(treaty.getItem()))
			return InteractionResult.PASS;
		boolean isCreative = isCreative(entity);
		EntityType<?> type = null;
		boolean shouldFlame = false;
		int exp = entity instanceof Player _plr ? _plr.experienceLevel : 0;
		int expCost = 0;
		if (treaty.getItem() == CaerulaArborModItems.TREATY_COPPER.get()) {
			expCost = 1;
			type = CaerulaArborModEntities.JUNIOR_WARRIOR_PRIEST.get();
		} else if (treaty.getItem() == CaerulaArborModItems.TREATY_IRON.get()) {
			expCost = 2;
			type = CaerulaArborModEntities.WARRIOR_PRIEST.get();
		} else if (treaty.getItem() == CaerulaArborModItems.TREATY_GOLD.get()) {
			expCost = 3;
			int r = Mth.nextInt(entity.level().random, 0, 2);
			if (r == 0) type = CaerulaArborModEntities.CORRECTIONAL_PHALAX_VANGUARD.get();
			else if (r == 1) type = CaerulaArborModEntities.CORRECTIONAL_PHALANXY_INFANTRY.get();
			else if (r == 2) type = CaerulaArborModEntities.PENAL_BATALLION_CROSSBOWMAN.get();
		} else if (treaty.getItem() == CaerulaArborModItems.EMERALD_TREATY.get()) {
			expCost = 4;
			type = CaerulaArborModEntities.TRIBUNAL_HEALER.get();
		} else if (treaty.getItem() == CaerulaArborModItems.ENCHANTED_EMERALD_TREATY.get()) {
			expCost = 5;
			type = CaerulaArborModEntities.LUMEN.get();
		} else if (treaty.getItem() == CaerulaArborModItems.TREATY_DIAMOND.get()) {
			expCost = 4;
			type = world.getRandom().nextFloat() < 0.5 ? CaerulaArborModEntities.IRENE.get(): CaerulaArborModEntities.TYPHOEUS.get();
			shouldFlame = true;
		}
 else if (treaty.getItem() == CaerulaArborModItems.TREATY_NETHERITE.get()) {
			expCost = 5;
			type = CaerulaArborModEntities.ISHMAEL.get();
			shouldFlame = true;
		}
		if (entity instanceof Player player && type != null && world instanceof ServerLevel _level){
			if (exp >= expCost || isCreative){
				if (shouldFlame) _level.sendParticles(
					(SimpleParticleType) (CaerulaArborModParticleTypes.PURPLE_FLAME.get()), 
					(x + 0.5), (y + 1.5), (z + 0.5), 32, 0.75, 0.75, 0.75, 0.1);
				Entity entityToSpawn = type.spawn(_level, 
					BlockPos.containing(x + direction.getStepX(), y + direction.getStepY(), z + direction.getStepZ()),
					MobSpawnType.MOB_SUMMONED);
				if (!isCreative){
					player.giveExperienceLevels( -1 * expCost);
				}
				player.getMainHandItem().shrink(1);
				player.getCooldowns().addCooldown(treaty.getItem(), 5);
				if (entityToSpawn instanceof TamableAnimal t)
					t.tame(player);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
