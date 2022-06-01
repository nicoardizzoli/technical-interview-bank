import { Component, OnInit } from '@angular/core';
import {NzTableLayout, NzTablePaginationPosition, NzTablePaginationType, NzTableSize} from "ng-zorro-antd/table";
import {FormBuilder, FormGroup} from "@angular/forms";
import {CustomerDto} from "../../model/customer-dto";
import {CustomerService} from "../../services/customer.service";
import {AccountDto} from "../../model/account-dto";
import {AccountService} from "../../services/account.service";



interface Setting {
  bordered: boolean;
  loading: boolean;
  pagination: boolean;
  sizeChanger: boolean;
  title: boolean;
  header: boolean;
  footer: boolean;
  expandable: boolean;
  checkbox: boolean;
  fixHeader: boolean;
  noResult: boolean;
  ellipsis: boolean;
  simple: boolean;
  size: NzTableSize;
  tableScroll: string;
  tableLayout: NzTableLayout;
  position: NzTablePaginationPosition;
  paginationType: NzTablePaginationType;
}

@Component({
  selector: 'app-search-account',
  templateUrl: './search-account.component.html',
  styleUrls: ['./search-account.component.scss']
})
export class SearchAccountComponent implements OnInit {

  settingForm?: FormGroup;
  listOfData: readonly AccountDto[] = [];
  displayData: readonly AccountDto[] = [];
  fixedColumn = false;
  scrollX: string | null = null;
  scrollY: string | null = null;
  settingValue!: Setting;
  searchValueAccountNumber?: number;
  searchValueAccountType = '';
  searchValueAccountCustomerIdentification: string = '';
  visibleAccountType = false;
  visibleAccountNumber = false;
  visibleAccountCustomer: boolean = false;

  constructor(private formBuilder: FormBuilder, private accountService: AccountService) {}

  ngOnInit(): void {
    this.settingForm = this.formBuilder.group({
      bordered: true,
      loading: false,
      pagination: true,
      sizeChanger: false,
      title: true,
      header: true,
      footer: true,
      expandable: false,
      checkbox: false,
      fixHeader: false,
      noResult: false,
      ellipsis: false,
      simple: false,
      size: 'small',
      paginationType: 'default',
      tableScroll: 'unset',
      tableLayout: 'fixed',
      position: 'bottom'
    });
    this.settingValue = this.settingForm.value;
    this.settingForm.valueChanges.subscribe(value => (this.settingValue = value));
    this.settingForm.get('tableScroll')!.valueChanges.subscribe(scroll => {
      this.fixedColumn = scroll === 'fixed';
      this.scrollX = scroll === 'scroll' || scroll === 'fixed' ? '100vw' : null;
    });
    this.settingForm.get('fixHeader')!.valueChanges.subscribe(fixed => {
      this.scrollY = fixed ? '240px' : null;
    });
    this.settingForm.get('noResult')!.valueChanges.subscribe(empty => {
      if (empty) {
        this.listOfData = [];
      } else {
        this.generateData();
      }
    });
    this.generateData();
  }


  currentPageDataChange($event: readonly AccountDto[]): void {
    this.displayData = $event;
  }



  generateData() {
    this.accountService.getAllAccounts().subscribe(data => {
      this.displayData = data;
      this.listOfData = data;
    })
  }

  resetAccountNumber(): void {
    this.searchValueAccountNumber = undefined;
    this.displayData = this.listOfData;
  }
  resetAccountType(): void {
    this.searchValueAccountType = '';
    this.searchAccountType()
  }
  resetAccountCustomer(): void {
    this.searchValueAccountCustomerIdentification = '';
    this.searchAccountCustomer();
  }

  searchAccountNumber(): void {
    this.visibleAccountNumber= false;
    this.displayData = this.listOfData.filter((item: AccountDto) => item.accountNumber == this.searchValueAccountNumber );
  }

  searchAccountType(): void {
    this.visibleAccountType = false;
    this.displayData = this.listOfData.filter((item: AccountDto) => item.accountType.indexOf(this.searchValueAccountType) !== -1);
  }

  searchAccountCustomer(): void {
    this.visibleAccountCustomer = false;
    this.displayData = this.listOfData.filter((item: AccountDto) => item.customerIdentification.indexOf(this.searchValueAccountCustomerIdentification) !== -1);
  }



}
