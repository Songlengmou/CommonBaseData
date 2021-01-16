package com.anningtex.commonbasedata.test.six.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @Author Song
 */

@Database(entities = {StudentBean.class}, version = 1, exportSchema = false)
public abstract class SixDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "zl_lm_db";

    private static SixDataBase databaseInstance;

    public static synchronized SixDataBase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room
                    .databaseBuilder(context.getApplicationContext(), SixDataBase.class, DATABASE_NAME)
                    //不保留原有数据,直接清空
//                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
//                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return databaseInstance;
    }

    public abstract StudentDao studentDao();

    /**
     * 升级数据库
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE student "
                    + " ADD COLUMN olid INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE student "
                    + " ADD COLUMN orderNo TEXT");
        }
    };
}
