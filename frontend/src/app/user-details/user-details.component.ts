import {Component, OnInit} from '@angular/core';
import {HttpConnectionService} from '../http-connection.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html'
})
export class UserDetailsComponent implements OnInit {

  users = [];

  constructor(private http: HttpConnectionService) {
  }

  ngOnInit() {
    this.http.performGet('credentials/other-users/')
      .subscribe(data => {
        this.users = data;
        this.getDebtsDiff();
      });
  }

  private getDebtsDiff() {
    this.users.forEach((user) => {
      this.http.performGet('payments/money-balance-with-other-user/' + user.id + '/')
        .subscribe(balance => {
          user.balance = balance;
        });
    });
  };
}
