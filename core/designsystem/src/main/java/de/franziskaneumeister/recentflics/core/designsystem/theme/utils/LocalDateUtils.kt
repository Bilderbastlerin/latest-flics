package de.franziskaneumeister.recentflics.core.designsystem.theme.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.intl.Locale
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
@ReadOnlyComposable
public fun LocalDate.formatDate(): String {
    return format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(
                java.util.Locale(
                    Locale.current.language,
                    Locale.current.region
                )
            )
    )
}