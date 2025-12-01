package uk.ac.tees.mad.s3525839.recipenest.ui

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import uk.ac.tees.mad.s3525839.recipenest.R
import uk.ac.tees.mad.s3525839.recipenest.ui.viewmodel.ProfileViewModel
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = viewModel()) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val user by profileViewModel.user.collectAsState()
    val isUploading by profileViewModel.isUploading.collectAsState()

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageUri?.let { uri ->
                profileViewModel.uploadProfilePicture(uri)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            AsyncImage(
                model = imageUri ?: user?.photoUrl ?: R.drawable.ic_launcher_foreground,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            if (isUploading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Username: ${user?.displayName ?: ""}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Email: ${user?.email ?: ""}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (cameraPermissionState.status is PermissionStatus.Granted) {
                val file = File(context.cacheDir, "temp_image.jpg")
                val newImageUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                imageUri = newImageUri
                launcher.launch(newImageUri)
            } else {
                cameraPermissionState.launchPermissionRequest()
            }
        }) {
            Text("Change Profile Picture")
        }
    }
}
