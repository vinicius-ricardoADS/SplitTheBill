package br.edu.ifsp.ads.splitthebill.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Member (
    val id: Int,
    var name: String,
    var items: MutableList<String>,
    var amountPaid: Double
): Parcelable