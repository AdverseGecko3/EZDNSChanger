package com.adversegecko3.ezdnschanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import com.adversegecko3.ezdnschanger.ui.theme.EZDNSChangerTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EZDNSChangerTheme {
                Surface {
                    val systemUiController = rememberSystemUiController()
                    val color = MaterialTheme.colors.surface
                    SideEffect {
                        systemUiController.setSystemBarsColor(
                            color = color
                        )
                    }
                    DNSScreen()
                }
            }
        }
    }
}

/*
@ExperimentalMaterialApi
@Composable
fun Options() {
    val context = LocalContext.current
    val canWrite = remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    canWrite.value = Settings.System.canWrite(context)
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    if (canWrite.value) {
        WriteGranted()
    } else {
        WriteNotGranted()
    }
}
*/