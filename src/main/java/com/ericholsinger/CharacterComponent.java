package com.ericholsinger;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.ericholsinger.enums.Animation;
import com.ericholsinger.enums.Direction;
import javafx.scene.Node;

import java.util.HashMap;

/**
 * Created by eric on 4/14/18.
 */
public class CharacterComponent extends Component {
    private int moveEvents = 0;

    private Direction direction;

    private AnimatedTexture texture;

    private HashMap<Direction, AnimationChannel> animWalk;
    private HashMap<Direction, AnimationChannel> animIdle;

    private CharacterComponent() {
        // no default constructor
    }

    public CharacterComponent(String spriteSheetName) {
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

    public Node getNodeWithBBox() {
        return texture;
    }

    @Override
    public void onAdded() {
        entity.setView(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        if (isMoving()) {
            texture.loopAnimationChannel(animWalk.get(direction));
        } else {
            texture.loopAnimationChannel(animIdle.get(direction));
        }
    }

    public void walk(Direction direction) {
        setDirection(direction);
        addMoveEvent();
    }

    public void idle() {
        subtractMoveEvent();
    }

    public void idle(Direction direction) {
        setDirection(direction);
        idle();
    }

    public boolean isMoving() {
        return this.moveEvents > 0;
    }

    private void addMoveEvent() {
        ++this.moveEvents;
    }

    private void subtractMoveEvent() {
        // never go below zero
        if (this.moveEvents > 0) {
            --this.moveEvents;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}