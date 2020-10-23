package com.vi.vimarvel.view.main.character;

import android.content.Context;

import com.vi.vimarvel.R;
import com.vi.vimarvel.dispatcher.ActionType;
import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.dispatcher.EventErrorType;
import com.vi.vimarvel.dispatcher.EventType;
import com.vi.vimarvel.dispatcher.IEventHandler;
import com.vi.vimarvel.store.models.MarvelCharacterInfoModel;
import com.vi.vimarvel.view.main.base.BaseViewModel;

import androidx.annotation.VisibleForTesting;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class MarvelCharacterInfoViewModel extends BaseViewModel implements IEventHandler<MarvelCharacterInfoModel> {

    private final int characterId;
    private final String noValueString;

    private MarvelCharacterInfoModel characterInfo;

    MarvelCharacterInfoViewModel(Context context, int characterId) {
        this(context, Dispatcher.getInstance(), characterId);
    }

    @VisibleForTesting
    private MarvelCharacterInfoViewModel(Context context, Dispatcher dispatcher, int characterId) {
        super(dispatcher);
        this.characterId = characterId;
        this.noValueString = context.getString(R.string.empty_value_string);
        this.characterInfo = new MarvelCharacterInfoModel();
        dispatcher.addEventListener(EventType.EVENT_TYPE_MARVEL_CHARACTER_INFO_FETCHED, this);
    }

    @Override
    protected void onResume() {
        dispatcher.dispatchAction(ActionType.ACTION_TYPE_FETCH_CHARACTER_INFO, characterId);
    }

    /*
     * Binding
     */

    @Bindable
    public String getImageUrl() {
        return characterInfo.getImageUrl();
    }

    @Bindable
    public String getCharacterName() {
        return characterInfo.getFullName().isEmpty() ? noValueString: characterInfo.getFullName();
    }

    @Bindable
    public String getCharacterAlias() {
        return characterInfo.getAlias().isEmpty() ? noValueString: characterInfo.getAlias();
    }

    @Bindable
    public String getCharacterPlaceOfBirth() {
        return characterInfo.getPlaceOfBirth().isEmpty() ? noValueString: characterInfo.getPlaceOfBirth();
    }

    /*
     * Event handling
     */

    @Override
    public void onEvent(EventType eventType, MarvelCharacterInfoModel data) {
        characterInfo = data;
        updateBinding();
    }

    @Override
    public void onEventFailure(EventType eventType, EventErrorType errorType, MarvelCharacterInfoModel data) {
        // TODO: Handle error
    }

    /*
     * Internal
     */

    private void updateBinding() {
        notifyPropertyChanged(BR.imageUrl);
        notifyPropertyChanged(BR.characterName);
        notifyPropertyChanged(BR.characterAlias);
        notifyPropertyChanged(BR.characterPlaceOfBirth);
    }
}
