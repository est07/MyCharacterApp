package com.example.mycharacterapp.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.mycharacterapp.R
import com.example.mycharacterapp.domain.models.CharacterModel
import com.example.mycharacterapp.presentation.ui.theme.MyCharacterAppTheme
import com.example.mycharacterapp.presentation.viewmodels.CharactersViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.collections.mutableListOf

private const val CHARACTERS_TOOLBAR_TAG = "CharacterToolbar"
private const val CHARACTERS_TOOLBAR_TITLE_TAG = "CharacterToolbarTitle"
private const val ADD_CHARACTER_FLOATING_ACTION_BUTTON_TAG = "AddCharacterFloatingActionButton"

private const val CHARACTER_ITEM_TAG = "CharacterItem"
private const val CHARACTER_ITEM_IMAGE_TAG = "CharacterImageItem"

private const val CHARACTER_ITEM_NAME_TEXT_TAG = "CharacterItemNameText"
private const val CHARACTER_ITEM_STATUS_TEXT_TAG = "CharacterItemStatusText"
private const val CHARACTER_ITEM_DESCRIPTION_TEXT_TAG = "CharacterItemDescriptionText"

private const val DEFAULT_MAX_LINES = 1
private const val DEFAULT_WEIGHT = 1f

@Composable
fun CharactersRoute(
    charactersViewModel: CharactersViewModel = koinViewModel(),
    navigateToEdit : (CharacterModel) -> Unit
) {
    CharactersScreen(
        characterModel = charactersViewModel.characterList,
        navigateToEdit = navigateToEdit
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    characterModel: MutableList<CharacterModel>,
    navigateToEdit: (CharacterModel) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTagsAsResourceId = true },
        topBar = {

            CenterAlignedTopAppBar(
                modifier = Modifier.testTag(CHARACTERS_TOOLBAR_TAG),
                title = {
                    Text(
                        modifier = Modifier.testTag(CHARACTERS_TOOLBAR_TITLE_TAG),
                        text = stringResource(R.string.app_name),
                        maxLines = DEFAULT_MAX_LINES
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
                    .testTag(ADD_CHARACTER_FLOATING_ACTION_BUTTON_TAG)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(
                        R.string.characters_view_content_description_add_new_character
                    )
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {
            CitiesList(
                characterModel = characterModel,
                navigateToEdit = navigateToEdit
            )
        }
    }
}

@Composable
fun CitiesList(
    characterModel: MutableList<CharacterModel>,
    navigateToEdit: (CharacterModel) -> Unit
) {
    val lazyColumnListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = lazyColumnListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.size_16dp))
    ) {
        items(
            characterModel.size,
            key = { characterModel[it].id },
        ) {
            characterModel[it].let { character ->
                CharacterItem(
                    characterModel = character,
                    onItemSelected = { navigateToEdit(character) }
                )
            }
        }
    }
}

@Composable
fun CharacterItem(
    characterModel: CharacterModel,
    onItemSelected: (CharacterModel) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.size_6dp))
            .clickable { onItemSelected(characterModel) }
            .testTag(CHARACTER_ITEM_TAG),
        shape = RoundedCornerShape(
            dimensionResource(id = R.dimen.size_12dp)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.size_2dp)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.size_16dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier.testTag(CHARACTER_ITEM_IMAGE_TAG),
                horizontalAlignment = Alignment.End
            ) {

                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AsyncImage(
                        model = characterModel.image,
                        contentDescription = stringResource(
                            R.string.character_item_content_description_character_image
                        ),
                        modifier = Modifier.size(dimensionResource(R.dimen.size_80dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = dimensionResource(R.dimen.size_16dp))
                    .weight(DEFAULT_WEIGHT)
            ) {
                Text(
                    modifier = Modifier.testTag(CHARACTER_ITEM_NAME_TEXT_TAG),
                    text = characterModel.name ?: String(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.testTag(CHARACTER_ITEM_STATUS_TEXT_TAG),
                    text = stringResource(
                        R.string.character_item_status,
                        characterModel.status ?: String()
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(
                    modifier = Modifier.height(dimensionResource(id = R.dimen.size_4dp))
                )
                Text(
                    modifier = Modifier.testTag(CHARACTER_ITEM_DESCRIPTION_TEXT_TAG),
                    text = stringResource(
                        R.string.character_item_description,
                        characterModel.gender ?: String(),
                        characterModel.species ?: String()
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharactersRoutePreview() {
    CharactersScreen(
        characterModel = mutableListOf(
            CharacterModel(
                id = 1,
                name = "name",
                status = "status",
                species = "species",
                gender = "gender",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                created = "created"
            ),
            CharacterModel(
                id = 2,
                name = "name2",
                status = "status2",
                species = "species2",
                gender = "gender2",
                image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                created = "created2"
            )
        ),
        navigateToEdit = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun CityItemPreview() {
    MyCharacterAppTheme {
        CharacterItem(
            characterModel = CharacterModel(
                id = 1,
                name = "name",
                status = "status",
                species = "species",
                gender = "gender",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                created = "created"
            ),
            onItemSelected = {}
        )
    }
}