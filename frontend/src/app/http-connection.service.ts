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
    let headers: Headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa('client:secret'));
    headers.append('Content-Type', 'application/x-www-form-urlencoded');

    let url = `oauth/token?password=${credentials.password}&username=${credentials.email}&grant_type=password&scope=openid&client_secret=secret&client_id=client`;

    this.http
      .post(this.api + url, null, {headers: headers})
      .map(response => response.json())
      .subscribe(
        data => {
          sessionStorage.setItem('token', 'Bearer ' + data.access_token);
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

  tryLogUserIn() {
    let token = sessionStorage.getItem('token');
    if (token) {
      this.headers.append('Authorization', token);
      this.checkAdminPrivileges();
      this.router.navigate(['/my-debts']);
    }
  }

  checkAdminPrivileges() {
    this.http
      .get(this.api + 'auth/am-i-admin', {headers: this.headers})
      .subscribe(
        (data) => {
          document.getElementById('admin-button')
            .style
            .display = 'list-item';
        },
        (err) => {
        }
      );
  }

  private serverError(err: any) {
    console.log('sever error:', err);
    return Observable.throw('error');
  }
}
