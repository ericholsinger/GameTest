package com.ericholsinger;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.entity.Control;
import com.ericholsinger.enums.Animation;
import com.ericholsinger.enums.Direction;

import java.util.HashMap;

/**
 * Created by eric on 4/14/18.
 */
public class CharacterControl extends Control {
    private int moveEvents = 0;

    private Direction direction;

    private AnimatedTexture texture;

    private HashMap<Direction, AnimationChannel> animWalk;
    private HashMap<Direction, AnimationChannel> animIdle;

    public CharacterControl(String spriteSheetName) {
        animIdle = new HashMap<>();
        animWalk = new HashMap<>();

        direction = Direction.SOUTH; // initial direction

        // iterate over directions and populate animations for walk and idle
        for (Direction direction : Direction.values()) {
            animWalk.put(direction,
                    CharacterSpriteSheet.getAnimationChannel(spriteSheetName,
                        Animation.WALK,
                        direction)
            );

            animIdle.put(direction,
                    CharacterSpriteSheet.getAnimationChannel(spriteSheetName,
                            Animation.IDLE,
                            direction)
            );
        }

        texture = new AnimatedTexture(animIdle.get(direction));

        texture.start(FXGL.getApp().getStateMachine().getPlayState());
    }

    @Override
    public void onAdded(Entity entity) {
        entity.setView(texture);
    }

    @Override
    public void onUpdate(Entity entity, double tpf) {
        if (isMoving()) {
            texture.setAnimationChannel(animWalk.get(direction));
        } else {
            texture.setAnimationChannel(animIdle.get(direction));
        }
    }

    public void walk(Direction direction) {
        this.direction = direction;
        ++this.moveEvents;
    }

    public void idle() {
        --this.moveEvents;
    }

    public boolean isMoving() {
        return this.moveEvents > 0;
    }
}
