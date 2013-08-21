package com.pj.game.cheats.entity;

/**
 *
 * @author Sherif(prajin)
 */
public class GameCheatCode {

    int id;
    int gameId;
    String pcCheatCode;
    String cheatCodeResult;
    String cheatCodeRemark;

    public String getCheatCodeRemark() {
        return cheatCodeRemark;
    }

    public void setCheatCodeRemark(String cheatCodeRemark) {
        this.cheatCodeRemark = cheatCodeRemark;
    }

    public String getCheatCodeResult() {
        return cheatCodeResult;
    }

    public void setCheatCodeResult(String cheatCodeResult) {
        this.cheatCodeResult = cheatCodeResult;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPcCheatCode() {
        return pcCheatCode;
    }

    public void setPcCheatCode(String pcCheatCode) {
        this.pcCheatCode = pcCheatCode;
    }
}
