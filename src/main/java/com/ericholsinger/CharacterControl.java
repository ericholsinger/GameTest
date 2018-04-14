package com.ericholsinger;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.entity.Control;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

/**
 * Created by eric on 4/14/18.
 */
public class CharacterControl extends Control {
    private int speed = 0;
    private String spriteSheetName = "";

    private final int FRAMES_PER_ROW_IDLE = 8;
    private final int FRAME_WIDTH = 64;
    private final int FRAME_HEIGHT = 64;


    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalkSide, animWalkDown, animWalkUp;

    public CharacterControl(String spriteSheetName) {
        this.spriteSheetName = spriteSheetName;

        Rectangle2D idleArea = new Rectangle2D(0, 6 * FRAME_HEIGHT, 8 * FRAME_WIDTH, FRAME_HEIGHT);

        Texture spriteSheet = FXGL.getAssetLoader()
                .loadTexture(spriteSheetName)
                .subTexture(idleArea);

        texture = FXGL.getAssetLoader()
                .loadTexture(spriteSheetName)
                .subTexture(idleArea)
                .toAnimatedTexture(8, Duration.seconds(1));

        animIdle = texture.getAnimationChannel();
        texture.start(FXGL.getApp().getStateMachine().getPlayState());

    }

    @Override
    public void onAdded(Entity entity) {
        entity.setView(texture);
    }

    @Override
    public void onUpdate(Entity entity, double tpf) {
        entity.translateX(speed * tpf);

        if (speed == 0) {
            texture.setAnimationChannel(animIdle);
        }
    }
}
