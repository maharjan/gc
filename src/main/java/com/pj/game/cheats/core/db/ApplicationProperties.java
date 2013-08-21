package com.pj.game.cheats.core.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author prajin
 */
public class ApplicationProperties {

    private String server;
    private String user;
    private String password;
    private int port;
    private int dbCondition;
    private String driver;
    private String databaseName;
    private String trayToolTip;

    public String getTrayToolTip() {
        return trayToolTip;
    }

    public void setTrayToolTip(String trayToolTip) {
        this.trayToolTip = trayToolTip;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getDbCondition() {
        return dbCondition;
    }

    public void setDbCondition(int dbCondition) {
        this.dbCondition = dbCondition;
    }

    public void initPropFile() {
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("application.properties");
            props.load(fis);
            server = props.getProperty("server").trim();
            user = props.getProperty("username").trim();
            password = props.getProperty("password").trim();
            String port1 = props.getProperty("port").trim();
            port = Integer.parseInt(port1);
            driver = props.getProperty("driver").trim();
            databaseName = props.getProperty("databaseName").trim();
            String db = props.getProperty("db").trim();
            dbCondition = Integer.parseInt(db);
//            trayToolTip = props.getProperty("toolTip");
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println(" ============================================= ");
            System.out.println(" ||     Couldn't read fron property file    ||");
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public void ApplicationProperties() {
        initPropFile();
    }
}
