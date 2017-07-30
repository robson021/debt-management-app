import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import {environment} from '../environments/environment';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class HttpConnectionService {

  private headers = new Headers({'Content-Type': 'application/json'});

  private api: string;

  constructor(private http: Http, private router: Router) {
    this.api = environment.production ? '/' : 'http://localhost:8080/';
  }

  logUserIn(credentials) {
    this.http
      .post(this.api + 'auth/login/', JSON.stringify(credentials), {headers: this.headers})
      .map(response => response.json())
      .subscribe(
        data => {
          sessionStorage.setItem('token', 'Bearer ' + data.message);
          this.tryLogUserIn();
        }
      );
  }

  performDelete(uri): Observable<any> {
    console.log('delete: ', uri);
    return this.http
      .delete(this.api + uri, {headers: this.headers})
      .map(response => {
        try {
          response.json();
        } catch (e) {
          response.text();
        }
      });
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
      .catch(this.serverError);
  }

  performPut(uri, body): Observable<any> {
    console.log('put:', uri, 'body:', body);
    return this.http
      .put(this.api + uri, JSON.stringify(body), {headers: this.headers})
      .catch(this.serverError);
  }

  private serverError(err: any) {
    console.log('sever error:', err);
    return Observable.throw('error');
  }

  tryLogUserIn() {
    let token = sessionStorage.getItem('token');
    if (token) {
      this.headers.append('Authorization', token);
      this.router.navigate(['/my-debts']);
    }
  }
}
