package com.perpy.controllers.characterdetail;

import com.example.mviframework.AndroidMviViewModel;
import com.example.mviframework.BaseMviViewModel;

public class CharacterDetailViewModelImpl extends AndroidMviViewModel<State, Effect> implements CharacterDetailViewModel {
    CharacterDetailViewModelImpl(CharacterDetailViewModelDelegate characterDetailModelDelegate ) {
        super(new BaseMviViewModel(characterDetailModelDelegate));
    }
}
