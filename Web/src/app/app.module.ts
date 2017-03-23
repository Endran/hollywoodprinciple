import {BrowserModule} from '@angular/platform-browser';
import {NgModule, enableProdMode} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {AngularFireModule, AngularFire} from 'angularfire2';

import {AppComponent} from './app.component';
import {environment} from "../environments/environment";

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
  constructor() {
    if (environment.production){
      console.log("PRODUCTION");
    }else {
      console.log("DEBUG");
    }
  }
}
