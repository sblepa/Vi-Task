package com.vi.vimarvel.view.main;

import com.vi.vimarvel.dispatcher.ActionType;
import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.dispatcher.EventErrorType;
import com.vi.vimarvel.dispatcher.EventType;
import com.vi.vimarvel.dispatcher.IEventHandler;
import com.vi.vimarvel.store.models.MarvelCharacterModel;
import com.vi.vimarvel.view.main.base.BaseViewModel;

import java.util.ArrayList;

import androidx.annotation.VisibleForTesting;

public class MarvelCharacterListViewModel extends BaseViewModel implements IEventHandler<ArrayList<MarvelCharacterModel>> {

    private ArrayList<MarvelCharacterModel> marvelCharacters;
    private IMainViewModelViewEvents viewEvents;

    MarvelCharacterListViewModel() {
        this(Dispatcher.getInstance());
    }

    @VisibleForTesting
    private MarvelCharacterListViewModel(Dispatcher dispatcher) {
        super(dispatcher);
        dispatcher.addEventListener(EventType.EVENT_TYPE_MARVEL_CHARACTERS_FETCHED, this);
    }

    @Override
    protected void onResume() {
        dispatcher.dispatchAction(ActionType.ACTION_TYPE_FETCH_MARVEL_CHARACTERS);
    }

    /*
     * Event handling
     */

    @Override
    public void onEvent(EventType eventType, ArrayList<MarvelCharacterModel> data) {
        if (eventType == EventType.EVENT_TYPE_MARVEL_CHARACTERS_FETCHED) {
            marvelCharacters = data;
            if (viewEvents != null) {
                viewEvents.onDataUpdate();
            }
        }
    }

    @Override
    public void onEventFailure(EventType eventType, EventErrorType errorType, ArrayList<MarvelCharacterModel> data) {

    }

    /*
     * View Interface
     */

    interface IMainViewModelViewEvents {
        void onDataUpdate();
        void onCharacterClicked(int characterId);
    }

    void setViewEvents(IMainViewModelViewEvents viewEvents) {
        this.viewEvents = viewEvents;
    }

    public void onCharacterClicked(int characterId) {
        if (viewEvents != null) {
            viewEvents.onCharacterClicked(characterId);
        }
    }

    /*
     * Internal
     */

    public ArrayList<MarvelCharacterModel> getMarvelCharacters() {
        return marvelCharacters;
    }
}
