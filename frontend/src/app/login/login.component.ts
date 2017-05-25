import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpConnectionService} from "../http-connection.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpConnectionService) {
    this.loginForm = fb.group({
      email: ['test@t.pl', Validators.required],
      password: ['Passwd.123', Validators.required]
    });
  }

  submitLogin() {
    this.http.logUserIn(this.loginForm.value);
  }

}
