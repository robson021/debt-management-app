import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {RegisterComponent} from './register/register.component';
import {MyDebtsComponent} from './my-debts/my-debts.component';
import {MyDebtorsComponent} from './my-debtors/my-debtors.component';
import {MutualPaymentsComponent} from './mutual-payments/mutual-payments.component';
import {UserDetailsComponent} from './user-details/user-details.component';
import {AdminAreaComponent} from './admin-area/admin-area.component';
import {NotesComponent} from './notes/notes.component';

export const router: Routes = [
  // {path: '', redirectTo: 'login', pathMatch: 'full'},
  // {path: 'login', component: LoginComponent},
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {path: 'register', component: RegisterComponent},
  {path: 'my-debts', component: MyDebtsComponent},
  {path: 'my-debtors', component: MyDebtorsComponent},
  {path: 'mutual-payments', component: MutualPaymentsComponent},
  {path: 'admin-area', component: AdminAreaComponent},
  {path: 'notes', component: NotesComponent},
  {path: 'user-details', component: UserDetailsComponent}
];

export const routes: ModuleWithProviders = RouterModule.forRoot(router);
