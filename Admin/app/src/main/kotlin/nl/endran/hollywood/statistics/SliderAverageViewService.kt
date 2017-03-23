package nl.endran.hollywood.statistics

import com.google.firebase.database.DatabaseReference
import nl.endran.hollywood.models.FireEvent
import nl.endran.rxfirebaseadmin.RxFirebaseChildEvent
import nl.endran.rxfirebaseadminkt.observeChildEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SliderAverageViewService @Autowired constructor(
        databaseReference: DatabaseReference) {

    var avg = 0.0;
    var count = 0L;

    init {
        databaseReference.child("events").observeChildEvent(FireEvent::class.java)
                .filter { it.eventType == RxFirebaseChildEvent.EventType.ADDED }
                .map { it.value }
                .subscribe {
                    avg = (avg * count + it.slider!!.toDouble()) / (1 + count)
                    count++
                    databaseReference.child("views").child("sliderAverage").setValue(avg)
                }
    }
}
