import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JsonConvert } from 'json2typescript';
import { ChatUserDTO } from 'src/app/models/chat-user.dto';
import { AuthService } from 'src/app/services/auth.service';
import { SpinnerService } from 'src/app/services/spinner-service';
import { UserService } from 'src/app/services/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public form: FormGroup;
  public loginInvalid = true;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private toastr: ToastrService,
    private userService: UserService,
    private authService: AuthService,
    private spinnerService: SpinnerService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      username: ['', Validators.required]
    });
  }

  async onSubmit() {
    if (this.form.valid) {
      try {
        const username = this.form.get('username').value;

        // this.spinnerService.startSpinner();
        this.authService.register({ username }).subscribe(
          data => {
            console.log('Registration success: ' + JSON.stringify(data));

            const jsonConvert: JsonConvert = new JsonConvert();
            const chatUser: ChatUserDTO = jsonConvert.deserializeObject(data, ChatUserDTO);

            this.loginInvalid = false;
            // this.spinnerService.stopSpinner();
            this.authService.setUserRegistered(true);
            this.userService.setCurrentUser(chatUser);
            this.router.navigateByUrl('/chat');
          },
          err => {
            console.log('Registration error: ' + JSON.stringify(err));

            this.loginInvalid = true;
            // this.spinnerService.stopSpinner();
            this.toastr.error('Registration failed. Please try again later.');
          }
        );
      } catch (err) {
        this.loginInvalid = true;
        // this.spinnerService.stopSpinner();
        this.toastr.error('Registration failed. Please try again later.');
      }
    } else {
      this.loginInvalid = true;
    }
  }
}
