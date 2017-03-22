import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {AngularFireModule} from 'angularfire2';

import {AppComponent} from './app.component';

export const firebaseConfig = {
  apiKey: "AIzaSyCuS0Jo1sFlrgPIuLG8sOkIYYvx6e6u_Qk",
  authDomain: "hollywoodprinciple-fdebd.firebaseapp.com",
  databaseURL: "https://hollywoodprinciple-fdebd.firebaseio.com",
  storageBucket: "hollywoodprinciple-fdebd.appspot.com",
  messagingSenderId: "442806894257"
};

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AngularFireModule.initializeApp(firebaseConfig)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
