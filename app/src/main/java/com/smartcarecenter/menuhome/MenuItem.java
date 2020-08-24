/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package com.smartcarecenter.menuhome;

public class MenuItem {
    String badge;
    Integer img;
    String menuname;
    String show;
    public MenuItem(String badge, Integer img, String menuname, String show) {
        this.badge = badge;
        this.img = img;
        this.menuname = menuname;
        this.show = show;
    }



    public MenuItem() {
    }



    public String getBadge() {
        return this.badge;
    }

    public Integer getImg() {
        return this.img;
    }

    public String getMenuname() {
        return this.menuname;
    }

    public String getShow() {
        return this.show;
    }

    public void setBadge(String string2) {
        this.badge = string2;
    }

    public void setImg(Integer n) {
        this.img = n;
    }

    public void setMenuname(String string2) {
        this.menuname = string2;
    }

    public void setShow(String string2) {
        this.show = string2;
    }
}

