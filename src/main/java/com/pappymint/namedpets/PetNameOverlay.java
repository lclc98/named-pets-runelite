package com.pappymint.namedpets;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Actor;
import net.runelite.api.Point;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import java.awt.*;
import javax.inject.Inject;

public class PetNameOverlay extends Overlay
{
    private static final String CONFIG_GROUP = "namedPets";
    private final NamedPetsConfig pluginConfig;

    @Inject
    private ConfigManager configManager;

    @Inject
    private Client client;

    @Inject
    private PetNameOverlay(NamedPetsConfig pluginConfig)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);

        this.pluginConfig = pluginConfig;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        NPC follower = client.getFollower();
        if (follower == null) {
            return null;
        }

        renderFollowingPetName(graphics, follower, follower.getId());
        return null;
    }

    private void renderFollowingPetName(Graphics2D graphics, Actor petActor, int petId) {
        // Gets stored pet name from config manager
        String followingPetName = configManager.getConfiguration(CONFIG_GROUP, String.valueOf(petId));
        // Default config height to 0 if it isnt between 1 - 100
        int customHeightIncrease = pluginConfig.getCustomPosition() > 0 && pluginConfig.getCustomPosition() <= 100 ? pluginConfig.getCustomPosition() : 0;
        Point petNameLocation = petActor.getCanvasTextLocation(graphics, followingPetName, petActor.getModelHeight() + customHeightIncrease);

        if (petNameLocation != null) {
            OverlayUtil.renderTextLocation(graphics, petNameLocation, followingPetName, Color.PINK);
        }
    }
}
