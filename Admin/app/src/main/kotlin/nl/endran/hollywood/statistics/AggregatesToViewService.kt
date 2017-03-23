package nl.endran.hollywood.statistics

import com.google.firebase.database.DatabaseReference
import nl.endran.rxfirebaseadminkt.observeChildEvent
import nl.endran.rxfirebaseadminkt.observeValueEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AggregatesToViewService @Autowired constructor(
        databaseReference: DatabaseReference) {

    init {
        countAggregate(databaseReference, "appId")
        countAggregate(databaseReference, "users")

        countChildAggregate(databaseReference, "clientType")
        countChildAggregate(databaseReference, "checked")
        countChildAggregate(databaseReference, "radio")
    }

    private fun countAggregate(databaseReference: DatabaseReference, aggregateKey: String) {
        databaseReference.child("aggregates").child(aggregateKey).observeValueEvent()
                .map { it.children.count() }
                .subscribe {
                    databaseReference.child("views").child(aggregateKey).setValue(it)
                }
    }

    private fun countChildAggregate(databaseReference: DatabaseReference, aggregateKey: String) {
        databaseReference.child("aggregates").child(aggregateKey).observeChildEvent()
                .subscribe {
                    countAggregate(databaseReference, "$aggregateKey/${it.key}")
                }
    }
}
