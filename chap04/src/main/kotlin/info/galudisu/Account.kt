package info.galudisu

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue


class Account constructor(@JsonProperty("name") val name: String) {

  object MAPPER : ObjectMapper()

  init {
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    MAPPER.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true)
    MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL)
  }

  companion object Loader {
    fun fromJSON(jsonText: String): Account = MAPPER.readValue(jsonText)
  }
}
