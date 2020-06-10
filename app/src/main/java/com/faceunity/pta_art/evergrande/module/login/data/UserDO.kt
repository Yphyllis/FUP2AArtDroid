package com.faceunity.pta_art.evergrande.module.login.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class UserDO(val userId: String, val token: String): Parcelable, Serializable