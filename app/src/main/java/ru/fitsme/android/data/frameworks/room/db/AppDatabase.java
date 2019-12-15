package ru.fitsme.android.data.frameworks.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ru.fitsme.android.data.frameworks.room.RoomBrands;
import ru.fitsme.android.data.frameworks.room.RoomColors;
import ru.fitsme.android.data.frameworks.room.RoomProductName;

@Database(entities = {RoomBrands.class, RoomColors.class, RoomProductName.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "database.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(){
        if (instance == null){
            throw new RuntimeException("Database has not been created. Please call create()");
        }
        return instance;
    }

    public static void create(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
        }
    }
}
