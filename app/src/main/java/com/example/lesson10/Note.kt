package com.example.lesson10

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator
import java.util.*

/**
 * homework com.example.notes
 *
 * @author Amina
 * 06.06.2021
 */
data class Note constructor(var name: String?, var description: String?,var date: Date) : Parcelable {

    var id: String? = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        Date(parcel.readLong())
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(description)
        dest.writeLong(date.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}