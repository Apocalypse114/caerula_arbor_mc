package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class KillFuncProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		execute(null, world, x, y, z, damagesource, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		if (damagesource == null || entity == null)
			return;
		boolean validweapon = false;
		double dama = 0;
		ItemStack weapon = ItemStack.EMPTY;
		String rname = "";
		Entity sourceent = null;
		if (!event.isCanceled()) {
			sourceent = damagesource.getEntity();
			if (IsSeabornProcedure.execute(entity)) {
				if (OnSublimationDiedProcedure.execute(world, x, y, z, entity)) {
					if (event != null && event.isCancelable()) {
						event.setCanceled(true);
					}
					return;
				}
				if (world.getLevelData().getGameRules().getBoolean(CaerulaArborModGameRules.NATURAL_EVOLUTION)) {
					if (!world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 128, 128, 128), e -> true).isEmpty()) {
						if (!world.isClientSide()) {
							CaerulaArborModVariables.MapVariables.get(world).evo_point_breed = CaerulaArborModVariables.MapVariables.get(world).evo_point_breed + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.1;
							CaerulaArborModVariables.MapVariables.get(world).syncData(world);
							UpgradeBreedProcedure.execute(world);
							UpgradeSilenceProcedure.execute(world, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.1);
							UpgradeSublimationProcedure.execute(world, GetPointFromHeightProcedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.1));
						}
					}
				}
			}
			if (IsSeanornEliteProcedure.execute(entity)) {
				if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
					if (Math.random() < 0.1) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.amethyst_cluster.break")), SoundSource.AMBIENT, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.amethyst_cluster.break")), SoundSource.AMBIENT, 1, 1, false);
							}
						}
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z,
									new ItemStack((ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("caerula_arbor_reunite:common_relics"))).getRandomElement(RandomSource.create()).orElseGet(() -> Items.AIR))));
							entityToSpawn.setPickUpDelay(10);
							entityToSpawn.setUnlimitedLifetime();
							_level.addFreshEntity(entityToSpawn);
						}
					}
				}
			}
			if (sourceent instanceof Player) {
				OnEchoPlayerKillProcedure.execute(world, x, y, z, entity, sourceent);
			}
			GeneSampleDropProcedure.execute(world, x, y, z, entity, sourceent);
		}
	}
}
