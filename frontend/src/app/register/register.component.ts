import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpConnectionService} from "../http-connection.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {

  registerForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private http: HttpConnectionService) {
    this.registerForm = formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      repassword: ['', Validators.required]
    });
  }

}
