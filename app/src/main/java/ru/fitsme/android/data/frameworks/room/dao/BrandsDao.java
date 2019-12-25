package ru.fitsme.android.data.frameworks.room.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Observable;
import ru.fitsme.android.data.frameworks.room.RoomBrand;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BrandsDao {

    @Query("DELETE FROM RoomBrand WHERE isUpdated == 0")
    void clearNotUpdatedBrands();

    @Delete
    void delete(RoomBrand roomBrand);

    @Query("SELECT * FROM RoomBrand")
    Observable<List<RoomBrand>> getBrands();

    @Insert(onConflict = REPLACE)
    void insert(List<RoomBrand> brandList);

    @Insert(onConflict = REPLACE)
    void insert(RoomBrand roomBrand);

    @Update
    void update(RoomBrand brand);
}
