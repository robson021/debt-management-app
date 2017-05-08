import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {routes} from "./app.router";
import {AppComponent} from "./app.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {MyDebtsComponent} from "./my-debts/my-debts.component";
import {MyDebtorsComponent} from "./my-debtors/my-debtors.component";
import {MutualPaymentsComponent} from "./mutual-payments/mutual-payments.component";
import {HttpConnectionService} from "./http-connection.service";
import {UserDetailsComponent} from "./user-details/user-details.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    MyDebtsComponent,
    MyDebtorsComponent,
    MutualPaymentsComponent,
    UserDetailsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    routes
  ],
  providers: [HttpConnectionService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
