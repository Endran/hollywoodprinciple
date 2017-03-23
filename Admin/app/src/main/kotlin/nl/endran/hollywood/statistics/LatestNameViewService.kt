package nl.endran.hollywood.statistics

import com.google.firebase.database.DatabaseReference
import nl.endran.hollywood.models.FireEvent
import nl.endran.rxfirebaseadmin.RxFirebaseChildEvent
import nl.endran.rxfirebaseadminkt.observeChildEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LatestNameViewService @Autowired constructor(
        databaseReference: DatabaseReference) {

    init {
        databaseReference.child("events").observeChildEvent(FireEvent::class.java)
                .filter { it.eventType == RxFirebaseChildEvent.EventType.ADDED }
                .subscribe {
                    databaseReference.child("views").child("latest").setValue(it.value)
                }
    }
}
