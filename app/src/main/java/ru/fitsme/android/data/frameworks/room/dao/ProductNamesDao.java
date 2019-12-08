package ru.fitsme.android.data.frameworks.room.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Observable;
import ru.fitsme.android.data.frameworks.room.RoomProductName;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProductNamesDao {

    @Query("DELETE FROM roomproductname")
    void clearProductNames();

    @Query("SELECT * FROM roomproductname")
    Observable<List<RoomProductName>> getProductNames();

    @Insert(onConflict = REPLACE)
    void insert(List<RoomProductName> nameList);
}
