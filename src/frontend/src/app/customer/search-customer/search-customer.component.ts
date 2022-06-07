import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {CustomerDto} from "../../model/customer-dto";
import {CustomerService} from "../../services/customer.service";
import {Setting} from "../../model/setting";


@Component({
  selector: 'app-search-customer',
  templateUrl: './search-customer.component.html',
  styleUrls: ['./search-customer.component.scss']
})
export class SearchCustomerComponent implements OnInit {

  settingForm?: FormGroup;
  listOfData: readonly CustomerDto[] = [];
  displayData: readonly CustomerDto[] = [];
  fixedColumn = false;
  scrollX: string | null = null;
  scrollY: string | null = null;
  settingValue!: Setting;
  searchValueName = '';
  searchValueIdentification = '';
  visibleIdentification = false;
  visibleName = false;

  constructor(private formBuilder: FormBuilder, private customerService: CustomerService) {}

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


  currentPageDataChange($event: readonly CustomerDto[]): void {
    this.displayData = $event;
    // this.refreshStatus();
  }

  // refreshStatus(): void {
  //   const validData = this.displayData.filter(value => !value.disabled);
  //   const allChecked = validData.length > 0 && validData.every(value => value.checked);
  //   const allUnChecked = validData.every(value => !value.checked);
  //   this.allChecked = allChecked;
  //   this.indeterminate = !allChecked && !allUnChecked;
  // }


  generateData() {
    this.customerService.getAllCustomers().subscribe(data => {
      this.displayData = data;
      this.listOfData = data;
    })
  }

  resetName(): void {
    this.searchValueName = '';
    this.searchName()
  }
  resetIdentification(): void {
    this.searchValueIdentification = '';
    this.searchIdentification()
  }

  searchName(): void {
    this.visibleName= false;
    this.displayData = this.listOfData.filter((item: CustomerDto) => item.name.indexOf(this.searchValueName) !== -1);
  }

  searchIdentification(): void {
    this.visibleIdentification = false;
    this.displayData = this.listOfData.filter((item: CustomerDto) => item.identification.indexOf(this.searchValueIdentification) !== -1);
  }



}
