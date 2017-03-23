package nl.endran.hollywood.statistics

import com.google.firebase.database.DatabaseReference
import nl.endran.rxfirebaseadminkt.observeValueEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventCountViewService @Autowired constructor(
        databaseReference: DatabaseReference) {

    init {
        databaseReference.child("events").observeValueEvent()
                .map { it.children.count() }
                .subscribe {
                    databaseReference.child("views").child("eventCount").setValue(it)
                }
    }
}
