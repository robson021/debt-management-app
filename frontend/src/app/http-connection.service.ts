import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";
import {environment} from "../environments/environment";
import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";

@Injectable()
export class HttpConnectionService {

  private headers = new Headers({'Content-Type': 'application/json'});

  private api: string;

  constructor(private http: Http) {
    this.api = environment.production ? '/' : 'http://51.254.115.19:8099/';
  }

  logUserIn(credentials) {
    this.http
      .post(this.api + 'auth/login/', JSON.stringify(credentials), {headers: this.headers})
      .map(response => response.json())
      .subscribe(
        data => {
          console.log("JWT:", data.message);
          this.headers.append("Authorization", "Bearer " + data.message)
        }
      );

  }


  private extractData(res) {
    let body = res.json();
    return body.data || {};
  }

  private handleError(error: any) {
    console.error(error);
  }

}
