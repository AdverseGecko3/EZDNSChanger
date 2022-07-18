package com.adversegecko3.ezdnschanger

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
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
                    AllScreen()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun AllScreen() {
    Column {
        TopAppBarMain()
        Column(
            Modifier.padding(horizontal = 8.dp)
        ) {
            Options()
        }
    }
}

@Composable
fun TopAppBarMain() {
    TopAppBar(
        title = {
            Text(text = "EZ DNS Changer")
        },
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            ResourcesCompat.getDrawable(
                LocalContext.current.resources,
                R.mipmap.ic_dns_foreground, LocalContext.current.theme
            )?.let { drawable ->
                val bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth, drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                Image(
                    // painter = painterResource(R.mipmap.ic_launcher),
                    bitmap = bitmap.asImageBitmap(),
                    "App icon top"
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add item"
                )
            }
        },
        elevation = 0.dp
    )
}

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

@ExperimentalMaterialApi
@Preview(showSystemUi = true)
@Composable
fun PreviewMainActivity() {
    AllScreen()
}