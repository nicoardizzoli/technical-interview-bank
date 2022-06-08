import {Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Setting} from "../../model/setting";
import {MovementService} from "../../services/movement.service";
import {MovementReportDto} from "../../model/movement-report-dto";
import {ActivatedRoute} from "@angular/router";
import {NzMessageService} from "ng-zorro-antd/message";

@Component({
  selector: 'app-movement-report',
  templateUrl: './movement-report.component.html',
  styleUrls: ['./movement-report.component.scss']
})
export class MovementReportComponent implements OnInit {
  startDate!: Date;
  endDate!: Date;

  constructor(private location: Location, private formBuilder: FormBuilder, private movementService: MovementService, private activatedRoute: ActivatedRoute, private message: NzMessageService) {
  }

  settingForm?: FormGroup;
  listOfData: readonly MovementReportDto[] = [];
  displayData: readonly MovementReportDto[] = [];
  fixedColumn = false;
  scrollX: string | null = null;
  scrollY: string | null = null;
  settingValue!: Setting;
  showInputCustomerIdentification = false;
  date = null;
  customerIdentification: string = "";


  ngOnInit(): void {

    this.customerIdentification = this.activatedRoute.snapshot.params['customerIdentification'];

    if (this.customerIdentification == undefined) {
      this.showInputCustomerIdentification = true;
    }

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
      }
    });
  }


  currentPageDataChange($event: readonly MovementReportDto[]): void {
    this.displayData = $event;
  }




  back() {
    this.location.back();
  }


  onChange(result: Date[]): void {
    this.startDate = result[0];
    this.endDate = result[1];
    console.log('onChange: ', result);
  }


  searchMovements() {

    if (this.customerIdentification == undefined) {
      this.message.create("info","Customer identification required");
    }

    if (this.startDate == undefined) {
      this.message.create("info","Start date required");
    }

    if (this.endDate == undefined) {
      this.message.create("info","End date required");
    }

    if (this.customerIdentification != undefined && this.startDate != undefined && this.endDate != undefined) {
      this.movementService.completeReport(this.customerIdentification, this.startDate, this.endDate).subscribe(value => {
        console.log(value)
        this.displayData = value;
        this.listOfData = value;
      });
    }
  }
}
