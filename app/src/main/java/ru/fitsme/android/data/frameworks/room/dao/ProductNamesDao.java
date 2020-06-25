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
import ru.fitsme.android.data.frameworks.room.RoomProductName;
import ru.fitsme.android.data.repositories.clothes.entity.RepoClotheProductName;

@Dao
public abstract class ProductNamesDao {

    @Query("DELETE from RoomProductName WHERE id NOT IN (:ids)")
    abstract void deleteNotInListProductNames(List<Integer> ids);

    @Query("SELECT id FROM RoomProductName WHERE isChecked == 1")
    public abstract List<Integer> getCheckedProductNamesListIds();

    @Query("SELECT * FROM RoomProductName WHERE isChecked == 1")
    public abstract Single<List<RoomProductName>> getCheckedFilters();

    @Query("SELECT * FROM RoomProductName")
    public abstract LiveData<List<RoomProductName>> getProductNamesLiveData();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> insert(List<RoomProductName> roomProductNames);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract void update(List<RoomProductName> roomProductNames);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void update(RoomProductName roomProductName);

    @Query("UPDATE RoomProductName SET isChecked = 0")
    public abstract void resetProductNameFilters();

    @Transaction
    public void upsert(List<RepoClotheProductName> repoProductNamesList) {
        if (repoProductNamesList == null) return;

        List<RoomProductName> recordsForInsert = Collections.emptyList();
        List<RoomProductName> recordsForUpdate = Collections.emptyList();
        List<Integer> ids = Collections.emptyList();

        for (RepoClotheProductName repoProductName : repoProductNamesList) {
            recordsForInsert.add(new RoomProductName(repoProductName));
            ids.add(repoProductName.getId());
        }

        // 1 - delete records which not in list from repo
        deleteNotInListProductNames(ids);

        // 2 - try to insert all records from repo
        List<Long> rowIds = insert(recordsForInsert);

        for (int i = 0; i < rowIds.size(); i++) {
            if (rowIds.get(i) == -1) {
                recordsForUpdate.add(new RoomProductName(repoProductNamesList.get(i)));
            }
        }

        // 3 - update records which not inserted due to already exist
        update(recordsForUpdate);
    }
}
