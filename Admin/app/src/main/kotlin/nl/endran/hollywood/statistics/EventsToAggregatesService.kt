package nl.endran.hollywood.statistics

import com.google.firebase.database.DatabaseReference
import nl.endran.hollywood.models.FireEvent
import nl.endran.rxfirebaseadmin.RxFirebaseChildEvent
import nl.endran.rxfirebaseadminkt.observeChildEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventsToAggregatesService @Autowired constructor(
        databaseReference: DatabaseReference) {

    init {
        databaseReference.child("events").observeChildEvent(FireEvent::class.java)
                .filter { it.eventType == RxFirebaseChildEvent.EventType.ADDED }
                .subscribe {
                    databaseReference.child("aggregates").child("appId").child(it.value.appId).setValue(true)
                    databaseReference.child("aggregates").child("users").child(it.value.name).setValue(true)

                    databaseReference.child("aggregates").child("clientType").child(it.value.clientType).child(it.value.appId).setValue(true)

                    databaseReference.child("aggregates").child("checked").child("${it.value.checked}").child(it.key).setValue(it.value.readableTimeStamp())
                    databaseReference.child("aggregates").child("radio").child("r${it.value.radioId}").child(it.key).setValue(it.value.readableTimeStamp())
                }
    }
}
