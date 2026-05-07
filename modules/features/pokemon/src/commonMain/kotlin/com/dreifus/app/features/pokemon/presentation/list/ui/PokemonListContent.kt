package com.dreifus.app.features.pokemon.presentation.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dreifus.app.data.pokemon.PokemonListItem
import com.dreifus.app.features.pokemon.presentation.list.PokemonListState
import com.dreifus.arch.lce.LceState
import com.dreifus.arch.lce.isRefreshing
import com.dreifus.template.uikit.button.AppButton
import com.dreifus.template.uikit.icon.Search24
import com.dreifus.template.uikit.style.AppIcons
import com.dreifus.template.uikit.style.AppTheme
import com.dreifus.template.uikit.textField.AppTextField
import kmptemplateapp.modules.features.pokemon.generated.resources.Res
import kmptemplateapp.modules.features.pokemon.generated.resources.pokemon_error_unknown
import kmptemplateapp.modules.features.pokemon.generated.resources.pokemon_list_error_title
import kmptemplateapp.modules.features.pokemon.generated.resources.pokemon_retry
import kmptemplateapp.modules.features.pokemon.generated.resources.pokemon_search_hint
import org.jetbrains.compose.resources.stringResource

@Composable
fun PokemonListContent(
    state: PokemonListState,
    onItemClick: (String) -> Unit,
    onRefresh: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (state.items.isRefreshing) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        when (val items = state.items) {
            is LceState.Loading -> LoadingPlaceholder()

            is LceState.Content -> PokemonList(
                items = items.value,
                searchQuery = state.searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                onItemClick = onItemClick,
            )

            is LceState.Error -> ErrorPlaceholder(
                error = items.error,
                onRetry = onRefresh,
            )
        }
    }
}

@Composable
private fun PokemonList(
    items: List<PokemonListItem>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        item {
            AppTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                labelText = stringResource(Res.string.pokemon_search_hint),
                leadingIcon = AppIcons.Search24,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
        items(items, key = { it.id }) { item ->
            PokemonRow(item = item, onClick = { onItemClick(item.name) })
        }
    }
}

@Composable
private fun PokemonRow(
    item: PokemonListItem,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.name,
            modifier = Modifier.size(56.dp),
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = item.name,
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.contentPrimary,
        )
    }
}

@Composable
private fun LoadingPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorPlaceholder(error: Throwable?, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.pokemon_list_error_title),
            style = AppTheme.typography.headlineMedium,
            color = AppTheme.colors.contentPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error?.message ?: stringResource(Res.string.pokemon_error_unknown),
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.contentSecondary,
        )
        Spacer(modifier = Modifier.height(24.dp))
        AppButton(text = stringResource(Res.string.pokemon_retry), onClick = onRetry)
    }
}
