package com.pj.game.cheats.entity;

import java.util.Date;

/**
 *
 * @author Sherif
 */
public class GameInformation {

    int id;
    String gameTitle;
    String gameSubTitle;
    String gamePublisher;
    String gameGenre;
    String gameVersion;
    Date releasedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public void setGameGenre(String gameGenre) {
        this.gameGenre = gameGenre;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameName) {
        this.gameTitle = gameName;
    }

    public String getGameSubTitle() {
        return gameSubTitle;
    }

    public void setGameSubTitle(String gameSubTitle) {
        this.gameSubTitle = gameSubTitle;
    }

    public String getGamePublisher() {
        return gamePublisher;
    }

    public void setGamePublisher(String gamePublisher) {
        this.gamePublisher = gamePublisher;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }
}
