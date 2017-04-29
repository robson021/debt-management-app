import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {routes} from './app.router';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {MyDebtsComponent} from './my-debts/my-debts.component';
import {MyDebtorsComponent} from './my-debtors/my-debtors.component';
import {MutualPaymentsComponent} from './mutual-payments/mutual-payments.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    MyDebtsComponent,
    MyDebtorsComponent,
    MutualPaymentsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routes
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
