package ru.fitsme.android.data.frameworks.room.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Observable;
import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.room.RoomColor;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ColorsDao {

    @Query("DELETE FROM RoomColor WHERE isUpdated == 0")
    void clearNotUpdatedColors();

    @Query("SELECT * FROM RoomColor")
    Single<List<RoomColor>> getColorsList();

    @Query("SELECT * FROM RoomColor")
    LiveData<List<RoomColor>> getColorsLiveData();

    @Insert(onConflict = REPLACE)
    void insert(List<RoomColor> colorList);

    @Insert(onConflict = REPLACE)
    void insert(RoomColor roomColor);

    @Update
    void update(RoomColor roomColor);

    @Query("SELECT * FROM roomcolor WHERE isChecked == 1")
    Single<List<RoomColor>> getCheckedColors();
}
