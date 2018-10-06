import {Component, OnInit} from '@angular/core';
import {HttpConnectionService} from '../http-connection.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-admin-area',
  templateUrl: './admin-area.component.html',
})
export class AdminAreaComponent implements OnInit {

  changePasswordForm: FormGroup;

  changeEmailForm: FormGroup;

  users = [];

  selectedUser = null;

  constructor(private fb: FormBuilder, private http: HttpConnectionService) {
    this.changePasswordForm = fb.group({
      password: ['', Validators.required],
      repassword: ['', Validators.required]
    });

    this.changeEmailForm = fb.group({
      email: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.http.performGet('admin/all-users/')
      .subscribe(data => {
        this.users = data;
      });
  }

  selectUser(user) {
    this.selectedUser = user;
  }

  submitNewPassword() {
    this.selectedUser.password = this.changePasswordForm.value.password;
    this.http.performPut('admin/change-password/', this.selectedUser)
      .subscribe(data => {
        this.selectedUser = null;
        this.changePasswordForm.reset();
      });
  }

  submitNewEmail() {
    this.selectedUser.email = this.changeEmailForm.value.email;
    this.http.performPut('admin/change-email', this.selectedUser)
      .subscribe(data => {
        this.selectedUser = null;
        this.changeEmailForm.reset();
      });
  }

}
