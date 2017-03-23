package nl.endran.hollywood

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import com.f2prateek.rx.preferences.RxSharedPreferences
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.widget.textChanges
import com.kelvinapps.rxfirebase.RxFirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import rx.Subscription
import java.util.*

class MainActivity : AppCompatActivity() {

    data class FireEvent(
            val appId: String,
            val name: String,
            val radioId: Int,
            val checked: Boolean,
            val slider: Int,
            val clientType: String = "Android",
            val timeStamp: Map<String, String> = ServerValue.TIMESTAMP)

    data class FireView(
            val appId: Int? = null,
            val checked: Map<String, Int>? = null,
            val clientType: Map<String, Int>? = null,
            val eventCount: Int? = null,
            val radio: Map<String, Int>? = null,
            val sliderAverage: Double? = null,
            val users: Int? = null)


    var subscriptions = mutableListOf<Subscription>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.setPersistenceEnabled(true);
    }

    override fun onResume() {
        super.onResume()


        val preferences = RxSharedPreferences.create(getSharedPreferences(getPackageName(), Context.MODE_PRIVATE))
        val appId = getAppId(preferences)

        val namePreference = preferences.getString("Name")
        editText.setText(namePreference.get())

        editText.textChanges()
                .map { it.toString() }
                .subscribe(namePreference.asAction())
                .run { subscriptions.add(this) }

        editText.textChanges()
                .subscribe { sendButton.isEnabled = it.isNotBlank() }
                .run { subscriptions.add(this) }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBarText.text = "$progress"
            }
        })

        val reference = FirebaseDatabase.getInstance().reference

        sendButton.clicks()
                .map { FireEvent(appId, editText.text.toString(), getCheckedIndex(), checkBox.isChecked, seekbar.progress) }
                .subscribe { reference.child("events").push().setValue(it) }
                .run { subscriptions.add(this) }

        RxFirebaseDatabase.observeValueEvent(reference.child("events"))
                .map { it.children.count() }
                .subscribe { nrOfLocalEventsText.text = "$it" }

        RxFirebaseDatabase.observeValueEvent(reference.child("views"))
                .map { it.getValue(FireView::class.java) }
                .subscribe {
                    nrOfRemoteEventsText.text = "${it.eventCount}"
                    nrOfRemoteAppsText.text = "${it.appId}"
                    nrOfRemoteUserText.text = "${it.users}"
                    nrOfRemoteRadio1Text.text = "${it.radio?.get("r1")}"
                    nrOfRemoteRadio2Text.text = "${it.radio?.get("r2")}"
                    nrOfRemoteRadio3Text.text = "${it.radio?.get("r3")}"
                    nrOfRemoteCheckedTrueText.text = "${it.checked?.get("true")}"
                    nrOfRemoteCheckedFalseText.text = "${it.checked?.get("false")}"
                    remoteSliderAvgText.text = "${it.sliderAverage}"
                }
    }

    private fun getCheckedIndex(): Int {
        when (radioGroup.checkedRadioButtonId) {
            R.id.radioButton1 -> return 1
            R.id.radioButton2 -> return 2
            R.id.radioButton3 -> return 3
            else -> return -1
        }
    }

    override fun onPause() {
        seekbar.setOnSeekBarChangeListener(null)
        subscriptions.forEach { it.unsubscribe() }
        subscriptions.clear()
        super.onPause()
    }

    private fun getAppId(preferences: RxSharedPreferences): String {
        val appIdPreference = preferences.getString("APP_ID")
        if (!appIdPreference.isSet) {
            appIdPreference.set(UUID.randomUUID().toString())
        }
        return appIdPreference.get()!!
    }
}
