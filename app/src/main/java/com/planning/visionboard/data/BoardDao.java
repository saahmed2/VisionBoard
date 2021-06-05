package com.planning.visionboard.data;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.planning.visionboard.model.Board;

@Dao
public interface BoardDao {

    @Insert
    long insert(Board board);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateBoard(Board board);

    @Query("DELETE FROM board_tbl WHERE id = :id")
    int deleteBoard(int id);

    @Query("SELECT * FROM board_tbl")
    LiveData<List<Board>> loadAllBoards();

    @Query("SELECT * FROM board_tbl WHERE id = :id ")
    LiveData<Board> loadBoardById(int id);

    @Query("DELETE FROM board_tbl")
     void deleteAllBoards();


}
