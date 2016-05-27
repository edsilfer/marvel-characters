package br.com.hole19.marvel.ui.controller;

import br.com.hole19.marvel.ui.commons.util.managers.ToolbarManager;

/**
 * Created by edgar on 08-May-16.
 */
public class ActivityBasicConfiguration {
    private Integer contentView;
    private Integer menuFile;
    private Integer toolbartTextColor;
    private Integer navigationIcon;
    private Boolean defaultReturnBehavior;
    private String title;
    private ToolbarManager.OnIconClicked onNavigationItemClicked;

    public Integer getContentView() {
        return contentView;
    }

    public void setContentView(Integer contentView) {
        this.contentView = contentView;
    }

    public Integer getMenuFile() {
        return menuFile;
    }

    public void setMenuFile(Integer menuFile) {
        this.menuFile = menuFile;
    }

    public Integer getToolbartTextColor() {
        return toolbartTextColor;
    }

    public void setToolbartTextColor(Integer toolbartTextColor) {
        this.toolbartTextColor = toolbartTextColor;
    }

    public Integer getNavigationIcon() {
        return navigationIcon;
    }

    public void setNavigationIcon(Integer navigationIcon) {
        this.navigationIcon = navigationIcon;
    }

    public Boolean getDefaultReturnBehavior() {
        return defaultReturnBehavior;
    }

    public void setDefaultReturnBehavior(Boolean defaultReturnBehavior) {
        this.defaultReturnBehavior = defaultReturnBehavior;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ToolbarManager.OnIconClicked getOnNavigationItemClicked() {
        return onNavigationItemClicked;
    }

    public void setOnNavigationItemClicked(ToolbarManager.OnIconClicked onNavigationItemClicked) {
        this.onNavigationItemClicked = onNavigationItemClicked;
    }
}
