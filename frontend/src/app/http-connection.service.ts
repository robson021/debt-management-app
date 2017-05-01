import {Injectable} from '@angular/core';
import {Http, Headers} from "@angular/http";
import {environment} from "../environments/environment";
import 'rxjs/add/operator/toPromise';

@Injectable()
export class HttpConnectionService {

  private headers = new Headers({'Content-Type': 'application/json'});

  private api: string;

  constructor(private http: Http) {
    this.api = environment.production ? '/' : 'http://localhost:8080/';
  }

  logUserIn(credentials) {
    let url = this.api + 'auth/login/';
    this.http.post(url, JSON.stringify(credentials), {headers: this.headers})
      .subscribe(
        response => {
          // TODO
          console.log(response);
        },
        error => {
          console.log(error.text());
        });
  }

}
