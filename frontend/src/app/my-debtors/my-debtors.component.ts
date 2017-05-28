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
      description: ['', Validators.required],
      selectedUser: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.loadDebtors();
    this.loadOtherUsers();
  }

  cancelDebt(debtId) {
    this.http.performDelete('payments/cancel-debt/' + debtId + '/')
      .subscribe(data => {
        this.loadDebtors();
      });
  }

  submitNewAsset() {
    let u = this.debtorsForm.value.selectedUser.split(' ');
    let newAsset = {
      borrowerName: u[0],
      borrowerSurname: u[1],
      borrowerId: u[2],
      description: this.debtorsForm.value.description,
      amount: this.debtorsForm.value.amount
    };

    this.http.performPost('payments/add-assets-to-user/', newAsset)
      .subscribe(data => {
        this.loadDebtors();
      }, error => {
        console.log(error);
        this.loadDebtors();
      });
  }

  private loadOtherUsers() {
    this.http.performGet('credentials/other-users/')
      .subscribe(data => {
        this.otherUsers = data;
      });
  }

  private loadDebtors() {
    this.http.performGet('payments/my-debtors/')
      .subscribe(data => {
        this.debtors = data;
        console.log(data)
      });
  }

}
