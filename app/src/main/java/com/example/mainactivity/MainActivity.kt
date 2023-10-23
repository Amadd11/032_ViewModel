package com.example.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mainactivity.Data.DataForm
import com.example.mainactivity.Data.DataSource.jenis
import com.example.mainactivity.ui.theme.MainActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TampilLayout()
                }
            }
        }
    }
}

@Composable
fun TampilLayout(
    modifier: Modifier = Modifier
){
    Card (modifier = Modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)){
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            ) {
            TampilText()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilText(cobaViewModel: CobaViewModel = viewModel()){

    var textForm by remember { mutableStateOf("")}
    var phoneForm by remember { mutableStateOf("")}
    var alamatForm by remember { mutableStateOf("")}

    val  context = LocalContext.current
    val dataForm: DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataForm = uiState

        OutlinedTextField(
            value = textForm,
            onValueChange = {textForm = it},
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            label = { Text(text = "Nama Lengkap")},
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
            )
        OutlinedTextField(
            value = phoneForm,
            onValueChange = {phoneForm = it},
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            label = { Text(text = "Nomor Telepon")},
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
    OutlinedTextField(
        value = alamatForm,
        onValueChange = {alamatForm = it},
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        label = { Text(text = "Alamat")},
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    )

        SelectJK(options = jenis.map { id -> context.resources.getString(id) },
            onSelectedChanged = {
                cobaViewModel.setJenisK(it)
            })
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {cobaViewModel.BacaData(textForm,phoneForm,alamatForm,dataForm.sex)
        }
        ) {
            Text(text = stringResource(R.string.submit),
                fontSize = 16.sp)

        }
        Spacer(modifier = Modifier.height(150.dp))
        TextHasil(
            namanya = cobaViewModel.namaUsr,
            telponnya =cobaViewModel.noTlp ,
            jenisnya = cobaViewModel.jenisKL,
            alamatnya = cobaViewModel.alamatUsr,
        )

}
@Composable
fun TextHasil(namanya:String,telponnya:String,jenisnya:String,alamatnya:String){
    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(text = "Nama : " + namanya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp))
        Text(text = "Telepon : " + telponnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp))
        Text(text = "Jenis Kelamin : " + jenisnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp))
        Text(text = "Alamat : " + alamatnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp))

    }

}
@Composable
fun SelectJK(
    options: List<String>,
    onSelectedChanged: (String) -> Unit = {}) {

    var selectedValue by rememberSaveable { mutableStateOf("")
    }
    Column (modifier = Modifier.padding(16.dp)) {
        options.forEach { item ->
            Row (
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectedChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
        ){
                RadioButton(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectedChanged(item)
                    }
                )
                Text(item)
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainActivityTheme {
        TampilLayout()
    }
}