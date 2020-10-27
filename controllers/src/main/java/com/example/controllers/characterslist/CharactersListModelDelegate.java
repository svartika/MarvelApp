package com.example.controllers.characterslist;

import android.view.View;

import com.example.controllers.retrofit.ProcessedMarvelCharacter;
import com.example.mviframework.BaseMviDelegate;
import com.example.mviframework.Change;
import com.example.mviframework.Reducer;

import java.util.ArrayList;
import java.util.List;

public class CharactersListModelDelegate extends BaseMviDelegate<State, CharactersListModelDelegate.InnerState, Effect> {

    MarvelCharacterClickListener clickListener = new MarvelCharacterClickListener<ProcessedMarvelCharacter>() {

        @Override
        public void invoke(View view, ProcessedMarvelCharacter character) {
            enqueue(new Reducer<InnerState, Effect>() {
                @Override
                public Change<InnerState, Effect> reduce(InnerState innerState) {
                    return withEffects(innerState, new Effect.ClickCharacterEffect<ProcessedMarvelCharacter>(view, character));
                }
            });
        }
    };

    CharactersListModelDelegate() {

    }

    @Override
    public Change<InnerState, Effect> getInitialChange() {
        return asChange(clear);
    }

    @Override
    public State mapState(InnerState innerState) {
        return new State(innerState.charactersList, innerState.searchStr, clickListener);
    }

    static class InnerState {
        List<ProcessedMarvelCharacter> charactersList;
        String searchStr;

        InnerState(List<ProcessedMarvelCharacter> charactersList, String searchStr) {
            this.charactersList = charactersList;
            this.searchStr = searchStr;
        }

        InnerState copy() {
            return new InnerState(charactersList, searchStr);
        }
    }

    static InnerState clear = new InnerState(new ArrayList<>(), "");

}
