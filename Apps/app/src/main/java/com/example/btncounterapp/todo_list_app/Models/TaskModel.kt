package com.example.btncounterapp.todo_list_app.Models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "task_table",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = TodoModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("todo_id"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data  class TaskModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int=0,
    @ColumnInfo(name = "task_name") var taskName: String?,
    @ColumnInfo(name = "todo_id", index = true) var todoId: Int?,
    @ColumnInfo(name = "is_done") var isDone: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(taskName)
        parcel.writeValue(todoId)
        parcel.writeByte(if (isDone) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskModel> {
        override fun createFromParcel(parcel: Parcel): TaskModel {
            return TaskModel(parcel)
        }

        override fun newArray(size: Int): Array<TaskModel?> {
            return arrayOfNulls(size)
        }
    }


}