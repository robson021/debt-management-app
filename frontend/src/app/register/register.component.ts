import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {

  registerForm: FormGroup;

  constructor(public formBuilder: FormBuilder) {
    this.registerForm = formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      repassword: ['', Validators.required]
    });
  }

}
