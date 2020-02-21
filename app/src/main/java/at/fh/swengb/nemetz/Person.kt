package at.fh.swengb.nemetz

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Person(val name: String, val profileImagePath: String) {

}