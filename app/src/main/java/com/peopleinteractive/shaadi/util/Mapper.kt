package com.peopleinteractive.shaadi.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.peopleinteractive.shaadi.data.db.entity.People


object Mapper {

    @JvmStatic
    fun getPersonName(people: People) =
        "${people.name.title}. ${people.name.first} ${people.name.last}"

    @JvmStatic
    fun getPersonDetails(people: People) =
        "${people.dob.age}, ${people.phone}, ${people.gender}"

    @JvmStatic
    fun getPersonLocation(people: People) =
        "${people.location.city}, ${people.location.country}, ${people.location.state}-${people.location.postcode}"

    @JvmStatic
    fun getUpdatedStatus(people: People): String {
        val isUpdate = people.connection.isUpdated
        if (isUpdate) {
            return if (people.connection.connectionStatus == ACCEPTED) {
                "Accepted by you on ${DateUtil.getStandardTime(people.connection.updatedAt)}"
            } else {
                "Declined by you on ${DateUtil.getStandardTime(people.connection.updatedAt)}"
            }
        }
        return ""
    }

    @JvmStatic
    @BindingAdapter("app:profileImage")
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(imageUrl).apply(RequestOptions().fitCenter())
            .into(view)
    }
}