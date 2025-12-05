package uk.ac.tees.mad.s3525839.recipenest.ui

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import uk.ac.tees.mad.s3525839.recipenest.viewmodel.AddRecipeViewModel
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddRecipeScreen(onRecipeAdded: () -> Unit) {
    val context = LocalContext.current
    val viewModel: AddRecipeViewModel = viewModel()
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            // The URI is already updated, no need to do anything here
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("Instructions") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
            maxLines = 8
        )

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Recipe Image",
                modifier = Modifier.size(128.dp)
            )
        }

        Button(onClick = {
            if (cameraPermissionState.status is PermissionStatus.Granted) {
                val file = File(context.cacheDir, "new_recipe_image.jpg")
                val newImageUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                imageUri = newImageUri
                launcher.launch(newImageUri)
            } else {
                cameraPermissionState.launchPermissionRequest()
            }
        }) {
            Text("Add Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.addRecipe(title, ingredients, instructions, imageUri, onRecipeAdded)
        }) {
            Text("Save Recipe")
        }
    }
}
