
package com.state.memo.model;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")

@Keep
@Entity(tableName = "admin")
public class Admin {

    @PrimaryKey
    private int id = 1;

    @ColumnInfo(name = "schoolName")
    private String schoolName;

    @ColumnInfo(name = "users")
    private List<String> users;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
