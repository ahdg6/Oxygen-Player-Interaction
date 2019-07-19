package austeretony.oxygen_interaction.client.gui.interaction;

import java.util.UUID;

import austeretony.alternateui.screen.core.AbstractGUIScreen;
import austeretony.alternateui.screen.core.AbstractGUISection;
import austeretony.alternateui.screen.core.GUIBaseElement;
import austeretony.alternateui.screen.core.GUIWorkspace;
import austeretony.oxygen.common.api.OxygenGUIHelper;
import austeretony.oxygen.common.main.OxygenMain;
import austeretony.oxygen_interaction.common.main.InteractionMain;
import net.minecraft.util.ResourceLocation;

public class InteractionGUIScreen extends AbstractGUIScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(OxygenMain.MODID, "textures/gui/interaction/background.png");

    private final UUID playerUUID;

    protected InteractionGUISection mainSection;

    private boolean initialized;

    public InteractionGUIScreen(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    @Override
    protected GUIWorkspace initWorkspace() {
        return new GUIWorkspace(this, 120, 120);
    }

    @Override
    protected void initSections() {
        this.mainSection = new InteractionGUISection(this);
        this.getWorkspace().initSection(this.mainSection);        
    }

    @Override
    protected AbstractGUISection getDefaultSection() {
        return this.mainSection;
    }

    @Override
    public void handleElementClick(AbstractGUISection section, GUIBaseElement element) {}

    @Override
    protected boolean doesGUIPauseGame() {
        return false;
    }

    @Override
    public void updateScreen() {    
        super.updateScreen();
        if (!this.initialized//reduce map calls
                && OxygenGUIHelper.isNeedSync(InteractionMain.PLAYER_INTERACTION_MENU_SCREEN_ID)
                && OxygenGUIHelper.isScreenInitialized(InteractionMain.PLAYER_INTERACTION_MENU_SCREEN_ID)
                && OxygenGUIHelper.isDataRecieved(InteractionMain.PLAYER_INTERACTION_MENU_SCREEN_ID)) {
            this.initialized = true;
            OxygenGUIHelper.resetNeedSync(InteractionMain.PLAYER_INTERACTION_MENU_SCREEN_ID);
            this.mainSection.initActions();
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        OxygenGUIHelper.resetNeedSync(InteractionMain.PLAYER_INTERACTION_MENU_SCREEN_ID);
        OxygenGUIHelper.resetScreenInitialized(InteractionMain.PLAYER_INTERACTION_MENU_SCREEN_ID);
        OxygenGUIHelper.resetDataRecieved(InteractionMain.PLAYER_INTERACTION_MENU_SCREEN_ID);
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }
}
