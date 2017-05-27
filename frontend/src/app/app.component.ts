import {Component, OnInit} from '@angular/core';
import {HttpConnectionService} from './http-connection.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {

  constructor(private http: HttpConnectionService) {
  }

  ngOnInit(): void {
    this.http.tryLogUserIn();
  }
}
