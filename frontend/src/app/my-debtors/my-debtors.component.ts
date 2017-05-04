import {Component, OnInit} from "@angular/core";
import {HttpConnectionService} from "../http-connection.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-my-debtors',
  templateUrl: './my-debtors.component.html'
})
export class MyDebtorsComponent implements OnInit {

  debtors = [];

  otherUsers = [];

  debtorsForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpConnectionService) {
    this.debtorsForm = fb.group({
      amount: ['0.0', Validators.required],
      description: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.loadDebtors();
  }

  cancelDebt(debtId) {
    this.http.performDelete('payments/cancel-debt/' + debtId + '/')
      .subscribe(data => {
        this.loadDebtors();
      });
  }

  submitNewAsset() {
  }

  private loadDebtors() {
    this.http.getDebtors()
      .subscribe(data => {
        this.debtors = data;
        console.log(data)
      });
  }

}
