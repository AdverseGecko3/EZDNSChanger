package com.adversegecko3.ezdnschanger

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@ExperimentalMaterialApi
@Composable
fun ItemDNS(
    dns: DNS,
    pos: Int,
    viewModel: DNSViewModel
) {
    val context = LocalContext.current
    val dialogState = remember { mutableStateOf(false) }
    val dialogType = remember { mutableIntStateOf(0) }
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

                Toast.makeText(context, "Copied ${dns.value}", Toast.LENGTH_SHORT).show()
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .height(IntrinsicSize.Max)
                ) {
                    Text(
                        text = dns.name!!,
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 20.sp
                    )
                    Text(
                        text = dns.value!!,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                Box {
                    IconButton(
                        onClick = { showMenu = !showMenu }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Options menu icon",
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            showMenu = !showMenu
                            dialogType.intValue = 0
                            dialogState.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit icon",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(text = "Edit")
                        }
                        DropdownMenuItem(onClick = {
                            showMenu = !showMenu
                            dialogType.intValue = 1
                            dialogState.value = true
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
    if (dialogState.value) {
        if (dialogType.intValue == 0) {
            DialogAddEditDNS(
                dns = dns,
                pos = pos,
                dialogState = dialogState,
                viewModel = viewModel,
                onDismissRequest = { dialogState.value = false })
        } else {
            DialogDeleteDNS(
                dns = dns,
                dialogState = dialogState,
                viewModel = viewModel,
                onDismissRequest = { dialogState.value = false })
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun PreviewItemDNS() {
    val viewModel: DNSViewModel = viewModel()
    ItemDNS(dns = DNS("Cloudflare", "1.1.1.1"), 0, viewModel)
}