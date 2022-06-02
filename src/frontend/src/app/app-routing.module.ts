import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CreateCustomerComponent} from "./customer/create-customer/create-customer.component";
import {SearchCustomerComponent} from "./customer/search-customer/search-customer.component";
import {CreateAccountComponent} from "./account/create-account/create-account.component";
import {SearchAccountComponent} from "./account/search-account/search-account.component";
import {CreateMovementComponent} from "./movement/create-movement/create-movement.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: '/welcome'},
  {path: 'welcome', loadChildren: () => import('./pages/welcome/welcome.module').then(m => m.WelcomeModule)},
  {path: 'create-customer', component: CreateCustomerComponent},
  {path: 'search-customer', component: SearchCustomerComponent},
  {path: 'create-account', component: CreateAccountComponent},
  {path: 'search-account', component: SearchAccountComponent},
  {path: 'create-movement', component: CreateMovementComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
