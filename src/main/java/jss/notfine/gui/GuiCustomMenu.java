package jss.notfine.gui;

import jss.notfine.core.SettingsManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;

public class GuiCustomMenu extends GuiScreen {
    private final GuiScreen parentGuiScreen;
    private final String titleUnlocalized;
    private final Object[] settings;
    protected String screenTitle;

    private GuiListExtended optionsRowList;

    public GuiCustomMenu(GuiScreen parentGuiScreen, String titleUnlocalized, Object... settings) {
        this.parentGuiScreen = parentGuiScreen;
        this.titleUnlocalized = titleUnlocalized;
        this.settings = settings;
    }

    @Override
    public void initGui() {
        screenTitle = I18n.format(titleUnlocalized);
        buttonList.clear();
        buttonList.add(new GuiButton(200, width / 2 - 100, height - 27, I18n.format("gui.done")));

        optionsRowList = new GuiCustomSettingsRowList(mc, width, height, 32, height - 32, 25, settings);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled && button.id == 200) {
            if(!(parentGuiScreen instanceof GuiCustomMenu)) {
                saveSettings();
            }
            mc.displayGuiScreen(parentGuiScreen);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int originalScale = mc.gameSettings.guiScale;

        super.mouseClicked(mouseX, mouseY, mouseButton);
        optionsRowList.func_148179_a(mouseX, mouseY, mouseButton);

        if(mc.gameSettings.guiScale != originalScale) {
            ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            setWorldAndResolution(mc, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
        }
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int state) {
        int originalScale = mc.gameSettings.guiScale;

        super.mouseMovedOrUp(mouseX, mouseY, state);
        optionsRowList.func_148181_b(mouseX, mouseY, state);

        if(mc.gameSettings.guiScale != originalScale) {
            ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            setWorldAndResolution(mc, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        optionsRowList.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRendererObj, screenTitle, width / 2, 5, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if(keyCode == 1) {
            saveSettings();
        }
        super.keyTyped(typedChar, keyCode);
    }

    private void saveSettings() {
        mc.gameSettings.saveOptions();
        SettingsManager.settingsFile.saveSettings();
    }

}