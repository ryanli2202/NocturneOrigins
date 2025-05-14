package com.wraith.nocturneorigins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;

public class SkinListWidget extends AbstractSelectionList<SkinEntry> {

    private final Screen parent;

    public SkinListWidget(Minecraft minecraft, int width, int height, int top, int bottom, int itemHeight, Screen parent) {
        super(minecraft, width, height, top, bottom, itemHeight);
        this.parent = parent;
    }

    public void addSkinEntry(SkinEntry entry) {
        this.addEntry(entry);
    }

    public void select(SkinEntry entry) {
        this.setSelected(entry);
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width - 6;
    }

    @Override
    public int getRowWidth() {
        // Reduce horizontal padding on each entry
        return this.width - 12;
    }

    @Override
    protected boolean isFocused() {
        return true;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }
}