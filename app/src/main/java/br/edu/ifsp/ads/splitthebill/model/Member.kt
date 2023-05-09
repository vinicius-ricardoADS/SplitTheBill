package br.edu.ifsp.ads.splitthebill.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Member(
    @PrimaryKey (autoGenerate = true)
    val id: Int?,
    @NonNull
    var name: String,
    @NonNull
    var items: String,
    @NonNull
    var amountPaid: Double
): Parcelable