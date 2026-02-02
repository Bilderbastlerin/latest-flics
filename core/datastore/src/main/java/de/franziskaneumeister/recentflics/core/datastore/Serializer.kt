@file:OptIn(InternalSerializationApi::class)

package de.franziskaneumeister.recentflics.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import de.franziskaneumeister.recentflics.core.datastore.models.Settings
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

internal object SettingsSerializer : Serializer<Settings> {

    override val defaultValue: Settings = Settings(
        nextPage = 1
    )

    override suspend fun readFrom(input: InputStream): Settings =
        try {
            Json.decodeFromString<Settings>(
                input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read Settings", serialization)
        }

    override suspend fun writeTo(t: Settings, output: OutputStream) {
        output.write(
            Json.encodeToString(t)
                .encodeToByteArray()
        )
    }
}