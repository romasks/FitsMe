package ru.fitsme.android.data.frameworks.room.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Observable;
import ru.fitsme.android.data.frameworks.room.RoomColors;
import ru.fitsme.android.data.frameworks.room.RoomProductName;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ColorsDao {

    @Query("DELETE FROM roomcolors")
    void clearColors();

    @Query("SELECT * FROM roomcolors")
    Observable<List<RoomColors>> getColors();

    @Insert(onConflict = REPLACE)
    void insert(List<RoomColors> colorList);
}
