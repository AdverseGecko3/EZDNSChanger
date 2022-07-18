package com.adversegecko3.ezdnschanger

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalMaterialApi
@Composable
fun WriteGranted() {
    val context = LocalContext.current
    val alItems = mutableListOf(
        DNS(
            "AdGuard",
            "dns.adguard.com"
        ),
        DNS(
            "1.1.1.1",
            "1dot1dot1dot1.cloudflare-dns.com"
        ),
        DNS(
            "8.8.8.8",
            "dns.google"
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .background(MaterialTheme.colors.primary)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, bottom = 8.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Sort icon",
                    tint = Color.White
                )
                Text(
                    text = "Sort type",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 4.dp),
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(alItems) { index, item ->
                    ItemDNS(dns = item, pos = index)
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Card(
                    onClick = {
                        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                        intent.data = Uri.parse("package:${context.packageName}")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    },
                    shape = RoundedCornerShape(15.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                    elevation = 20.dp
                ) {
                    Text(
                        text = "Change Writing access",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

fun editItem(pos: Int, dns: DNS) {

}

@ExperimentalMaterialApi
@Preview(showSystemUi = true)
@Composable
fun PreviewWriteGranted() {
    WriteGranted()
}