package com.varu.offlineappchallenge.data.models

data class Person(
    var gender: String? = null,
    var name: Name? = null,
    var location: Location? = null,
    var email: String? = null,
    var dob: DateDetail? = null,
    var phone: String? = null,
    var id: IDDetail? = null,
    var picture: PictureDetail? = null,
    @Transient var matchAction: String? = null
)

data class Name(
    var title: String? = null,
    var first: String? = null,
    var last: String? = null,
)

data class Location(
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var postcode: String? = null,
)

data class DateDetail(
    var date: String? = null,
    var age: Double? = null,
)

data class IDDetail(
    var name: String? = null,
    var value: String? = null,
)

data class PictureDetail(
    var large: String? = null,
    var medium: String? = null,
    var thumbnail: String? = null,
)
