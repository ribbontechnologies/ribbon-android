package com.ribbonapp.sample

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribbon.sdk.Ribbon
import com.ribbon.sdk.StudyStateCallback
import com.ribbon.sdk.model.FontConfiguration
import com.ribbon.sdk.model.FontConfigurationTypeface
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

    fun initialiseSdk(context: FragmentActivity) {
        // TODO: Edit your configuration here
        val organisationID = ""

        // This is how you would set the font using resources,
        // This is an example using the different fonts that we use
        // all arguments are optional
        val fontConfiguration = FontConfiguration(
            title = R.font.crimson_text_bold,
            body = R.font.crimson_text_regular,
            callouts = R.font.crimson_text_italic,
            button = R.font.crimson_text_semi_bold_italic
        )
        // This is how you would set the font using assets, from the assets folder
        // please specify if you have any folders e.g. if the path to the fonts in your assets is
        // assets/fonts/title/my_title_font.ttf then parse through /fonts/title/my_title_font.ttf
        // for the title parameter

        val fontConfigurationTypeface = FontConfigurationTypeface(
            title = "fonts/title/crimson_text_bold.ttf",
            body = "fonts/body/crimson_text_regular.ttf",
            callouts = "crimson_text_italic.ttf",
            button = "crimson_text_semi_bold_italic.ttf",
        )
        // Configure now takes the different font configuration as an argument, so the below would work too
        Ribbon.configure(organisationID, context, fontConfigurationTypeface)
        // Ribbon.configure(organisationID, context, fontConfiguration)
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

            else -> {
                MainPageUiModel.NoStudy
            }
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