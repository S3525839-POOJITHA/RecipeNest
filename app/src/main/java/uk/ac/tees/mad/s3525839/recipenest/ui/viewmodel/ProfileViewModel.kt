package uk.ac.tees.mad.s3525839.recipenest.ui.viewmodel

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private val _user = MutableStateFlow(auth.currentUser)
    val user: StateFlow<com.google.firebase.auth.FirebaseUser?> = _user

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    fun uploadProfilePicture(uri: Uri) {
        val currentUser = _user.value ?: return
        val storageRef = storage.reference.child("profile_pictures/${currentUser.uid}")

        viewModelScope.launch {
            _isUploading.value = true
            storageRef.putFile(uri)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setPhotoUri(downloadUri)
                            .build()
                        currentUser.updateProfile(profileUpdates)
                            .addOnCompleteListener {
                                _isUploading.value = false
                                if (it.isSuccessful) {
                                    // Re-fetch the user to get the updated photoUrl
                                    _user.value = auth.currentUser
                                } else {
                                    Toast.makeText(getApplication(), "Failed to update profile.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
                .addOnFailureListener {
                    _isUploading.value = false
                    Toast.makeText(getApplication(), "Failed to upload image: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
