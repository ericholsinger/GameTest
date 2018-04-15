package com.ericholsinger;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.entity.Control;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * Created by eric on 4/14/18.
 */
public class CharacterControl extends Control {
    private int speed = 0;

    private final int FRAMES_PER_WALK = 9;
    private final int FRAMES_PER_IDLE = 1;
    private final int WALK_ROW = 8;
    private final HashMap<Direction, Integer> WALK_ROW_OFFSET = new HashMap<Direction, Integer>() {{
        put(Direction.NORTH, 0);
        put(Direction.WEST, 1);
        put(Direction.SOUTH, 2);
        put(Direction.EAST, 3);
    }};
    private final int FRAME_WIDTH = 64;
    private final int FRAME_HEIGHT = 64;

    private final Duration DURATION_WALK = Duration.seconds(.25);
    private final Duration DURATION_IDLE = Duration.seconds(.5);


    private Direction direction;
    private boolean isMoving = false;

    private AnimatedTexture texture;

    private HashMap<Direction, AnimationChannel> animWalk;
    private HashMap<Direction, AnimationChannel> animIdle;

    public CharacterControl(String spriteSheetName) {
        animIdle = new HashMap<>();
        animWalk = new HashMap<>();
        direction = Direction.SOUTH;

        Texture spriteSheet = FXGL.getAssetLoader()
                .loadTexture(spriteSheetName);

        Rectangle2D spriteRow; // rectangle cutout of each sprite animation

        // load NORTH facing walk sprites
        spriteRow = new Rectangle2D(0
                , (WALK_ROW + WALK_ROW_OFFSET.get(Direction.NORTH)) * FRAME_HEIGHT
                , FRAMES_PER_WALK * FRAME_WIDTH
                , FRAME_HEIGHT);
        texture = spriteSheet
                .subTexture(spriteRow)
                .toAnimatedTexture(FRAMES_PER_WALK, DURATION_WALK);
        animWalk.put(Direction.NORTH, texture.getAnimationChannel());

        // first frame is NORTH facing idle frame
        spriteRow = new Rectangle2D(0
                , (WALK_ROW + WALK_ROW_OFFSET.get(Direction.NORTH)) * FRAME_HEIGHT
                , FRAMES_PER_IDLE * FRAME_WIDTH
                , FRAME_HEIGHT);
        texture = spriteSheet
                .subTexture(spriteRow)
                .toAnimatedTexture(FRAMES_PER_IDLE, DURATION_IDLE);
        animIdle.put(Direction.NORTH, texture.getAnimationChannel());

        // load EAST facing walk sprites
        spriteRow = new Rectangle2D(0
                , (WALK_ROW + WALK_ROW_OFFSET.get(Direction.EAST)) * FRAME_HEIGHT
                , FRAMES_PER_WALK * FRAME_WIDTH
                , FRAME_HEIGHT);
        texture = spriteSheet
                .subTexture(spriteRow)
                .toAnimatedTexture(FRAMES_PER_WALK, DURATION_WALK);
        animWalk.put(Direction.EAST, texture.getAnimationChannel());

        // first frame is EAST facing idle frame
        spriteRow = new Rectangle2D(0
                , (WALK_ROW + WALK_ROW_OFFSET.get(Direction.EAST)) * FRAME_HEIGHT
                , FRAMES_PER_IDLE * FRAME_WIDTH
                , FRAME_HEIGHT);
        texture = spriteSheet
                .subTexture(spriteRow)
                .toAnimatedTexture(FRAMES_PER_IDLE, DURATION_IDLE);
        animIdle.put(Direction.EAST, texture.getAnimationChannel());

        // load SOUTH facing walk sprites
        spriteRow = new Rectangle2D(0
                , (WALK_ROW + WALK_ROW_OFFSET.get(Direction.SOUTH)) * FRAME_HEIGHT
                , FRAMES_PER_WALK * FRAME_WIDTH
                , FRAME_HEIGHT);
        texture = spriteSheet
                .subTexture(spriteRow)
                .toAnimatedTexture(FRAMES_PER_WALK, DURATION_WALK);
        animWalk.put(Direction.SOUTH, texture.getAnimationChannel());

        // first frame is SOUTH facing idle frame
        spriteRow = new Rectangle2D(0
                , (WALK_ROW + WALK_ROW_OFFSET.get(Direction.SOUTH)) * FRAME_HEIGHT
                , FRAMES_PER_IDLE * FRAME_WIDTH
                , FRAME_HEIGHT);
        texture = spriteSheet
                .subTexture(spriteRow)
                .toAnimatedTexture(FRAMES_PER_IDLE, DURATION_IDLE);
        animIdle.put(Direction.SOUTH, texture.getAnimationChannel());

        // load WEST facing walk sprites
        spriteRow = new Rectangle2D(0
                , (WALK_ROW + WALK_ROW_OFFSET.get(Direction.WEST)) * FRAME_HEIGHT
                , FRAMES_PER_WALK * FRAME_WIDTH
                , FRAME_HEIGHT);
        texture = spriteSheet
                .subTexture(spriteRow)
                .toAnimatedTexture(FRAMES_PER_WALK, DURATION_WALK);
        animWalk.put(Direction.WEST, texture.getAnimationChannel());

        // first frame is WEST facing idle frame
        spriteRow = new Rectangle2D(0
                , (WALK_ROW + WALK_ROW_OFFSET.get(Direction.WEST)) * FRAME_HEIGHT
                , FRAMES_PER_IDLE * FRAME_WIDTH
                , FRAME_HEIGHT);
        texture = spriteSheet
                .subTexture(spriteRow)
                .toAnimatedTexture(FRAMES_PER_IDLE, DURATION_IDLE);
        animIdle.put(Direction.WEST, texture.getAnimationChannel());

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
        this.isMoving = true;
    }

    public void idle(Direction direction) {
        this.direction = direction;
        this.isMoving = false;
    }

    public boolean isMoving() {
        return this.isMoving;
    }
}
