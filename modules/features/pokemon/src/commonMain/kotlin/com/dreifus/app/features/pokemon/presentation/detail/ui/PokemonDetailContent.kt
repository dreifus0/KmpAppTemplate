package com.dreifus.app.features.pokemon.presentation.detail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dreifus.app.data.pokemon.PokemonDetail
import com.dreifus.app.features.pokemon.presentation.detail.PokemonDetailState
import com.dreifus.arch.lce.LceState
import com.dreifus.navigation.ui.toolbar.ShevronBackToolbar
import com.dreifus.template.uikit.button.AppButton
import com.dreifus.template.uikit.preview.AppPreview
import com.dreifus.template.uikit.style.AppTheme
import androidx.compose.ui.tooling.preview.Preview
import kmptemplateapp.modules.features.pokemon.generated.resources.Res
import kmptemplateapp.modules.features.pokemon.generated.resources.pokemon_detail_error_title
import kmptemplateapp.modules.features.pokemon.generated.resources.pokemon_detail_height
import kmptemplateapp.modules.features.pokemon.generated.resources.pokemon_detail_title
import kmptemplateapp.modules.features.pokemon.generated.resources.pokemon_detail_weight
import kmptemplateapp.modules.features.pokemon.generated.resources.pokemon_error_unknown
import kmptemplateapp.modules.features.pokemon.generated.resources.pokemon_retry
import org.jetbrains.compose.resources.stringResource

@Composable
fun PokemonDetailContent(
    state: PokemonDetailState,
    fallbackName: String,
    onRetry: () -> Unit,
) {
    Scaffold(
        containerColor = AppTheme.colors.backgroundBase,
        topBar = {
            ShevronBackToolbar(title = stringResource(Res.string.pokemon_detail_title))
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (val detail = state.detail) {
                is LceState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )

                is LceState.Content -> DetailBody(detail = detail.value)

                is LceState.Error -> ErrorBody(
                    fallbackName = fallbackName,
                    error = detail.error,
                    onRetry = onRetry,
                )
            }
        }
    }
}

@Composable
private fun DetailBody(detail: PokemonDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = detail.imageUrl,
            contentDescription = detail.name,
            modifier = Modifier.size(180.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = detail.name,
            style = AppTheme.typography.headlineLarge,
            color = AppTheme.colors.contentPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = detail.types.joinToString(separator = " · "),
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.contentSecondary,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            StatBlock(
                label = stringResource(Res.string.pokemon_detail_height),
                value = "${detail.heightDecimeters / 10.0} m"
            )
            StatBlock(
                label = stringResource(Res.string.pokemon_detail_weight),
                value = "${detail.weightHectograms / 10.0} kg"
            )
        }
    }
}

@Composable
private fun StatBlock(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = AppTheme.typography.bodySmall,
            color = AppTheme.colors.contentSecondary,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = AppTheme.typography.headlineMedium,
            color = AppTheme.colors.contentPrimary,
        )
    }
}

@Composable
private fun ErrorBody(
    fallbackName: String,
    error: Throwable?,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.pokemon_detail_error_title, fallbackName),
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

private val previewDetail = PokemonDetail(
    id = 1,
    name = "bulbasaur",
    heightDecimeters = 7,
    weightHectograms = 69,
    imageUrl = null,
    types = listOf("grass", "poison"),
)

@Preview
@Composable
private fun PokemonDetailLoadingPreview() {
    AppPreview {
        PokemonDetailContent(
            state = PokemonDetailState(),
            fallbackName = "bulbasaur",
            onRetry = {},
        )
    }
}

@Preview
@Composable
private fun PokemonDetailContentPreview() {
    AppPreview {
        PokemonDetailContent(
            state = PokemonDetailState(
                name = "bulbasaur",
                detail = LceState.Content(previewDetail),
            ),
            fallbackName = "bulbasaur",
            onRetry = {},
        )
    }
}

@Preview
@Composable
private fun PokemonDetailErrorPreview() {
    AppPreview {
        PokemonDetailContent(
            state = PokemonDetailState(
                name = "bulbasaur",
                detail = LceState.Error(RuntimeException("Not found")),
            ),
            fallbackName = "bulbasaur",
            onRetry = {},
        )
    }
}
