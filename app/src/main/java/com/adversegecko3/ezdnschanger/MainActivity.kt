package com.adversegecko3.ezdnschanger

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.adversegecko3.ezdnschanger.ui.theme.EZDNSChangerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EZDNSChangerTheme {
                Surface {
                    AllScreen()
                }
            }
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun AllScreen() {
    Options()
}

@ExperimentalPermissionsApi
@Composable
fun Options() {
    val context = LocalContext.current
    val canWrite: MutableState<Boolean> = remember { mutableStateOf(false) }

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
        Options2()
    } else {
        LetMeWrite()
    }
}

@Composable
fun LetMeWrite() {
    val context = LocalContext.current
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Writing permission was permanently denied. " +
                        "If you want to enable it, you should do it through the app setting.",
                modifier = Modifier.padding(32.dp)
            )
            Button(
                onClick = {
                    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                    intent.data = Uri.parse("package:${context.packageName}")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            ) {
                Text(text = "Enable Writing access")
            }
        }
    }
}

@Composable
fun Options2() {
    val context = LocalContext.current
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DNSButton(
                dns = "dns.adguard.com",
                buttonText = "AdGuard",
                context = context
            )
            DNSButton(
                dns = "1dot1dot1dot1.cloudflare-dns.com",
                buttonText = "1.1.1.1",
                context = context
            )
            DNSButton(
                dns = "dns.google",
                buttonText = "8.8.8.8",
                context = context
            )
            Button(
                onClick = {
                    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                    intent.data = Uri.parse("package:${context.packageName}")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            ) {
                Text(text = "Enable Writing access")
            }
        }
    }
}

@Composable
fun DNSButton(dns: String, buttonText: String = dns, context: Context) {
    OutlinedButton(
        onClick = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("DNS Server", dns)
            clipboard.setPrimaryClip(clip)
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            Toast.makeText(context, "Copied $dns", Toast.LENGTH_SHORT).show()
        },
        border = BorderStroke(1.dp, MaterialTheme.colors.primary)
    ) {
        Text(text = buttonText)
    }
}

@ExperimentalPermissionsApi
@Preview(showSystemUi = true)
@Composable
fun Preview() {
    AllScreen()
}