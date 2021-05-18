package com.example.room_bai02;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item ")
    List<Item> getAll();

    @Query("SELECT name FROM item ")
    List<String> getAllName();

    @Insert
    void insert(Item item);

    @Delete
    void delete(Item item);
}
