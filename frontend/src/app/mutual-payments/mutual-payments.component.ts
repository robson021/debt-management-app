import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpConnectionService} from "../http-connection.service";

@Component({
  selector: 'app-mutual-payments',
  templateUrl: './mutual-payments.component.html'
})
export class MutualPaymentsComponent implements OnInit {

  newPaymentForm: FormGroup;

  payments = [];

  constructor(private fb: FormBuilder, private http: HttpConnectionService) {
    this.newPaymentForm = fb.group({
      amount: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.loadMutualPayments();
  }

  cancelPayment(paymentId) {
    this.http.performDelete('payments/delete-mutual-payment/' + paymentId + '/')
      .subscribe(data => this.loadMutualPayments())
  }

  submitNewFee(paymentId, newFeeAmount) {
    this.http.performPost('payments/add-fee/' + paymentId + '/' + newFeeAmount + '/', null)
      .subscribe(data => this.loadMutualPayments());
  }

  cancelMyAllFees(paymentId) {
    this.http.performDelete('payments/delete-my-fees/' + paymentId + '/')
      .subscribe(data => this.loadMutualPayments());
  }

  submitNewPayment() {
    this.http.performPost('payments/add-mutual-payment/', this.newPaymentForm.value)
      .subscribe(data => this.loadMutualPayments());

    this.newPaymentForm.reset();
  }

  private loadMutualPayments() {
    this.http.performGet('payments/mutual-payments/')
      .subscribe(data => this.payments = data);
  }

}
