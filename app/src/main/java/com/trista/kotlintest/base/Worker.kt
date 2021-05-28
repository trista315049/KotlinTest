package com.trista.kotlintest.base

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  @author : zhouff
 *  date : 2020/10/26 14:32
 *  description :
 */
@Entity(tableName = "worker")
class Worker() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id:Long = 0

    lateinit var name: String

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        name = parcel.readString().toString()
    }
    override fun toString() = "Worker{name='$name', id=$id}"

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return  0
    }

    companion object CREATOR : Parcelable.Creator<Worker> {
        override fun createFromParcel(parcel: Parcel): Worker {
            return Worker(parcel)
        }

        override fun newArray(size: Int): Array<Worker?> {
            return arrayOfNulls(size)
        }
    }
}