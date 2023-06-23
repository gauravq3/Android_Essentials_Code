package com.master.androidessentials.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.master.androidessentials.mvvm.models.userslist.User

@Dao
interface EmployeeDao {

    @Query("select * from user")
    fun getAllUsers():List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewEmp(user:User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(usersList: List<User>)
}