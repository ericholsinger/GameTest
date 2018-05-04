package com.ericholsinger;

import com.almasb.fxgl.input.Input;

/**
 * Created by eric on 4/29/18.
 */
public abstract class CharacterState {

    private final CharacterComponent characterComponent;
    public CharacterState(CharacterComponent characterComponent) {
        this.characterComponent = characterComponent;
    }

    public abstract CharacterState handleInput(Input input);

    public abstract void onUpdate(double tpf);

    public CharacterComponent getCharacterComponent() {
        return characterComponent;
    }
}
