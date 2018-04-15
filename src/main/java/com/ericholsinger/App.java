package com.ericholsinger;

import com.almasb.fxgl.app.*;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.*;
import com.almasb.fxgl.texture.Texture;
import com.ericholsinger.enums.Direction;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App extends GameApplication {

    private Entity player;
    private final int MOVE_DISTANCE = 5;
    private final int PLAYER_CENTER = 32;

    private final String BUMPSOUND = "bfxr-hit.wav";
    //music from http://tones.wolfram.com/generate/G10BkPZMEQ97GUwl7OPdckUz5774SqvpLY7y6jfMqB
    private final String BGMUSIC = "NKM-G-25-55-2994450-0-7200-26-34-3-2840-49-0-107-122-0-0-0-0-108-913-0-0-0.mp3";

    private Texture spriteSheet01;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Game Test App");
        settings.setVersion("0.1");
    }

    @Override
    protected void initGame() {
        super.initGame();

        player = Entities.builder()
                .at(300,300)
                .with(new CharacterControl("male-character01.png"))
//                .viewFromNode(new Rectangle(PLAYER_CENTER, PLAYER_CENTER, Color.BLUE))
                .buildAndAttach(getGameWorld());
    }

    @Override
    protected void initInput() {
        super.initInput();

        Input input = getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onActionBegin() {
                player.getControl(CharacterControl.class).walk(Direction.EAST);
            }
            @Override
            protected void onAction() {
                if (player.getRightX() + PLAYER_CENTER + MOVE_DISTANCE <= getGameScene().getWidth()) {
                    player.translateX(MOVE_DISTANCE); // move right 5 pixels
                    getGameState().increment("pixelsMoved", MOVE_DISTANCE);
                    getGameState().setValue("playerX", ((int) player.getX()));
                } else {
                    getAudioPlayer().playSound(BUMPSOUND);
                }
            }
            @Override
            protected void onActionEnd() {

                player.getControl(CharacterControl.class).idle();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onActionBegin() {
                player.getControl(CharacterControl.class).walk(Direction.WEST);
            }
            @Override
            protected void onAction() {
                if (player.getX() + PLAYER_CENTER - MOVE_DISTANCE >= 0) {
                    player.translateX(-MOVE_DISTANCE); // move left 5 pixels
                    getGameState().increment("pixelsMoved", MOVE_DISTANCE);
                    getGameState().setValue("playerX", ((int) player.getX()));
                } else {
                    getAudioPlayer().playSound(BUMPSOUND);
                }
            }
            @Override
            protected void onActionEnd() {
                player.getControl(CharacterControl.class).idle();
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onActionBegin() {
                player.getControl(CharacterControl.class).walk(Direction.NORTH);
            }
            @Override
            protected void onAction() {
                if (player.getY() + PLAYER_CENTER - MOVE_DISTANCE >= 0) {
                    player.translateY(-MOVE_DISTANCE); // move up 5 pixels
                    getGameState().increment("pixelsMoved", MOVE_DISTANCE);
                    getGameState().setValue("playerY", ((int) player.getY()));
                } else {
                    getAudioPlayer().playSound(BUMPSOUND);
                }
            }
            @Override
            protected void onActionEnd() {
                player.getControl(CharacterControl.class).idle();
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onActionBegin() {
                player.getControl(CharacterControl.class).walk(Direction.SOUTH);
            }
            @Override
            protected void onAction() {
                if (player.getY() + PLAYER_CENTER + MOVE_DISTANCE <= getGameScene().getHeight()) {
                    player.translateY(MOVE_DISTANCE); // move down 5 pixels
                    getGameState().increment("pixelsMoved", MOVE_DISTANCE);
                    getGameState().setValue("playerY", ((int) player.getY()));
                } else {
                    getAudioPlayer().playSound(BUMPSOUND);
                }
            }
            @Override
            protected void onActionEnd() {
                player.getControl(CharacterControl.class).idle();
            }
        }, KeyCode.S);

    }

    @Override
    protected void initUI() {
        super.initUI();

        Text textPixelsMovedLabel = new Text();
        textPixelsMovedLabel.setTranslateX(10);
        textPixelsMovedLabel.setTranslateY(20);
        textPixelsMovedLabel.textProperty().set("Moved");

        Text textPlayerXLabel = new Text();
        textPlayerXLabel.setTranslateX(10);
        textPlayerXLabel.setTranslateY(40);
        textPlayerXLabel.textProperty().set("X pos");

        Text textPlayerYLabel = new Text();
        textPlayerYLabel.setTranslateX(10);
        textPlayerYLabel.setTranslateY(60);
        textPlayerYLabel.textProperty().set("Y pos");

        Text textPixelsMoved = new Text();
        textPixelsMoved.setTranslateX(60);
        textPixelsMoved.setTranslateY(20);
        textPixelsMoved.textProperty().bind(getGameState().intProperty("pixelsMoved").asString());

        Text textPlayerX = new Text();
        textPlayerX.setTranslateX(60);
        textPlayerX.setTranslateY(40);
        textPlayerX.textProperty().bind(getGameState().intProperty("playerX").asString());

        Text textPlayerY = new Text();
        textPlayerY.setTranslateX(60);
        textPlayerY.setTranslateY(60);
        textPlayerY.textProperty().bind(getGameState().intProperty("playerY").asString());

        getGameScene().addUINode(textPixelsMovedLabel);
        getGameScene().addUINode(textPlayerXLabel);
        getGameScene().addUINode(textPlayerYLabel);

        getGameScene().addUINode(textPixelsMoved);
        getGameScene().addUINode(textPlayerX);
        getGameScene().addUINode(textPlayerY);

        getAssetLoader().loadSound(BUMPSOUND);
        getAssetLoader().loadMusic(BGMUSIC);

        getAudioPlayer().setGlobalMusicVolume(.05);
        getAudioPlayer().loopBGM(BGMUSIC);


    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
        vars.put("playerX", 0);
        vars.put("playerY", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
