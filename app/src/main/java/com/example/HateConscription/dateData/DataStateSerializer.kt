package com.example.HateConscription.dateData

import androidx.datastore.core.Serializer
import com.example.HateConscription.calendar.DateNDaysDataState
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object DataStateSerializer: Serializer<DateNDaysDataState> {
    override val defaultValue: DateNDaysDataState
        get() = DateNDaysDataState()

    override suspend fun readFrom(input: InputStream): DateNDaysDataState {
        return try {
            Json.decodeFromString(
                deserializer = DateNDaysDataState.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: DateNDaysDataState, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = DateNDaysDataState.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}