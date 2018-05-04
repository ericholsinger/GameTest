package com.ericholsinger;

import com.almasb.fxgl.app.FXGL;
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
    public final int SPEED = 150;

    private int moveY = 0; // vertical movement
    private int moveX = 0; // horizontal movement

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

        // iterate over directions and populate animations for walk and stop
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
            getEntity().translateX(tpf * moveX);
            getEntity().translateY(tpf * moveY);
        }
    }

    public void walk(Direction direction) {
        updateMovement(direction, 1);
        updateDirection(direction);
        updateAnimation();
    }

    public void stop(Direction direction) {
        updateMovement(direction, 0);
        updateDirection(direction);
        updateAnimation();
    }

    private void updateMovement(Direction direction, int factor) {
        switch (direction) {
            case NORTH:
                moveY = factor * -SPEED;
                break;
            case SOUTH:
                moveY = factor * SPEED;
                break;
            case WEST:
                moveX = factor * -SPEED;
                break;
            case EAST:
                moveX = factor * SPEED;
                break;
        }
    }

    private void updateDirection(Direction direction) {
        // prioritizes horizontal (EAST-WEST) over vertical
        if (moveX > 0) {
            setDirection(Direction.EAST);
        } else if (moveX < 0) {
            setDirection(Direction.WEST);
        } else if (moveY > 0) {
            setDirection(Direction.SOUTH);
        } else if (moveY < 0) {
            setDirection(Direction.NORTH);
        } else {
            // not moving, change to direction
            setDirection(direction);
        }
    }

    private void updateAnimation() {
        if (isMoving()) {
            texture.loopAnimationChannel(animWalk.get(getDirection()));
        } else {
            texture.loopAnimationChannel(animIdle.get(getDirection()));
        }
    }

    private boolean isMoving() {
        return (moveX != 0 || moveY != 0);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
