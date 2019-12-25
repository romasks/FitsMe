package ru.fitsme.android.data.frameworks.room.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Observable;
import ru.fitsme.android.data.frameworks.room.RoomColor;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ColorsDao {

    @Query("DELETE FROM RoomColor WHERE isUpdated == 0")
    void clearNotUpdatedColors();

    @Query("SELECT * FROM RoomColor")
    Observable<List<RoomColor>> getColors();

    @Insert(onConflict = REPLACE)
    void insert(List<RoomColor> colorList);

    @Insert(onConflict = REPLACE)
    void insert(RoomColor roomColor);

    @Update
    void update(RoomColor roomColor);
}
