import {Component, OnInit} from '@angular/core';
import {HttpConnectionService} from "../http-connection.service";

@Component({
  selector: 'app-my-debtors',
  templateUrl: './my-debtors.component.html'
})
export class MyDebtorsComponent implements OnInit {

  constructor(private http: HttpConnectionService) {
  }

  ngOnInit() {
  }

  cancelDebt(debtId) {

  }

}
