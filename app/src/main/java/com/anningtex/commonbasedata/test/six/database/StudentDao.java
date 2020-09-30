package com.anningtex.commonbasedata.test.six.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @Author Song
 */
@Dao
public interface StudentDao {
    @Insert
    void insertStudent(StudentBean studentBean);

    @Delete
    void deleteStudent(StudentBean studentBean);

    @Update
    void updateStudent(StudentBean studentBean);

    @Query("SELECT * FROM student")
    List<StudentBean> getStudentList();

    @Query("SELECT * FROM student WHERE id = :id")
    StudentBean getStudentById(int id);
}
