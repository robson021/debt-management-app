import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {

  loginForm: FormGroup;

  constructor(public fb: FormBuilder) {
    this.loginForm = fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
}
