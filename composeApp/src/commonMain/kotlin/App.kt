import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Long.toStopWatchFormat(): String {
  this.milliseconds.toComponents { hours, minutes, seconds, _ ->
    val minutesString = minutes.toString().padStart(2, '0')
    val secondsString = seconds.toString().padStart(2, '0')
    return "$minutesString:$secondsString"
  }
}
@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
  MaterialTheme {

    var remainingTime by remember { mutableStateOf(1000 * 60 * 20L) }
    val countDownTimer = remember {
      CountDownTimer(1000 * 60 * 20, 1000) {
        remainingTime = it
      }
    }

    var showContent by remember { mutableStateOf(false) }
    val greeting = remember { Greeting().greet() }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
      Button(onClick = {
        showContent = !showContent
        countDownTimer.startTimer()
      }) {
        Text("Click me!")
      }
      Text("Remaining time: ${remainingTime.toStopWatchFormat()}")
      AnimatedVisibility(showContent) {
        Column(
          Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Image(painterResource("compose-multiplatform.xml"), null)
          Text("Compose: $greeting")
        }
      }
    }
  }
}