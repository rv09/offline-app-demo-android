package com.varu.offlineappchallenge.data.local.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.varu.offlineappchallenge.data.models.*

@Entity(tableName = "persons")
data class PersonTable(
    @PrimaryKey var idName: String,
    var idValue: String?,
    var nameTitle: String?,
    var firstName: String?,
    var lastName: String?,
    var gender: String?,
    var city: String?,
    var state: String?,
    var country: String?,
    var email: String?,
    var dobDate: String?,
    var dobAge: Double?,
    var phone: String?,
    var largePicture: String?,
    var thumbnailPicture: String?,
    var matchAction: String?,
) {
    constructor(person: Person): this(
        idName = person.id?.name ?: "missing-id",
        idValue = person.id?.value,
        nameTitle = person.name?.title,
        firstName = person.name?.first,
        lastName = person.name?.last,
        gender = person.gender,
        city = person.location?.city,
        state = person.location?.state,
        country = person.location?.country,
        email = person.email,
        dobDate = person.dob?.date,
        dobAge = person.dob?.age,
        phone = person.phone,
        largePicture = person.picture?.large,
        thumbnailPicture = person.picture?.thumbnail,
        matchAction = person.matchAction
    )
}

fun PersonTable.toPerson(): Person {
    return Person(
        gender = gender,
        name = Name(
            title = nameTitle,
            first = firstName,
            last = lastName,
        ),
        location = Location(
            city = city,
            state = state,
            country = country,
        ),
        email = email,
        dob = DateDetail(
            date = dobDate,
            age = dobAge,
        ),
        phone = phone,
        id = IDDetail(
            name = idName,
            value = idValue,
        ),
        picture = PictureDetail(
            large = largePicture,
            medium = largePicture,
            thumbnail = thumbnailPicture,
        ),
        matchAction = matchAction,
    )
}