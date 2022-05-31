import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CreateCustomerComponent} from "./create-customer/create-customer.component";
import {SearchCustomerComponent} from "./search-customer/search-customer.component";

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/welcome' },
  { path: 'welcome', loadChildren: () => import('./pages/welcome/welcome.module').then(m => m.WelcomeModule) },
  { path: 'create-customer', component: CreateCustomerComponent},
  { path: 'search-customer', component: SearchCustomerComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
