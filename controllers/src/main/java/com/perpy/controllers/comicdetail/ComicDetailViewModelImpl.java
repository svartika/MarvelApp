package com.perpy.controllers.comicdetail;

import com.example.mviframework.AndroidMviViewModel;
import com.example.mviframework.BaseMviViewModel;

public class ComicDetailViewModelImpl extends AndroidMviViewModel<State, Effect> implements ComicDetailViewModel {
    ComicDetailViewModelImpl(ComicDetailViewModelDelegate comicDetailViewModelDelegate) {
        super (new BaseMviViewModel(comicDetailViewModelDelegate));
    }
}
