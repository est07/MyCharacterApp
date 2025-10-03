package com.example.mycharacterapp.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.mycharacterapp.R
import com.example.mycharacterapp.domain.models.CharacterModel
import com.example.mycharacterapp.presentation.ui.theme.MyCharacterAppTheme
import com.example.mycharacterapp.presentation.viewmodels.CharactersViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar
import kotlin.random.Random

private const val CHARACTER_TOOLBAR_TAG = "CharacterToolbar"
private const val CHARACTER_TOOLBAR_TITLE_TAG = "CharacterToolbarTitle"
private const val CHARACTER_TOOLBAR_BACK_BUTTON_TAG = "CharacterToolbarBackButton"

private const val CHARACTER_VIEW_IMAGE_TAG = "CharacterViewImage"
private const val CHARACTER_VIEW_NAME_FIELD_TAG = "CharacterViewNameField"
private const val CHARACTER_VIEW_STATUS_FIELD_TAG = "CharacterViewStatusField"
private const val CHARACTER_VIEW_GENDER_FIELD_TAG = "CharacterViewGenderField"
private const val CHARACTER_VIEW_SPECIES_FIELD_TAG = "CharacterViewSpeciesField"
private const val CHARACTER_VIEW_REQUIRED_FIELD_TAG = "CharacterViewRequiredField"
private const val CHARACTER_VIEW_ACTION_BUTTON_TAG = "CharacterViewActionButtonField"
private const val CHARACTER_VIEW_DELETE_BUTTON_TAG = "CharacterViewActionDeleteField"

private const val DEFAULT_CHARACTER_ID = 0
private const val DEFAULT_MAX_LINES = 1
private const val DEFAULT_IMAGE = "IMAGE"
private const val DEFAULT_RANDOM_MIN_VALUE = 1
private const val DEFAULT_RANDOM_MAX_VALUE = 9999

@Composable
fun EditCharacterRoute(
    charactersViewModel: CharactersViewModel = koinViewModel(),
    characterModel: CharacterModel,
    navigateBack: () -> Unit
) {
    EditCharacterScreen(
        characterModel = characterModel,
        navigateBack = navigateBack,
        onActionCharacter = {
            with(it) {
                if (id == DEFAULT_CHARACTER_ID) {
                    charactersViewModel.createCharacter(
                        CharacterModel(
                            id = generateRandomNumber(),
                            name = name,
                            status = status,
                            gender = gender,
                            species = species,
                            image = DEFAULT_IMAGE,
                            created = created
                        )
                    )
                } else {
                    charactersViewModel.updateCharacter(it)
                }
            }
            navigateBack()
        },
        onDeleteCharacter = {
            charactersViewModel.deleteCharacter(it)
            navigateBack()
        },
    )
}

private fun generateRandomNumber(): Int {
    val calendar = Calendar.getInstance()
    val randomGenerator = Random(calendar.get(Calendar.MILLISECOND))
    return randomGenerator.nextInt(
        DEFAULT_RANDOM_MIN_VALUE,
        DEFAULT_RANDOM_MAX_VALUE
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditCharacterScreen(
    characterModel: CharacterModel,
    navigateBack: () -> Unit,
    onActionCharacter: (CharacterModel) -> Unit,
    onDeleteCharacter: (CharacterModel) -> Unit
) {
    var isEditCharacter by rememberSaveable {
        mutableStateOf(characterModel.id != DEFAULT_CHARACTER_ID)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTagsAsResourceId = true },
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.testTag(CHARACTER_TOOLBAR_TAG),
                title = {
                    Text(
                        modifier = Modifier.testTag(CHARACTER_TOOLBAR_TITLE_TAG),
                        text = stringResource(
                            if (isEditCharacter) {
                                R.string.character_view_toolbar_edit_text
                            } else {
                                R.string.character_view_toolbar_create_text
                            }
                        ),
                        maxLines = DEFAULT_MAX_LINES
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.testTag(CHARACTER_TOOLBAR_BACK_BUTTON_TAG),
                        onClick = { navigateBack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {

            var characterData by remember { mutableStateOf(CharacterModel()) }


            var isSaveCharacterEnable by rememberSaveable {
                mutableStateOf(isEditCharacter)
            }

            Box(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.size_16dp))
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.size_150dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = characterModel.image,
                    contentDescription = stringResource(
                        R.string.character_item_content_description_character_image
                    ),
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.size_150dp))
                        .testTag(CHARACTER_VIEW_IMAGE_TAG),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.icon_outline_100)
                )
            }

            CharacterForm(
                characterModel = characterModel,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.size_16dp)
                ),
                onValueChange = { characterData = it },
                onSaveCharacterEnable = {
                    isSaveCharacterEnable = it
                }
            )

            ActionsButtons(
                isEditCharacter = isEditCharacter,
                isSaveCharacterEnable = isSaveCharacterEnable,
                onActionCharacter = { onActionCharacter(characterData) },
                onDeleteCharacter = { onDeleteCharacter(characterData) }
            )
        }
    }
}

@Composable
private fun CharacterForm(
    characterModel: CharacterModel,
    modifier: Modifier = Modifier,
    onValueChange: (CharacterModel) -> Unit = {},
    onSaveCharacterEnable: (Boolean) -> Unit = {},
) {

    var textName by rememberSaveable { mutableStateOf(characterModel.name ?: String()) }
    var textStatus by rememberSaveable { mutableStateOf(characterModel.status ?: String()) }
    var textGender by rememberSaveable { mutableStateOf(characterModel.gender ?: String()) }
    var textSpecies by rememberSaveable {
        mutableStateOf(characterModel.species ?: String())
    }

    Column(
        modifier = modifier,
        verticalArrangement =
            Arrangement.spacedBy(dimensionResource(id = R.dimen.size_16dp))
    ) {
        OutlinedTextField(
            value = textName,
            onValueChange = { textName = it },
            label = { Text(stringResource(R.string.character_view_name_text)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(CHARACTER_VIEW_NAME_FIELD_TAG),
            singleLine = true
        )
        OutlinedTextField(
            value = textStatus,
            onValueChange = { textStatus = it },
            label = { Text(stringResource(R.string.character_view_status_text)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(CHARACTER_VIEW_STATUS_FIELD_TAG),
            singleLine = true
        )
        OutlinedTextField(
            value = textGender,
            onValueChange = { textGender = it },
            label = { Text(stringResource(R.string.character_view_gender_text)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(CHARACTER_VIEW_GENDER_FIELD_TAG),
            singleLine = true
        )
        OutlinedTextField(
            value = textSpecies,
            onValueChange = { textSpecies = it },
            label = { Text(stringResource(R.string.character_view_specie_text)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(CHARACTER_VIEW_SPECIES_FIELD_TAG),
            singleLine = true
        )

        if (
            validateFields(
                name = textName,
                status = textStatus,
                gender = textGender,
                species = textSpecies
            )
        ) {
            Text(
                text = stringResource(R.string.character_view_required_fields_text),
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.size_16dp))
                    .testTag(CHARACTER_VIEW_REQUIRED_FIELD_TAG)
            )
            onSaveCharacterEnable(false)
        } else {
            onSaveCharacterEnable(true)
            onValueChange(
                characterModel.run {
                    CharacterModel(
                        id = id,
                        name = textName,
                        status = textStatus,
                        gender = textGender,
                        species = textSpecies,
                        image = image,
                        created = created
                    )
                }
            )
        }
    }
}

private fun validateFields(
    name: String,
    status: String,
    gender: String,
    species: String
) = name.isEmpty() || status.isEmpty() || gender.isEmpty() || species.isEmpty()

@Composable
private fun ActionsButtons(
    isEditCharacter: Boolean,
    isSaveCharacterEnable: Boolean,
    onActionCharacter: () -> Unit = {},
    onDeleteCharacter: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.size_16dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Button(
            onClick = { onActionCharacter() },
            enabled = isSaveCharacterEnable,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.size_80dp))
                .testTag(CHARACTER_VIEW_ACTION_BUTTON_TAG),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_56dp)),
                imageVector = if (isEditCharacter) {
                    Icons.Default.Edit
                } else {
                    Icons.Default.Add
                },
                contentDescription = stringResource(
                    if (isEditCharacter) {
                        R.string.character_view_content_description_edit_character
                    } else {
                        R.string.character_view_content_description_add_character
                    }
                )
            )
        }

        if (isEditCharacter){
            Button(
                onClick = { onDeleteCharacter() },
                enabled = isSaveCharacterEnable,
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.size_16dp))
                    .size(dimensionResource(id = R.dimen.size_80dp))
                    .testTag(CHARACTER_VIEW_DELETE_BUTTON_TAG),
                shape = CircleShape,
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.size_56dp)),
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(
                        R.string.character_view_content_description_delete_character
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterFormPreview() {
    MyCharacterAppTheme {
        CharacterForm(
            characterModel = CharacterModel(
                id = 1,
                name = "name",
                status = "status",
                species = "species",
                gender = "gender",
                image = "",
                created = "created"
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionsButtonsPreview() {
    MyCharacterAppTheme {
        ActionsButtons(
            isEditCharacter = true,
            isSaveCharacterEnable = true,
            onActionCharacter = { },
            onDeleteCharacter = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditCharacterScreenPreview() {
    EditCharacterScreen(
        characterModel = CharacterModel(
            id = 1,
            name = "name",
            status = "status",
            species = "species",
            gender = "gender",
            image = "",
            created = "created"
        ),
        navigateBack = {},
        onActionCharacter = {},
        onDeleteCharacter = {}
    )
}
