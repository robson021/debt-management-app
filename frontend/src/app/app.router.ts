import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from "./register/register.component";
import {MyDebtsComponent} from "./my-debts/my-debts.component";
import {MyDebtorsComponent} from "./my-debtors/my-debtors.component";
import {MutualPaymentsComponent} from "./mutual-payments/mutual-payments.component";

export const router: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'my-debts', component: MyDebtsComponent},
  {path: 'my-debtors', component: MyDebtorsComponent},
  {path: 'mutual-payments', component: MutualPaymentsComponent}
];

export const routes: ModuleWithProviders = RouterModule.forRoot(router);
