import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {en_US, NZ_I18N} from 'ng-zorro-antd/i18n';
import {registerLocaleData} from '@angular/common';
import en from '@angular/common/locales/en';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {IconsProviderModule} from './icons-provider.module';
import {NzLayoutModule} from 'ng-zorro-antd/layout';
import {NzMenuModule} from 'ng-zorro-antd/menu';
import {CreateCustomerComponent} from './customer/create-customer/create-customer.component';
import {NzFormModule} from "ng-zorro-antd/form";
import {NzInputModule} from "ng-zorro-antd/input";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzAutocompleteModule} from "ng-zorro-antd/auto-complete";
import {NzSelectModule} from "ng-zorro-antd/select";
import {NzCheckboxModule} from "ng-zorro-antd/checkbox";
import {NzNotificationModule} from "ng-zorro-antd/notification";
import {NzMessageServiceModule} from "ng-zorro-antd/message";
import {SearchCustomerComponent} from './customer/search-customer/search-customer.component';
import {NzTableModule} from "ng-zorro-antd/table";
import {NzDropDownModule} from "ng-zorro-antd/dropdown";
import {NzDividerModule} from "ng-zorro-antd/divider";
import {NzRadioModule} from "ng-zorro-antd/radio";
import {NzSwitchModule} from "ng-zorro-antd/switch";
import {CreateAccountComponent} from './account/create-account/create-account.component';
import {NzCardModule} from "ng-zorro-antd/card";
import {SearchAccountComponent} from './account/search-account/search-account.component';
import { YesNoPipe } from './pipes/yes-no.pipe';

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    CreateCustomerComponent,
    SearchCustomerComponent,
    CreateAccountComponent,
    SearchAccountComponent,
    YesNoPipe
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
    NzNotificationModule,
    NzMessageServiceModule,
    NzTableModule,
    NzDropDownModule,
    NzDividerModule,
    NzRadioModule,
    NzSwitchModule,
    NzCardModule,
  ],
  providers: [
    { provide: NZ_I18N, useValue: en_US },
    // { provide: HTTP_INTERCEPTORS, useClass: HttpErrorsInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
