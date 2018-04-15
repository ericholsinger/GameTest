package com.ericholsinger;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.ericholsinger.enums.Animation;
import com.ericholsinger.enums.Direction;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * Created by eric on 4/15/18.
 *
 * holds information about how to cut up the spritesheet based on the animation type and direction
 */
public class CharacterSpriteSheet {

    private static final HashMap<Animation, Integer> ROWBEGIN = new HashMap<Animation, Integer>() {{
        put(Animation.WALK, 8);
        put(Animation.IDLE, 8);
    }};

    private static final HashMap<Animation, Integer> COLBEGIN = new HashMap<Animation, Integer>() {{
        put(Animation.WALK, 0);
        put(Animation.IDLE, 0);
    }};

    private static final HashMap<Animation, Integer> FRAMES = new HashMap<Animation, Integer>() {{
        put(Animation.WALK, 9);
        put(Animation.IDLE, 1);
    }};

    private static final HashMap<Direction, Integer> WALK_ROW_OFFSET = new HashMap<Direction, Integer>() {{
        put(Direction.NORTH, 0);
        put(Direction.WEST, 1);
        put(Direction.SOUTH, 2);
        put(Direction.EAST, 3);
    }};

    private static final HashMap<Animation, Duration> DURATION = new HashMap<Animation, Duration>() {{
        put(Animation.WALK, Duration.seconds(.25));
        put(Animation.IDLE, Duration.seconds(.5));
    }};

    private static final int FRAME_WIDTH = 64;
    private static final int FRAME_HEIGHT = 64;

    // load an animated texture from a spritesheet
    public static AnimationChannel getAnimationChannel(final String spriteSheetName,
                                                      final Animation animation,
                                                      final Direction direction) {

        Texture spriteSheet = FXGL.getAssetLoader()
                .loadTexture(spriteSheetName);

        Rectangle2D spriteRow = new Rectangle2D(COLBEGIN.get(animation)
                , (ROWBEGIN.get(animation) + WALK_ROW_OFFSET.get(direction)) * FRAME_HEIGHT
                , FRAMES.get(animation) * FRAME_WIDTH
                , FRAME_HEIGHT);

        AnimatedTexture texture = spriteSheet
                .subTexture(spriteRow)
                .toAnimatedTexture(FRAMES.get(animation),
                        DURATION.get(animation));

        return texture.getAnimationChannel();
    }
}
