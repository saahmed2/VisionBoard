package com.planning.visionboard.model;

import android.app.Application;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.planning.visionboard.data.ApplicationDatabase;

public class CatalogViewModel extends AndroidViewModel {
    private LiveData<List<Board>> boards;


    public CatalogViewModel(@NonNull Application application) {
        super(application);
        ApplicationDatabase database =
                ApplicationDatabase.getInstance(this.getApplication());
        boards = database.boardDao().loadAllBoards();
    }
    public LiveData<List<Board>> getBoards() {return boards;}

}
