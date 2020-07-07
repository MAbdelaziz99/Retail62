package com.example.retail62.room;

import android.content.Context;

import androidx.room.Room;

public class RoomFactory {

    private static Database database;

    public static Database getRoomObject(Context context) {

        if (database == null) {

            database = Room.databaseBuilder(context, Database.class, "database")
                    .build();
        }

        return database;
    }
}