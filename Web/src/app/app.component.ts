import {Component} from '@angular/core';
import {AngularFire, FirebaseListObservable} from 'angularfire2';
import {FireEvent} from "./model/fire-event";
import {FireView} from "./model/fire-view";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Hollywood';

  events: FireEvent[];
  view: FireView;

  constructor(af: AngularFire) {
    af.database.list('/events').subscribe(events => {
      this.events = events
    });

    af.database.object('/views').subscribe(view => {
      this.view = view
    });
  }
}
