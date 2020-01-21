package ru.fitsme.android.data.frameworks.room.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Observable;
import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.room.RoomProductName;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProductNamesDao {

    @Query("DELETE from RoomProductName WHERE isUpdated == 0")
    void clearNotUpdatedProductNames();

    @Query("SELECT * FROM roomproductname")
    Single<List<RoomProductName>> getSingleProductNamesList();

    @Query("SELECT * FROM roomproductname WHERE isChecked == 1")
    List<RoomProductName> getCheckedProductNamesList();

    @Query("SELECT * FROM roomproductname WHERE isChecked == 1")
    Single<List<RoomProductName>> getCheckedFilters();

    @Query("SELECT * FROM roomproductname")
    LiveData<List<RoomProductName>> getProductNamesLiveData();

    @Insert(onConflict = REPLACE)
    void insert(List<RoomProductName> nameList);

    @Insert(onConflict = REPLACE)
    void insert(RoomProductName roomProductName);

    @Update
    void update(RoomProductName roomProductName);
}
