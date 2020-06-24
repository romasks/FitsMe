package ru.fitsme.android.data.frameworks.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.fitsme.android.data.frameworks.room.RoomBrand;
import ru.fitsme.android.data.frameworks.room.RoomColor;
import ru.fitsme.android.data.frameworks.room.RoomProductName;
import ru.fitsme.android.data.frameworks.room.dao.BrandsDao;
import ru.fitsme.android.data.frameworks.room.dao.ColorsDao;
import ru.fitsme.android.data.frameworks.room.dao.ProductNamesDao;

@Database(entities = {RoomBrand.class, RoomColor.class, RoomProductName.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "database.db";

    public static AppDatabase instance;

    public static void create(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
        }
    }

    public abstract BrandsDao getBrandsDao();

    public abstract ColorsDao getColorsDao();

    public abstract ProductNamesDao getProductNamesDao();
}
