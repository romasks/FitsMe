package ru.fitsme.android.data.frameworks.room.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Observable;
import ru.fitsme.android.data.frameworks.room.RoomBrands;
import ru.fitsme.android.data.frameworks.room.RoomProductName;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BrandsDao {

    @Query("DELETE FROM roombrands")
    void clearBrands();

    @Query("SELECT * FROM roombrands")
    Observable<List<RoomBrands>> getBrands();

    @Insert(onConflict = REPLACE)
    void insert(List<RoomBrands> brandList);
}
