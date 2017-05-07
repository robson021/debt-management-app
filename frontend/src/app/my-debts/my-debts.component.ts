import {Component, OnInit} from "@angular/core";
import {HttpConnectionService} from "../http-connection.service";

@Component({
  selector: 'app-my-debts',
  templateUrl: './my-debts.component.html'
})
export class MyDebtsComponent implements OnInit {

  debts = [];

  constructor(private http: HttpConnectionService) {
  }

  ngOnInit() {
    this.http.performGet('payments/my-debts/')
      .subscribe( data => {
        this.debts = data;
        console.log(this.debts);
      });
  }

}
