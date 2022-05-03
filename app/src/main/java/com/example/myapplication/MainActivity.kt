package com.example.myapplication
import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // Greeting("Android")
                    MyScreen( myclick = ::myclick)
                }
            }
        }
    }
    fun myclick(){
        Toast.makeText(applicationContext,"按下button", Toast.LENGTH_SHORT).show()

        requestPermissionLauncher.launch( CAMERA )
    }
    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            permit->
        if (permit){
            Toast.makeText(applicationContext,"同意", Toast.LENGTH_SHORT).show()
            startLauncher.launch(Intent( MediaStore.ACTION_IMAGE_CAPTURE))


        }
    }

    var startLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        Toast.makeText(applicationContext,"拍完照", Toast.LENGTH_SHORT).show()

        if(result.resultCode == Activity.RESULT_OK){
          var bitmap =  result.data?.extras?.get("data") as Bitmap
            img = bitmap.asImageBitmap()
            mutableState.value = true
        }
    }
}
 lateinit var img : ImageBitmap

  val mutableState = mutableStateOf<Boolean>(false)
@Composable
fun MyScreen( myclick: ()->Unit  ){
    Column(){
        Text( "準備拍照")
        Button(onClick = { /*TODO*/
            myclick()
        }) {
            Text("按我拍照")
        }
        if(mutableState.value){
            Image(BitmapPainter ( img), "")
        }

    }
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}