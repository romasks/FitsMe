package ru.fitsme.android.data.frameworks.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.room.RoomBrand;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BrandsDao {

    @Query("DELETE FROM RoomBrand WHERE isUpdated == 0")
    void clearNotUpdatedBrands();

    @Delete
    void delete(RoomBrand roomBrand);

    @Query("SELECT * FROM RoomBrand")
    Single<List<RoomBrand>> getSingleBrandsList();

    @Query("SELECT * FROM RoomBrand WHERE isChecked == 1")
    List<RoomBrand> getCheckedBrandsList();

    @Query("SELECT id FROM RoomBrand WHERE isChecked == 1")
    List<Integer> getCheckedBrandsListIds();

    @Query("SELECT * FROM RoomBrand WHERE isChecked == 1")
    Single<List<RoomBrand>> getCheckedFilters();

    @Query("SELECT * FROM RoomBrand")
    LiveData<List<RoomBrand>> getBrandsLiveData();

    @Insert(onConflict = REPLACE)
    void insert(List<RoomBrand> brandList);

    @Insert(onConflict = REPLACE)
    void insert(RoomBrand roomBrand);

    @Update
    void update(RoomBrand brand);
}
