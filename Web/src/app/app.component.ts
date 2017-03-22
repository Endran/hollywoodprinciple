import {Component} from '@angular/core';
import {AngularFire, FirebaseListObservable} from 'angularfire2';
import {FireEvent} from "./model/fire-event";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app works!';

  events: FireEvent[];

  constructor(af: AngularFire) {
    af.database.list('/events').subscribe(events => {
      this.events = events
    });
  }
}
