package ru.fitsme.android.data.frameworks.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.room.RoomBrand;
import ru.fitsme.android.data.repositories.clothes.entity.RepoClotheBrand;

@Dao
public abstract class BrandsDao {

    @Query("DELETE from RoomBrand WHERE id NOT IN (:ids)")
    abstract void deleteNotInListBrands(List<Integer> ids);

    @Query("SELECT id FROM RoomBrand WHERE isChecked = 1")
    public abstract List<Integer> getCheckedBrandsListIds();

    @Query("SELECT COUNT(*) FROM RoomBrand WHERE isChecked = 1")
    public abstract Single<Integer> getCheckedFiltersCount();

    @Query("SELECT * FROM RoomBrand")
    public abstract LiveData<List<RoomBrand>> getBrandsLiveData();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> insert(List<RoomBrand> repoBrands);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract void update(List<RoomBrand> roomBrands);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void update(RoomBrand roomBrand);

    @Query("UPDATE RoomBrand SET isChecked = 0")
    public abstract void resetCheckedFilters();

    @Transaction
    public void upsert(List<RepoClotheBrand> repoBrandsList) {
        if (repoBrandsList == null) return;

        List<RoomBrand> recordsForInsert = Collections.emptyList();
        List<RoomBrand> recordsForUpdate = Collections.emptyList();
        List<Integer> ids = Collections.emptyList();

        for (RepoClotheBrand repoBrand : repoBrandsList) {
            recordsForInsert.add(new RoomBrand(repoBrand));
            ids.add(repoBrand.getId());
        }

        // 1 - delete records which not in list from repo
        deleteNotInListBrands(ids);

        // 2 - try to insert all records from repo
        List<Long> rowIds = insert(recordsForInsert);

        for (int i = 0; i < rowIds.size(); i++) {
            if (rowIds.get(i) == -1) {
                recordsForUpdate.add(new RoomBrand(repoBrandsList.get(i)));
            }
        }

        // 3 - update records which not inserted due to already exist
        update(recordsForUpdate);
    }
}
