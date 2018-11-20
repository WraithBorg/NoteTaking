package com.zxu.demo.item;
/**
 * 定义菜单项类
 */
public class TuiCoolMenuItem {
    private String menuTitle;
    private int menuIcon;

    //构造方法
    public TuiCoolMenuItem(String menuTitle, int menuIcon) {
        this.menuTitle = menuTitle;
        this.menuIcon = menuIcon;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(int menuIcon) {
        this.menuIcon = menuIcon;
    }
}
