import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.max

class CountDownTimer(
  private val millisInFuture: Long,
  private val countDownInterval: Long,
  private val action: (remainingMillis: Long) -> Unit
) {

  private val parentJob = SupervisorJob()
  private val scope = CoroutineScope(Dispatchers.Default + parentJob)
  private var currentJob: Job? = null

  private fun startCountDownTimer(
    millisInFuture: Long,
    countDownInterval: Long,
    action: (remainingMillis: Long) -> Unit
  ) = scope.launch {
    var remainingMillis = millisInFuture - countDownInterval
    withContext(Dispatchers.Main) {
      action(remainingMillis)
    }
    while (remainingMillis > 0) {
      delay(countDownInterval)
      remainingMillis = max(remainingMillis - countDownInterval, 0)
      withContext(Dispatchers.Main) {
        action(remainingMillis)
      }
    }
  }

  fun startTimer() {
    currentJob = startCountDownTimer(millisInFuture, countDownInterval, action)
  }

  fun cancelTimer() {
    currentJob?.cancel()
  }
}