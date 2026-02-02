package de.franziskaneumeister.recentflics.core.database

import androidx.room.withTransaction
import javax.inject.Inject

public class TransactionHandler @Inject internal constructor(
    private val database: AppDatabase
) {
    public suspend fun performTransaction(block: suspend () -> Unit) {
        database.withTransaction(block)
    }
}