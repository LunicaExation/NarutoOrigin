package com.tyler.narutoorigin.ranks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScorePlayerTeam;
import java.util.HashMap;
import java.util.Map;

public class RankManager {
    private final Map<String, Rank> playerRanks = new HashMap<>();

    public void assignRank(EntityPlayer player, Rank rank) {
        playerRanks.put(player.getName(), rank);
        updatePlayerScoreboard(player, rank);
    }

    public void removeRank(EntityPlayer player) {
        playerRanks.remove(player.getName());
        removePlayerScoreboard(player);
    }

    public Rank getRank(EntityPlayer player) {
        return playerRanks.get(player.getName());
    }

    public String getDisplayNameWithRank(EntityPlayer player) {
        Rank rank = getRank(player);
        if (rank != null) {
            return rank.getDisplayName() + " " + player.getName();
        }
        return player.getName();
    }

    private void updatePlayerScoreboard(EntityPlayer player, Rank rank) {
        Scoreboard scoreboard = player.getWorldScoreboard();
        ScorePlayerTeam team = scoreboard.getTeam(rank.getName());

        if (team == null) {
            team = scoreboard.createTeam(rank.getName());
            team.setPrefix(rank.getColor().toString() + rank.getName() + " ");
            team.setSuffix(""); // Optional: kann genutzt werden, um zusätzliche Informationen hinzuzufügen
        }

        // Entferne den Spieler aus anderen Teams
        scoreboard.removePlayerFromTeams(player.getName());

        // Füge den Spieler dem neuen Team hinzu
        scoreboard.addPlayerToTeam(player.getName(), team.getName());
    }

    private void removePlayerScoreboard(EntityPlayer player) {
        Scoreboard scoreboard = player.getWorldScoreboard();
        scoreboard.removePlayerFromTeams(player.getName());
    }
}
