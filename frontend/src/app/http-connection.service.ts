import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";
import {environment} from "../environments/environment";
import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";

@Injectable()
export class HttpConnectionService {

  private headers = new Headers({'Content-Type': 'application/json'});

  private api: string;

  private loggedIn: boolean;

  constructor(private http: Http, private router: Router) {
    this.api = environment.production ? '/' : 'http://localhost:8080/';
    this.loggedIn = false;
  }

  logUserIn(credentials) {
    if (this.loggedIn) {
      this.router.navigate(['/my-debts']);
    }

    this.http
      .post(this.api + 'auth/login/', JSON.stringify(credentials), {headers: this.headers})
      .map(response => response.json())
      .subscribe(
        data => {
          console.log('JWT:', data.message);
          this.headers.append('Authorization', 'Bearer ' + data.message);
          this.router.navigate(['/my-debts']);
          this.loggedIn = true;
        }
      );

  }

  getDebts(): Observable<any> {
    return this.http
      .post(this.api + 'payments/my-debts/', null, {headers: this.headers})
      .map(response => response.json());
  }

}
