package com.ericholsinger;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.ericholsinger.enums.EntityType;

/**
 * Created by eric on 5/4/18.
 */
public class TerrainFactory implements EntityFactory {
    @Spawns("terrain")
    public Entity newTerrain(SpawnData data) {
        System.out.println("newTerrain");
        return Entities.builder()
                .from(data)
                .type(EntityType.TERRAIN)
                .bbox(new HitBox(
                        BoundingShape.box(
                                (Integer)data.get("width"),
                                (Integer)data.get("height")
                        )
                ))
                .with(new CollidableComponent(true))
                .build();
    }
}
