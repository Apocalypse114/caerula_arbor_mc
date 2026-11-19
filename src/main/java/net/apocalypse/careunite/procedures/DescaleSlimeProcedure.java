package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.NetherseaSlimeEntity;
import net.apocalypse.careunite.procedures.SlimeAttrModifyProcedure;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;

public class DescaleSlimeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		int size = entity instanceof NetherseaSlimeEntity _datEntI ? _datEntI.getEntityData().get(NetherseaSlimeEntity.DATA_SIZE) : 0;
		if (size > 1) {
			size = (int) (size * 0.5);
			Vec3 pos = new Vec3(x, y, z);
			if (world instanceof ServerLevel _level) {
				RandomSource levelRandom = _level.getRandom();
				int t = Mth.nextInt(levelRandom, 2, 4);
				for (int index0 = 0; index0 < t; index0++) {
					Vec3 offset = new Vec3(Mth.nextDouble(levelRandom, -1, 1), 0, Mth.nextDouble(levelRandom, -1, 1));
					Entity entityToSpawn = CaerulaArborModEntities.NETHERSEA_SLIME.get().create(_level);
					if (entityToSpawn instanceof NetherseaSlimeEntity slime){
						slime.setPos(pos.add(offset));
						slime.getEntityData().set(NetherseaSlimeEntity.DATA_SIZE, size);
						//SlimeAttrModifyProcedure.execute(slime);
						slime.setYRot(world.getRandom().nextFloat() * 360F);
						_level.addFreshEntity(slime);
					}
				}
			}
		} else if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			if (world instanceof ServerLevel _level) {
				ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.TRAIL_CREAM.get()));
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
		}
	}
}
