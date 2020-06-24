package ru.fitsme.android.data.frameworks.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.room.RoomColor;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ColorsDao {

    @Query("DELETE FROM RoomColor WHERE isUpdated == 0")
    void clearNotUpdatedColors();

    @Query("SELECT * FROM RoomColor")
    Single<List<RoomColor>> getSingleColorsList();

    @Query("SELECT * FROM RoomColor WHERE isChecked == 1")
    List<RoomColor> getCheckedColorsList();

    @Query("SELECT id FROM RoomColor WHERE isChecked == 1")
    List<Integer> getCheckedColorsListIds();

    @Query("SELECT * FROM roomcolor WHERE isChecked == 1")
    Single<List<RoomColor>> getCheckedColors();

    @Query("SELECT * FROM RoomColor")
    LiveData<List<RoomColor>> getColorsLiveData();

    @Insert(onConflict = REPLACE)
    void insert(List<RoomColor> colorList);

    @Insert(onConflict = REPLACE)
    void insert(RoomColor roomColor);

    @Update
    void update(RoomColor roomColor);
}
