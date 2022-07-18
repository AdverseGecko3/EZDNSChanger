package com.adversegecko3.ezdnschanger

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalMaterialApi
@Composable
fun ItemDNS(
    dns: DNS,
    pos: Int
) {
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = 20.dp,
            onClick = {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("DNS Server", dns.value)
                clipboard.setPrimaryClip(clip)
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                Toast.makeText(context, "Copied $dns", Toast.LENGTH_SHORT).show()
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column {
                    Text(
                        text = dns.name,
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 20.sp
                    )
                    Text(
                        text = dns.value,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                // TODO("Drop down menu to edit and delete")
                Box {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Options menu icon",
                            modifier = Modifier.height(IntrinsicSize.Max)
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            editItem(pos, DNS("jajaja", "jajaja"))
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit icon",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(text = "Edit")
                        }
                        DropdownMenuItem(onClick = {

                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete icon",
                                tint = Color.Red,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(text = "Delete", color = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun PreviewItemDNS() {
    ItemDNS(dns = DNS("Cloudflare", "1.1.1.1"), 0)
}