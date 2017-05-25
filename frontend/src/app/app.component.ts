import {Component, OnInit} from '@angular/core';
import {HttpConnectionService} from "./http-connection.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {

  loggedIn: boolean;

  constructor(private http: HttpConnectionService) {
    this.loggedIn = false;
  }

  ngOnInit(): void {
    let token = sessionStorage.getItem('token');
    if (token) {
      this.loggedIn = true;
      this.http.setJwtHeader();
    }
  }
}
