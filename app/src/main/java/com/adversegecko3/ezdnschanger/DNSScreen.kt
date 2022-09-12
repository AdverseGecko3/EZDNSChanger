@file:OptIn(ExperimentalMaterialApi::class)

package com.adversegecko3.ezdnschanger

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DNSScreen(
    viewModel: DNSViewModel = viewModel()
) {
    Column {
        TopAppBarMain(viewModel)
        Column(
            Modifier.padding(horizontal = 8.dp)
        ) {
            DNSList(viewModel)
        }
    }
}

@Composable
fun TopAppBarMain(
    viewModel: DNSViewModel
) {
    val dialogAddState = remember { mutableStateOf(false) }
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
            IconButton(onClick = {
                dialogAddState.value = true
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add item"
                )
            }
        },
        elevation = 0.dp
    )
    if (dialogAddState.value) {
        DialogAddEditDNS(
            dialogState = dialogAddState,
            viewModel = viewModel,
            onDismissRequest = { dialogAddState.value = false }
        )
    }
}

@Composable
fun DNSList(
    viewModel: DNSViewModel
) {
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
                itemsIndexed(viewModel.alItems) { index, item ->
                    ItemDNS(dns = item, pos = index, viewModel = viewModel)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showSystemUi = true)
@Composable
fun PreviewMainActivity() {
    DNSScreen()
}