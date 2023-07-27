package com.ribbonapp.sample

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribbon.sdk.Ribbon
import com.ribbon.sdk.StudyStateCallback
import com.ribbon.sdk.model.StudyState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _uiState = MutableSharedFlow<MainPageUiModel>()

    val state: SharedFlow<MainPageUiModel> = _uiState

    private var sdkInitialised: MutableState<Boolean> = mutableStateOf(false)


    fun trigger() {
        viewModelScope.launch {
//            val attributes = mapOf<String, Any>(
//                "key1" to 1,
//                "key2" to "something text"
//            )
//            Ribbon.addAttributes(attributes)
            Ribbon.trigger(
                trigger = "global",
                callback = object : StudyStateCallback {
                    override fun onResult(studyState: StudyState) {
                        updateUiState(studyState)
                    }
                }
            )
        }
    }

    private fun updateUiState(studyState: StudyState) {
        viewModelScope.launch {
            val uiState = mapResultToUi(studyState)
            _uiState.emit(uiState)
        }
    }

    fun initialiseSdk(context: Context) {
        // TODO: Edit your configuration here
        val organisationID = ""
        Ribbon.configure(organisationID, context.applicationContext)
//        To set User
//        Ribbon.setIdentity("enter user here")
//        To enable preview mode, it's false by default
//        Ribbon.setPreview(true)
        sdkInitialised.value = true
    }

    fun isSdkInitialised() = sdkInitialised.value

    private fun mapResultToUi(result: StudyState): MainPageUiModel {
        return when (result) {
            is StudyState.Failure -> MainPageUiModel.Failure(
                errorMessage = "something went wrong",
                error = result.error
            )
            StudyState.NoStudy -> MainPageUiModel.NoStudy
            // This is to show the study details can be accessed for internal analytics
            is StudyState.StudyReady -> MainPageUiModel.Success(
                studyReady = result,
            )
        }
    }
}

sealed class MainPageUiModel {
    data class Success(
        val studyReady: StudyState.StudyReady,
    ) : MainPageUiModel()

    data class Failure(val errorMessage: String, val error: Throwable? = null) : MainPageUiModel()
    object NoStudy : MainPageUiModel()
}