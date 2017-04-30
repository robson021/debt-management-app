import {Injectable, OnInit} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../environments/environment";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class HttpService implements OnInit {

  private static _externalApi: string;

  constructor(private http: Http) {
  }

  ngOnInit(): void {
    HttpService._externalApi = environment.production ? '/' : 'localhost:8080/';
  }

  doGet(uri: string): Observable<any[]> {
    let promise = this.http.get(this.getUrl(uri));
    return HttpService.handleResponse(promise);
  }

  doPost(uri: string, body) {
    let promise = this.http.post(this.getUrl(uri), body);
    return HttpService.handleResponse(promise);
  }

  private getUrl(uri: string): string {
    return HttpService._externalApi + uri;
  }

  private static handleResponse(promise) {
    return promise
      .map(HttpService.extractData)
      .catch(HttpService.handleError);
  }

  private static extractData(res: Response) {
    let body = res.json();
    return body.data || {};
  }

  private static handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
}
