package com.pj.game.cheats.entity;

import java.sql.Date;

/**
 *
 * @author Sherif
 */
public class GameInstruction {

    int id;
    int gameId;
    String gameInstruction;
    Date lastUpdate;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameInstruction() {
        return gameInstruction;
    }

    public void setGameInstruction(String gameInstruction) {
        this.gameInstruction = gameInstruction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
