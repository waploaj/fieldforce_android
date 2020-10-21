package dev.encipher.tradewave.app.utils.helpers.providers

import android.content.SearchRecentSuggestionsProvider


class SuppliersSuggestionsProvider : SearchRecentSuggestionsProvider() {
    companion object {
        const val AUTHORITY = "dev.encipher.tradewave.app.utils.helpers.providers.SuppliersSuggestionsProvider"
        const val MODE = DATABASE_MODE_QUERIES
    }

    init {
        setupSuggestions(AUTHORITY, MODE)
    }
}