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
import ru.fitsme.android.data.frameworks.room.RoomColor;
import ru.fitsme.android.data.repositories.clothes.entity.RepoClotheColor;

@Dao
public abstract class ColorsDao {

    @Query("DELETE from RoomColor WHERE id NOT IN (:ids)")
    abstract void deleteNotInListColors(List<Integer> ids);

    @Query("SELECT id FROM RoomColor WHERE isChecked = 1")
    public abstract List<Integer> getCheckedColorsListIds();

    @Query("SELECT COUNT(*) FROM RoomColor WHERE isChecked = 1")
    public abstract Single<Integer> getCheckedFiltersCount();

    @Query("SELECT * FROM RoomColor")
    public abstract LiveData<List<RoomColor>> getColorsLiveData();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> insert(List<RoomColor> repoColors);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract void update(List<RoomColor> roomColors);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void update(RoomColor roomColor);

    @Query("UPDATE RoomColor SET isChecked = 0")
    public abstract void resetCheckedFilters();

    @Transaction
    public void upsert(List<RepoClotheColor> repoColorsList) {
        if (repoColorsList == null) return;

        List<RoomColor> recordsForInsert = Collections.emptyList();
        List<RoomColor> recordsForUpdate = Collections.emptyList();
        List<Integer> ids = Collections.emptyList();

        for (RepoClotheColor repoColor : repoColorsList) {
            recordsForInsert.add(new RoomColor(repoColor));
            ids.add(repoColor.getId());
        }

        // 1 - delete records which not in list from repo
        deleteNotInListColors(ids);

        // 2 - try to insert all records from repo
        List<Long> rowIds = insert(recordsForInsert);

        for (int i = 0; i < rowIds.size(); i++) {
            if (rowIds.get(i) == -1) {
                recordsForUpdate.add(new RoomColor(repoColorsList.get(i)));
            }
        }

        // 3 - update records which not inserted due to already exist
        update(recordsForUpdate);
    }
}
