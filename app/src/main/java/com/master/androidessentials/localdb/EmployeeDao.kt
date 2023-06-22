package com.master.androidessentials.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Query("select * from employee")
    fun getAllEmployees():List<Employee>

    @Insert
    fun insertNewEmp(emp:Employee)

}