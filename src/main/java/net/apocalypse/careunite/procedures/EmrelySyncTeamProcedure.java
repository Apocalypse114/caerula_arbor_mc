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

public class EmrelySyncTeamProcedure {
	public static void execute(Entity former, Entity latter) {
		if (former instanceof Mob before && latter instanceof Mob after){
            Team team = before.getTeam();
            MinecraftServer server = after.getServer();
            if (server != null && team instanceof PlayerTeam playerTeam){
                server.getScoreboard().addPlayerToTeam(after.getScoreboardName(), playerTeam);
            }
        }
	}
}
