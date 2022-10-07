package com.ribbonapp.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ribbon.sdk.Ribbon

class MainComposeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainContent() }
    }

    @Preview
    @Suppress("FunctionNaming", "LongMethod")
    @Composable
    private fun MainContent(viewModel: MainViewModel = viewModel()) {
        MaterialTheme {
            val state by viewModel.state.collectAsState(initial = null)
            when (state) {
                is MainPageUiModel.Success -> Ribbon.showDialog(supportFragmentManager)
                is MainPageUiModel.Failure -> showError(state as MainPageUiModel.Failure)
                is MainPageUiModel.NoStudy -> showToast("no session running")
                else -> {}
            }

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(start = 30.dp, top = 0.dp, bottom = 0.dp, end = 30.dp),
            ) {

                Text(
                    text = "Sample",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(contentColor = White),
                        onClick = {
                            if (viewModel.isSdkInitialised()) {
                                viewModel.trigger()
                            } else {
                                showToast("Please initialise sdk first")
                            }
                        }
                    ) {
                        Text(text = "trigger")
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(contentColor = White),
                        onClick = {
                            viewModel.initialiseSdk(
                                context = this@MainComposeActivity
                            )
                        }
                    ) {
                        Text(text = "Initialise")
                    }
                }
            }
        }
    }

    private fun showError(uiState: MainPageUiModel.Failure) {
        showToast("error Message: ${uiState.errorMessage}, exception: ${uiState.error}")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}