import {Component} from '@angular/core';
import {AngularFire, FirebaseListObservable, AngularFireDatabase} from 'angularfire2';
import {FireEvent} from "./model/fire-event";
import {FireView} from "./model/fire-view";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UUID} from "angular2-uuid";
import * as firebase from 'firebase'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Hollywood';

  events: FireEvent[];
  view: FireView;
  public eventFormGroup: FormGroup;

  constructor(private af: AngularFire, private _fb: FormBuilder) {
    af.database.list('/events').subscribe(events => {
      this.events = events.sort((left, right) => {
        return new Date(right.timeStamp).getTime() - new Date(left.timeStamp).getTime();
      })
    });

    af.database.object('/views').subscribe(view => {
      this.view = view
    });

    this.eventFormGroup = this.createFormGroup()
  }

  save(event: FireEvent, isValid: boolean) {
    if (!isValid) {
      return;
    }

    if (event.slider < 0) {
      event.slider = 0;
    }
    if (event.slider > 100) {
      event.slider = 100;
    }

    this.af.database.list("/events").push(event)
  }

  private createFormGroup() {
    var event = new FireEvent();
    event.clientType = "Angular2";
    event.appId = this.getAppId();

    return this._fb.group({
      name: [event.name, [<any>Validators.required]],
      appId: [event.appId, [<any>Validators.required, <any>Validators.minLength(5)]],
      clientType: [event.clientType, [<any>Validators.required]],
      checked: [event.checked, [<any>Validators.required]],
      radioId: [event.radioId, [<any>Validators.required]],
      slider: [event.slider, [<any>Validators.required]],
      timeStamp: [firebase.database.ServerValue.TIMESTAMP]
    });
  }

  private getAppId() {
    if (localStorage.getItem("APP_ID") == null) {
      localStorage.setItem("APP_ID", UUID.UUID().toString())
    }
    return localStorage.getItem("APP_ID")
  }
}
