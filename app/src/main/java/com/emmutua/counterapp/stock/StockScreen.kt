package com.emmutua.counterapp.stock

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.emmutua.counterapp.R

@Composable
fun StockScreen(
    stockViewModel: StockViewModel = viewModel()
) {
    val beansState by stockViewModel.beansState.collectAsState()
    val maizeState by stockViewModel.maizeState.collectAsState()
    val maizeProgress = stockViewModel.calculateProgress(maizeState.numberOfKgsRemaining)
    val beansProgress = stockViewModel.calculateProgress(beansState.numberOfKgsRemaining)
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(text = "StockAvailable (Kgs)", style = TextStyle(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(20.dp))
        Row (
            modifier = Modifier.fillMaxWidth(0.7f),
            horizontalArrangement = Arrangement.SpaceAround
        ){
           StockContainer(
               stockName = "Maize",
               maizeProgress = maizeProgress,
               numberOfKgsRemaining = maizeState.numberOfKgsRemaining,
               onAdd = {
                       stockViewModel.addMaizeStock(
                           onSuccess = {},
                           onError = {showToast(context,it)}
                       )
               },
               onMinus = {
                   stockViewModel.reduceMaizeStock(
                       onSuccess = {},
                       onError = {showToast(context,it)}
                   )
               }
           )
           StockContainer(
               stockName = "Beans",
               maizeProgress = beansProgress,
               numberOfKgsRemaining = beansState.numberOfKgsRemaining,
               onAdd = {
                   stockViewModel.addBeansStock(
                       onSuccess = {},
                       onError = {showToast(context,it)}
                   )
               },
               onMinus = {
                   stockViewModel.reduceBeansStock(
                       onSuccess = {},
                       onError = {showToast(context,it)}
                   )
               }
           )
       }
    }
}

@Preview(showSystemUi = true)
@Composable
fun StringTokenizer() {
    StockScreen()
}

@Composable
fun StockContainer(
    stockName : String,
    maizeProgress : Float,
    numberOfKgsRemaining : Float,
    onMinus : () -> Unit,
    onAdd : () -> Unit
) {
    Column (verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        Text(text = stockName)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp),
                progress = maizeProgress,
                strokeWidth = 10.dp,
                trackColor = MaterialTheme.colorScheme.errorContainer,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(text = "$numberOfKgsRemaining Kgs")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onMinus, modifier = Modifier.padding(bottom = 15.dp)) {
                Icon(painter = painterResource(id = R.drawable.minimize), contentDescription = "Clear")
            }
            IconButton(onClick = onAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }

        }
    }
}

fun showToast(
    context : Context,
    text : String
){
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
