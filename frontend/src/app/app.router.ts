import {RouterModule, Routes} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {MyDebtsComponent} from "./my-debts/my-debts.component";
import {MyDebtorsComponent} from "./my-debtors/my-debtors.component";
import {MutualPaymentsComponent} from "./mutual-payments/mutual-payments.component";
import {UserDetailsComponent} from "./user-details/user-details.component";

export const router: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'my-debts', component: MyDebtsComponent},
  {path: 'my-debtors', component: MyDebtorsComponent},
  {path: 'mutual-payments', component: MutualPaymentsComponent},
  {path: 'user-details', component: UserDetailsComponent}
];

export const routes: ModuleWithProviders = RouterModule.forRoot(router);
