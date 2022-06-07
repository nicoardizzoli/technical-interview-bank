import {Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Setting} from "../../model/setting";
import {MovementService} from "../../services/movement.service";
import {MovementReportDto} from "../../model/movement-report-dto";
import {ActivatedRoute} from "@angular/router";
import {getISOWeek} from 'date-fns';

@Component({
  selector: 'app-movement-report',
  templateUrl: './movement-report.component.html',
  styleUrls: ['./movement-report.component.scss']
})
export class MovementReportComponent implements OnInit {
startDate!: Date;
endDate!: Date;

  constructor(private location: Location, private formBuilder: FormBuilder, private movementService: MovementService, private activatedRoute: ActivatedRoute) {
  }

  settingForm?: FormGroup;
  listOfData: readonly MovementReportDto[] = [];
  displayData: readonly MovementReportDto[] = [];
  fixedColumn = false;
  scrollX: string | null = null;
  scrollY: string | null = null;
  searchValueName = '';
  settingValue!: Setting;
  searchValueIdentification = '';
  visibleIdentification = false;
  visibleName = false;
  date = null;
  customerIdentification: string = "";



  ngOnInit(): void {

    this.customerIdentification = this.activatedRoute.snapshot.params['customerIdentification'];

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


  currentPageDataChange($event: readonly MovementReportDto[]): void {
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
    // this.customerService.getAllCustomers().subscribe(data => {
    //   this.displayData = data;
    //   this.listOfData = data;
    // })
  }

  // resetName(): void {
  //   this.searchValueName = '';
  //   this.searchName()
  // }
  // resetIdentification(): void {
  //   this.searchValueIdentification = '';
  //   this.searchIdentification()
  // }
  //
  // searchName(): void {
  //   this.visibleName= false;
  //   this.displayData = this.listOfData.filter((item: CustomerDto) => item.name.indexOf(this.searchValueName) !== -1);
  // }
  //
  // searchIdentification(): void {
  //   this.visibleIdentification = false;
  //   this.displayData = this.listOfData.filter((item: CustomerDto) => item.identification.indexOf(this.searchValueIdentification) !== -1);
  // }

  back() {
    this.location.back();
  }


  onChange(result: Date[]): void {
    this.startDate = result[0];
    this.endDate = result[1];
    console.log('onChange: ', result);
  }


  searchMovements() {
    this.movementService.completeReport(this.customerIdentification, this.startDate, this.endDate).subscribe(value => {
      console.log(value)
      this.displayData = value;
      this.listOfData = value;
    });
  }
}
