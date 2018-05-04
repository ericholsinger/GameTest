package com.ericholsinger;

import com.almasb.fxgl.input.Input;

/**
 * Created by eric on 4/29/18.
 */
public class WalkingState extends CharacterState {
    public WalkingState(CharacterComponent characterComponent) {
        super(characterComponent);
    }

    @Override
    public CharacterState handleInput(Input input) {
        return null;
    }

    @Override
    public void onUpdate(double tpf) {

    }
}
