package com.example.controllers.characterdetail;

import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.mviframework.BaseMviDelegate;
import com.example.mviframework.Change;
import com.example.mviframework.Reducer;
import com.example.mviframework.Runner;

public class CharacterDetailViewModelDelegate extends BaseMviDelegate<State, CharacterDetailViewModelDelegate.InnerState, Effect> {
    CharacterDetailNetworkInterface networkInterface;
    ProcessedMarvelCharacter character;
    public CharacterDetailViewModelDelegate(CharacterDetailNetworkInterface networkInterface, ProcessedMarvelCharacter character) {
        this.networkInterface = networkInterface;
        this.character = character;
        /*enqueue(new Reducer<InnerState, Effect>() {
            @Override
            public Change<InnerState, Effect> reduce(InnerState innerState) {
                InnerState newInnerState = innerState.copy();
                newInnerState.character = character;
                newInnerState.error = false;
                newInnerState.loading = false;
                newInnerState.isImageLoaded = false;

                return asChange(newInnerState);
            }
        });*/
    }



    Runner callbackRunner = dispatch(new Reducer<InnerState, Effect>() {
        @Override
        public Change<InnerState, Effect> reduce(InnerState innerState) {
            InnerState newInnerState = innerState.copy();
            newInnerState.loading = false;
            newInnerState.error = false;
            return withEffects(newInnerState, new Effect.ImageLoaded());
            //return asChange(newInnerState);
        }
    });

   /* ImageLoadedCallbackListener callbackListener = new ImageLoadedCallbackListener() {
        @Override
        public void callback() {
            enqueue(new Reducer<InnerState, Effect>() {
                @Override
                public Change<InnerState, Effect> reduce(InnerState innerState) {
                    InnerState newInnerState = innerState.copy();
                    newInnerState.isImageLoaded = true;
                    return asChange(newInnerState);
                }
            });
        }
    };*/

    @Override
    public Change<InnerState, Effect> getInitialChange() {
        return asChange(new InnerState(character, true, false));
    }

    @Override
    public State mapState(InnerState innerState) {
        return new State(innerState.character, innerState.loading, innerState.error, callbackRunner);
    }

    static class InnerState {
        boolean loading;
        boolean error;
        ProcessedMarvelCharacter character;
        InnerState(ProcessedMarvelCharacter character, boolean loading, boolean error) {
            this.character = character;
            this.loading = loading;
            this.error = error;
        }
        InnerState copy() {
            return new InnerState(character, loading, error);
        }
    }


}
