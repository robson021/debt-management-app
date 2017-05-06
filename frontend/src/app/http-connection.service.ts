import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";
import {environment} from "../environments/environment";
import "rxjs/add/operator/toPromise";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class HttpConnectionService {

  private headers = new Headers({'Content-Type': 'application/json'});

  private api: string;

  private loggedIn: boolean;

  constructor(private http: Http, private router: Router) {
    this.api = environment.production ? '/' : 'http://51.254.115.19:8099/';
    this.loggedIn = false;
  }

  logUserIn(credentials) {
    console.log('logged in?:', this.loggedIn);
    if (this.loggedIn) {
      this.router.navigate(['/my-debts']);
      return;
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


  performDelete(uri): Observable<any> {
    console.log('delete: ', uri);
    return this.http
      .delete(this.api + uri, {headers: this.headers})
      .map(response => response.json());
  }

  performGet(uri): Observable<any> {
    console.log('get:', uri);
    return this.http
      .get(this.api + uri, {headers: this.headers})
      .map(response => response.json());
  }

  performPost(uri, body): Observable<any> {
    console.log('post:', uri, 'body:', body);
    return this.http
      .post(this.api + uri, JSON.stringify(body), {headers: this.headers})
      //.map(response => response.json())
      .catch(this.serverError);
  }

  private serverError(err: any) {
    console.log('sever error:', err);
    return Observable.throw('error');
  }
}
