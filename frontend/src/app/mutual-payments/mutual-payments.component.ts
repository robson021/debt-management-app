import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-mutual-payments',
  templateUrl: './mutual-payments.component.html'
})
export class MutualPaymentsComponent implements OnInit {

  private payments = [];

  constructor() {
  }

  ngOnInit() {
  }

  cancelPayment(paymentId) {
  }

  submitNewFee(paymentId) {
  }

  cancelMyAllFees(paymentId) {
  }

}
