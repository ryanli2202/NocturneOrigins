package com.wraith.nocturneorigins.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wraith.nocturneorigins.character.BackstoryManager;
import com.wraith.nocturneorigins.character.BackstoryManager.Backstory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class FinalizeCharacterScreen extends Screen {

    private EditBox nameField;
    private int selectedBackstoryIndex = 0;
    private final Screen parent;
    private List<Backstory> backstories;

    public FinalizeCharacterScreen(Screen parent) {
        super(new TextComponent("Finalize Your Character"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        Minecraft mc = Minecraft.getInstance();
        this.backstories = BackstoryManager.getBackstories();

        int centerX = this.width / 2;
        int top = 30;

        nameField = new EditBox(this.font, centerX - 100, top, 200, 20, new TextComponent("Enter Name"));
        nameField.setMaxLength(32);
        this.addRenderableWidget(nameField);

        this.addRenderableWidget(new Button(centerX - 105, top + 30, 100, 20, new TextComponent("Previous"), b -> {
            selectedBackstoryIndex = (selectedBackstoryIndex - 1 + backstories.size()) % backstories.size();
        }));

        this.addRenderableWidget(new Button(centerX + 5, top + 30, 100, 20, new TextComponent("Next"), b -> {
            selectedBackstoryIndex = (selectedBackstoryIndex + 1) % backstories.size();
        }));

        this.addRenderableWidget(new Button(centerX - 50, this.height - 30, 100, 20, new TextComponent("Confirm"), b -> {
            String chosenName = nameField.getValue();
            Backstory selected = backstories.get(selectedBackstoryIndex);

            Player player = mc.player;
            if (player != null) {
                var tag = player.getPersistentData();
                tag.putString("ChosenName", chosenName);
                tag.putString("BackstoryID", selected.id);
            }

            mc.setScreen(null); // Done, return to game
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 10, 0xFFFFFF);
        drawCenteredString(poseStack, this.font, "Enter your character name:", this.width / 2, 15, 0xAAAAAA);
        nameField.render(poseStack, mouseX, mouseY, partialTicks);
        drawCenteredString(poseStack, this.font, "Choose your backstory:", this.width / 2, 50, 0xAAAAAA);
        if (!backstories.isEmpty()) {
            Backstory backstory = backstories.get(selectedBackstoryIndex);

            drawCenteredString(poseStack, this.font, new TextComponent("Backstory: " + backstory.title), this.width / 2, 60, 0xAAAAFF);
            drawWrappedText(poseStack, backstory.text, this.width / 2 - 120, 80, 240, 0xFFFFFF);
        }

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void drawWrappedText(PoseStack stack, String text, int x, int y, int width, int color) {
        List<FormattedCharSequence> lines = this.font.split(new TextComponent(text), width);
        for (int i = 0; i < lines.size(); ++i) {
            this.font.draw(stack, lines.get(i), x, y + i * 10, color);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button) || nameField.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (nameField.keyPressed(keyCode, scanCode, modifiers)) return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char c, int modifiers) {
        return nameField.charTyped(c, modifiers);
    }

    @Override
    public void tick() {
        nameField.tick();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}