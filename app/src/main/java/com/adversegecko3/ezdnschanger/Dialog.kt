package com.adversegecko3.ezdnschanger

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DialogAddEditDNS(
    dns: DNS = DNS(),
    pos: Int = 0,
    dialogState: MutableState<Boolean>,
    viewModel: DNSViewModel,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        var textName by remember { mutableStateOf("") }
        var textValue by remember { mutableStateOf("") }
        if (!dns.name.isNullOrEmpty() && !dns.value.isNullOrEmpty()) {
            textName = dns.name
            textValue = dns.value
        }

        Card(shape = RoundedCornerShape(25.dp)) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Column {
                    TextField(
                        value = textName,
                        onValueChange = {
                            textName = it
                        },
                        label = { Text(text = "Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                    TextField(
                        value = textValue,
                        onValueChange = {
                            textValue = it
                        },
                        label = { Text(text = "Value") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(onClick = {
                        if (dns.value.isNullOrEmpty() && dns.value.isNullOrEmpty()) {
                            viewModel.addDNS(dns = DNS(textName, textValue))
                        } else {
                            viewModel.updateDNS(pos = pos, dns = DNS(textName, textValue))
                        }
                        dialogState.value = false
                    }) {
                        Text(text = "Accept", color = MaterialTheme.colors.onSurface)
                    }
                    Button(onClick = {
                        dialogState.value = false
                    }) {
                        Text(text = "Cancel", color = MaterialTheme.colors.onSurface)
                    }
                }
            }
        }
    }
}

@Composable
fun DialogDeleteDNS(
    dns: DNS = DNS(),
    dialogState: MutableState<Boolean>,
    viewModel: DNSViewModel,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(shape = RoundedCornerShape(25.dp)) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Are you sure you want to delete the ${dns.name} DNS?",
                    textAlign = TextAlign.Justify,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(onClick = {
                        viewModel.deleteDNS(dns = dns)
                        dialogState.value = false
                    }) {
                        Text(text = "Accept", color = MaterialTheme.colors.onSurface)
                    }
                    Button(onClick = {
                        dialogState.value = false
                    }) {
                        Text(text = "Cancel", color = MaterialTheme.colors.onSurface)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDialogDNS() {
    val dialogState = remember { mutableStateOf(false) }
    val vm: DNSViewModel = viewModel()
    DialogAddEditDNS(
        dns = DNS(
            "1.1.1.1",
            "1dot1dot1dot1.cloudflare-dns.com"
        ),
        pos = 1,
        dialogState = dialogState,
        viewModel = vm,
        onDismissRequest = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDialogDeleteDNS() {
    val dialogState = remember { mutableStateOf(false) }
    val vm: DNSViewModel = viewModel()
    DialogDeleteDNS(
        dns = DNS(
            "1.1.1.1",
            "1dot1dot1dot1.cloudflare-dns.com"
        ),
        dialogState = dialogState,
        viewModel = vm,
        onDismissRequest = {}
    )
}