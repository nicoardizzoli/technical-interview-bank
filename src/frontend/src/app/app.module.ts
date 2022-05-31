import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { IconsProviderModule } from './icons-provider.module';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { CreateCustomerComponent } from './create-customer/create-customer.component';
import {NzFormModule} from "ng-zorro-antd/form";
import {NzInputModule} from "ng-zorro-antd/input";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzAutocompleteModule} from "ng-zorro-antd/auto-complete";
import {NzSelectModule} from "ng-zorro-antd/select";
import {NzCheckboxModule} from "ng-zorro-antd/checkbox";
import {NzNotificationModule} from "ng-zorro-antd/notification";
import {HttpErrorsInterceptor} from "./interceptors/http-errors.interceptor";

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    CreateCustomerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    IconsProviderModule,
    NzLayoutModule,
    NzMenuModule,
    NzFormModule,
    ReactiveFormsModule,
    NzInputModule,
    NzButtonModule,
    NzAutocompleteModule,
    NzSelectModule,
    NzCheckboxModule,
    NzNotificationModule
  ],
  providers: [
    { provide: NZ_I18N, useValue: en_US },
    // { provide: HTTP_INTERCEPTORS, useClass: HttpErrorsInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
