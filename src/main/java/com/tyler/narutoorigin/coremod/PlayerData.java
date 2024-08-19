package com.tyler.narutoorigin.coremod;

import java.io.Serializable;

public class PlayerData implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean hasClaimedOrigin;
    private boolean hasUsedMakeMeAShinobi;
    private String name;    // Property to store the player's name
    private String clan;    // Property to store the player's clan
    private String kkg;
    private String dojutsu;

    // Constructor with name parameter
    public PlayerData(String name) {
        this.hasClaimedOrigin = false;
        this.hasUsedMakeMeAShinobi = false;
        this.name = name;
        this.clan = "";
        this.kkg = "";
        this.dojutsu = "";
    }

    // Default constructor (if needed)
    public PlayerData() {
        this("");
    }

    public boolean hasClaimedOrigin() {
        return hasClaimedOrigin;
    }

    public void setHasClaimedOrigin(boolean hasClaimedOrigin) {
        this.hasClaimedOrigin = hasClaimedOrigin;
    }

    public boolean hasUsedMakeMeAShinobi() {
        return hasUsedMakeMeAShinobi;
    }

    public void setHasUsedMakeMeAShinobi(boolean hasUsedMakeMeAShinobi) {
        this.hasUsedMakeMeAShinobi = hasUsedMakeMeAShinobi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public String getKkg() {
        return kkg;
    }

    public void setKkg(String kkg) {
        this.kkg = kkg;
    }

    public String getDojutsu() {
        return dojutsu;
    }

    public void setDojutsu(String dojutsu) {
        this.dojutsu = dojutsu;
    }
}
