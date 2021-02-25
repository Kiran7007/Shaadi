package com.peopleinteractive.shaadi.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.peopleinteractive.shaadi.R
import com.peopleinteractive.shaadi.data.db.entity.People

/**
 * Mapper provides modified data to the view.
 */
object Mapper {

    @JvmStatic
    fun getPersonName(people: People): String {
        val gender = when {
            people.gender.equals("male", true) -> {
                "M - "
            }
            people.gender.equals("female", true) -> {
                "F - "
            }
            else -> {
                ""
            }
        }
        return "${people.name.title.trim()}. ${people.name.first.trim()} ${people.name.last.trim()}\n($gender${people.dob.age})"
    }

    @JvmStatic
    fun getPersonLocation(people: People) =
        "${people.location.city}, ${people.location.country}, ${people.location.state}-${people.location.postcode}"

    @JvmStatic
    fun getUpdatedStatus(people: People): String {
        val isUpdate = people.connection.isUpdated
        if (isUpdate) {
            return if (people.connection.connectionStatus == ACCEPTED) {
                "Accepted on ${DateUtil.getStandardTime(people.connection.updatedAt)}"
            } else {
                "Declined on ${DateUtil.getStandardTime(people.connection.updatedAt)}"
            }
        }
        return ""
    }

    @JvmStatic
    @BindingAdapter("app:profileImage", "app:gender")
    fun loadImage(view: ImageView, imageUrl: String?, gender: String) {
        val drawable = when (gender) {
            "male" -> R.drawable.groom
            "female" -> R.drawable.bride
            else -> R.mipmap.ic_launcher_round
        }
        Glide.with(view.context)
            .load(imageUrl).apply(RequestOptions().placeholder(drawable).fitCenter())
            .into(view)
    }
}