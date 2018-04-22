package com.ericholsinger;

import com.almasb.fxgl.app.*;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.*;
import com.almasb.fxgl.texture.Texture;
import com.ericholsinger.enums.Direction;
import com.ericholsinger.enums.EntityType;
import com.ericholsinger.utility.DirectionHelper;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App extends GameApplication {

    private Entity player;
    private Entity npc;

    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 480;

    private final int PLAYER_CENTER = 32;

    private final String BUMPSOUND = "bfxr-hit.wav";
    //music from http://tones.wolfram.com/generate/G10BkPZMEQ97GUwl7OPdckUz5774SqvpLY7y6jfMqB
    private final String BGMUSIC = "NKM-G-25-55-2994450-0-7200-26-34-3-2840-49-0-107-122-0-0-0-0-108-913-0-0-0.mp3";

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(SCREEN_WIDTH);
        settings.setHeight(SCREEN_HEIGHT);
        settings.setTitle("Game Test App");
        settings.setVersion("0.2");
    }

    @Override
    protected void initGame() {
        super.initGame();

        CharacterComponent control = new CharacterComponent("male-character01.png");
        player = Entities.builder()
                .type(EntityType.PLAYER)
                .at(300,200)
                .with(control)
                .viewFromNodeWithBBox(control.getNodeWithBBox())
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());

        control = new CharacterComponent("male-character02.png");
        npc = Entities.builder()
                .type(EntityType.NPC)
                .at(400,200)
                .with(control)
                .viewFromNodeWithBBox(control.getNodeWithBBox())
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());
    }

    @Override
    protected void initInput() {
        super.initInput();

        Input input = getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onActionBegin() {
                player.getComponent(CharacterComponent.class).walk(Direction.EAST);
            }
            @Override
            protected void onAction() {

                if (player.getX() <= getGameScene().getWidth() - player.getWidth() / 2) {
                    getGameState().setValue("playerX", ((int) player.getX()));
                } else {
                    getAudioPlayer().playSound(BUMPSOUND);
                    player.getComponent(CharacterComponent.class).stop(Direction.EAST);
                }
            }
            @Override
            protected void onActionEnd() {
                player.getComponent(CharacterComponent.class).stop(Direction.EAST);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onActionBegin() {
                player.getComponent(CharacterComponent.class).walk(Direction.WEST);
            }
            @Override
            protected void onAction() {
                if (player.getX() + player.getWidth() / 2 >= 0) {
                    getGameState().setValue("playerX", ((int) player.getX()));
                } else {
                    getAudioPlayer().playSound(BUMPSOUND);
                    player.getComponent(CharacterComponent.class).stop(Direction.WEST);
                }
            }
            @Override
            protected void onActionEnd() {
                player.getComponent(CharacterComponent.class).stop(Direction.WEST);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onActionBegin() {
                player.getComponent(CharacterComponent.class).walk(Direction.NORTH);
            }
            @Override
            protected void onAction() {
                if (player.getY() + player.getHeight() / 2 >= 0) {
                    getGameState().setValue("playerY", ((int) player.getY()));
                } else {
                    getAudioPlayer().playSound(BUMPSOUND);
                    player.getComponent(CharacterComponent.class).stop(Direction.NORTH);
                }
            }
            @Override
            protected void onActionEnd() {
                player.getComponent(CharacterComponent.class).stop(Direction.NORTH);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onActionBegin() {
                player.getComponent(CharacterComponent.class).walk(Direction.SOUTH);
            }
            @Override
            protected void onAction() {
                if (player.getY() <= getGameScene().getHeight() - player.getHeight() / 2) {
                    getGameState().setValue("playerY", ((int) player.getY()));
                } else {
                    getAudioPlayer().playSound(BUMPSOUND);
                    player.getComponent(CharacterComponent.class).stop(Direction.SOUTH);
                }
            }
            @Override
            protected void onActionEnd() {
                player.getComponent(CharacterComponent.class).stop(Direction.SOUTH);
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

        Text textPlayerCollidedLabel = new Text();
        textPlayerCollidedLabel.setTranslateX(10);
        textPlayerCollidedLabel.setTranslateY(80);
        textPlayerCollidedLabel.textProperty().set("Collide");

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

        Text textPlayerCollided = new Text();
        textPlayerCollided.setTranslateX(60);
        textPlayerCollided.setTranslateY(80);
        textPlayerCollided.textProperty().bind(getGameState().stringProperty("collided"));

        getGameScene().addUINode(textPixelsMovedLabel);
        getGameScene().addUINode(textPlayerXLabel);
        getGameScene().addUINode(textPlayerYLabel);
        getGameScene().addUINode(textPlayerCollidedLabel);

        getGameScene().addUINode(textPixelsMoved);
        getGameScene().addUINode(textPlayerX);
        getGameScene().addUINode(textPlayerY);
        getGameScene().addUINode(textPlayerCollided);

        getAssetLoader().loadSound(BUMPSOUND);
        getAssetLoader().loadMusic(BGMUSIC);

        getAudioPlayer().setGlobalMusicVolume(.05);
        getAudioPlayer().loopBGM(BGMUSIC);


    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.NPC) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity npc) {
                getGameState().setValue("collided", "YES");
                getAudioPlayer().playSound(BUMPSOUND);

                // make npc turn toward character, using the opposite of player direction
                npc.getComponent(CharacterComponent.class).stop(
                        DirectionHelper.opposite(
                                player.getComponent(CharacterComponent.class).getDirection()
                        )
                );

                player.getComponent(CharacterComponent.class).stop(player.getComponent(CharacterComponent.class).getDirection());
            }

            @Override
            protected void onCollision(Entity player, Entity npc) {

            }

            @Override
            protected  void onCollisionEnd(Entity player, Entity npc) {
                getGameState().setValue("collided", "NO");
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
        vars.put("playerX", 0);
        vars.put("playerY", 0);
        vars.put("playerY", 0);
        vars.put("collided", "NO");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
