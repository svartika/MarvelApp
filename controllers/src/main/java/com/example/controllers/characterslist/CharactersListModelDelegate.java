package com.example.controllers.characterslist;

import android.view.View;

import androidx.paging.PagedList;

import com.example.controllers.characterslist.inmemoryrepository.CharactersListRepository;
import com.example.controllers.characterslist.inmemoryrepository.PagedResponse;
import com.example.controllers.commons.CardClickListener;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.mviframework.BaseMviDelegate;
import com.example.mviframework.Change;
import com.example.mviframework.Reducer;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CharactersListModelDelegate extends BaseMviDelegate<State, CharactersListModelDelegate.InnerState, Effect> {

    CharactersListNetworkInterface charactersListNetworkInterface;
    CharactersListRepository repository;
    PublishSubject<String> searchKeywords = PublishSubject.create();

    CardClickListener clickListener = new CardClickListener<ProcessedMarvelCharacter>() {

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

    SearchTextChangedCallbackListener searchCallback = new SearchTextChangedCallbackListener() {
        @Override
        public void invoke(String searchKeyword) {
            searchKeywords.onNext(searchKeyword);
            enqueue(new Reducer<InnerState, Effect>() {
                @Override
                public Change<InnerState, Effect> reduce(InnerState innerState) {
                    InnerState newInnerState = innerState.copy();
                    newInnerState.searchStr = searchKeyword;
                    return asChange(newInnerState);
                }
            });
        }
    };

    public CharactersListModelDelegate(CharactersListNetworkInterface charactersListNetworkInterface, CharactersListRepository repository) {
        this.charactersListNetworkInterface = charactersListNetworkInterface;
        this.repository = repository;
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
        PagedList<ProcessedMarvelCharacter> charactersList;
        String searchStr;

        InnerState(PagedList<ProcessedMarvelCharacter> charactersList, String searchStr) {
            this.charactersList = charactersList;
            this.searchStr = searchStr;
        }

        InnerState copy() {
            return new InnerState(charactersList, searchStr);
        }
    }

    static InnerState clear = new InnerState(null, "");
    Observable<PagedResponse<ProcessedMarvelCharacter>> observable = searchKeywords
            .startWith("")
            .distinctUntilChanged()
            .map(keyword -> {
                return repository.searchCharacter(keyword);

            }).onErrorReturn(throwable -> {
                return null;
            });
    Observable<PagedList<ProcessedMarvelCharacter>> characters = observable.switchMap(result -> result.getList());

    private void/*Observable<List<ProcessedMarvelCharacter>>*/ loadCharacters() {
        if (charactersListNetworkInterface == null) {
            return;// Observable.just(new ArrayList<>());
        }


        Observable<Reducer<InnerState, Effect>> reducerObservable = characters
                .map(list -> {
                    return new Reducer<InnerState, Effect>() {

                        @Override
                        public Change<InnerState, Effect> reduce(InnerState innerState) {
                            InnerState newInnerState = innerState.copy();
                            newInnerState.charactersList = list;
                            return asChange(newInnerState);
                        }
                    };
                });
        enqueue(reducerObservable);


    }
}
