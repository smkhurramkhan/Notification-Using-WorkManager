package com.example.notificationworkmanager


import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 1 Create Variables to hold user's selection
        var chosenYear = 0
        var chosenMonth = 0
        var chosenDay = 0
        var chosenHour = 0
        var chosenMin = 0

        // 2 Access View Components using their Id
        val descriptionText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.setBtn)
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        val today = Calendar.getInstance()

        // 3 initialize of datePicker using the current day as starting parameters and then
        // pass the userSelected to the variables created
        datePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            chosenYear = year
            chosenMonth = month
            chosenDay = day
        }
        // 4 Add the Listener to gain access to user selection in the TimePicker and
        // then assign the selected values to the variables created above
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            chosenHour = hour
            chosenMin = minute
        }

        // 5 Add the Listener to listen to click events and execute the code to setNotification
        button.setOnClickListener {

            // 6 Get the DateTime the user selected
            val userSelectedDateTime = Calendar.getInstance()
           // userSelectedDateTime.set(chosenYear, chosenMonth, chosenDay, chosenHour, chosenMin)

            userSelectedDateTime.set(datePicker.year, datePicker.month, datePicker.dayOfMonth,
                timePicker.currentHour, timePicker.currentMinute, 0)

            Timber.d("System time is ${System.currentTimeMillis()}")

            val startTime: Long = userSelectedDateTime.timeInMillis

            Timber.d("start time is $startTime")

            // 7 Next get DateTime for today
            val todayDateTime = Calendar.getInstance()

            // 8
            val delayInSeconds =
                (userSelectedDateTime.timeInMillis / 1000L) - (System.currentTimeMillis() / 1000L)

            Timber.d("delay in seconds is $delayInSeconds")

            // 9
            createWorkRequest(
                descriptionText.text.toString(),
                delayInSeconds
            )

            // 10
            Toast.makeText(this, "Reminder set", Toast.LENGTH_SHORT).show()
        }
    }

    // Private Function to create the OneTimeWorkRequest
    private fun createWorkRequest(message: String, timeDelayInSeconds: Long) {
        val myWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
                    "title" to "Reminder",
                    "message" to message,
                )
            )
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)
    }
}