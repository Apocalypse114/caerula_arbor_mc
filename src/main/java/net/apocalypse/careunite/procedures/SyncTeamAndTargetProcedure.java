package net.apocalypse.careunite.procedures;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

import java.util.List;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

public class SyncTeamAndTargetProcedure {
	public static void execute(Entity former, Entity latter) {
		if (former instanceof Mob before && latter instanceof Mob after){
            LivingEntity target = before.getTarget();
            Team team = before.getTeam();
            if (target != null) after.setTarget(target);
            MinecraftServer server = after.getServer();
            String name = former.getDisplayName().getString();
            if (latter instanceof TamableAnimal && !name.equals(former.getType().getDescription().getString())){
            	latter.setCustomName(former.getDisplayName());
            }
            if (before instanceof TamableAnimal tamable 
	            && tamable.getOwner() instanceof Player player 
	            && after instanceof TamableAnimal ta){
            	ta.tame(player);
            }
            if (server != null && team instanceof PlayerTeam playerTeam){
                server.getScoreboard().addPlayerToTeam(after.getScoreboardName(), playerTeam);
            }
            if (before.level() instanceof ServerLevel s){
                AABB aabb = new AABB(before.position(), before.position()).inflate(24);
                List<Mob> mobs = s.getEntitiesOfClass(Mob.class, aabb, LivingEntity::isAlive);
                mobs.forEach( m ->{
                    if (m.getTarget() != null && m.getTarget().is(before)) m.setTarget(after);
                });
            }
        }
	}
}
