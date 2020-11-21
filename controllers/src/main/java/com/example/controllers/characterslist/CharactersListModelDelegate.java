package com.example.controllers.characterslist;

import android.view.View;

import com.example.controllers.commons.CardClickListener;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.mviframework.BaseMviDelegate;
import com.example.mviframework.Change;
import com.example.mviframework.Reducer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class CharactersListModelDelegate extends BaseMviDelegate<State, CharactersListModelDelegate.InnerState, Effect> {

    CharactersListNetworkInterface charactersListNetworkInterface;
    PublishSubject<String> searchKeywords = PublishSubject.create();

    CardClickListener clickListener = new CardClickListener<ProcessedMarvelCharacter>() {

        @Override
        public void invoke(View view, ProcessedMarvelCharacter character) {
            enqueue(new Reducer<InnerState, Effect>() {
                @Override
                public Change<InnerState, Effect> reduce(InnerState innerState) {
                    return withEffects(innerState, new Effect.ClickCharacterEffect<ProcessedMarvelCharacter>(view,character));
                }
            });
        }
    };

    SearchTextChangedCallbackListener searchCallback = new SearchTextChangedCallbackListener() {
        @Override
        public void invoke(String searchKeyword) {
            searchKeywords.onNext(searchKeyword);
            enqueue(new Reducer<InnerState, Effect>() {
                @Override
                public Change<InnerState, Effect> reduce(InnerState innerState) {
                    InnerState newInnerState = innerState.copy();
                    newInnerState.searchStr = searchKeyword;
                    return  asChange(newInnerState);
                }
            });
        }
    };

    public CharactersListModelDelegate(CharactersListNetworkInterface charactersListNetworkInterface) {
        this.charactersListNetworkInterface = charactersListNetworkInterface;
        loadCharacters();
    }

    @Override
    public Change<InnerState, Effect> getInitialChange() {
        return asChange(clear);
    }

    @Override
    public State mapState(InnerState innerState) {
        return new State(innerState.charactersList, innerState.searchStr, clickListener, searchCallback);
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

    private void/*Observable<List<ProcessedMarvelCharacter>>*/ loadCharacters() {
        if (charactersListNetworkInterface == null) {
            return;// Observable.just(new ArrayList<>());
        }
        Observable<List<ProcessedMarvelCharacter>> observable = searchKeywords
                .startWith("")
                .distinctUntilChanged()
                .switchMap(keyword -> {
                    return charactersListNetworkInterface
                            .searchCharacter(keyword)
                            .onErrorReturn(throwable -> {
                                return new ArrayList<>();
                            });
                });

        Observable<Reducer<InnerState, Effect>> reducerObservable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(processedMarvelCharacters -> {
                    return new Reducer<InnerState, Effect>() {

                        @Override
                        public Change<InnerState, Effect> reduce(InnerState innerState) {
                            InnerState newInnerState = innerState.copy();
                            newInnerState.charactersList = processedMarvelCharacters;
                            return asChange(newInnerState);
                        }
                    };
                });
        enqueue(reducerObservable);


    }
}
