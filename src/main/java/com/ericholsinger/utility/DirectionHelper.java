package com.ericholsinger.utility;

import com.ericholsinger.enums.Direction;

/**
 * Created by eric on 4/18/18.
 */
public class DirectionHelper {

    private DirectionHelper() {
        // no constructor
    }

    public static Direction opposite(Direction direction) {
        Direction opposite = Direction.SOUTH;

        switch (direction) {
            case EAST:
                opposite = Direction.WEST;
                break;
            case WEST:
                opposite =  Direction.EAST;
                break;
            case NORTH:
                opposite =  Direction.SOUTH;
                break;
            case SOUTH:
                opposite =  Direction.NORTH;
                break;
        }

        return opposite;
    }
}
