import { Component, DestroyRef, OnInit } from '@angular/core';
import { AppCommonModule } from "../../app-common.module";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "../../service/auth.service";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { Router } from "@angular/router";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    AppCommonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private destroyRef: DestroyRef
  ) {
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (!this.loginForm.valid) {
      return;
    }

    this.authService.signIn(this.loginForm.value)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(
        {
          next: () => {
            this.authService.checkAuthenticationAndFetchProfile()
              .pipe(takeUntilDestroyed(this.destroyRef))
              .subscribe(user => {
                console.log('fetch user', user);
                console.log('navigate to home page');
                this.router.navigate(['/your-work']);
              });
          },
          error: (error) => {},
          complete: () => {}
        }
      );
  }

  loginWithGoogle(): void {
    window.location.href = `http://localhost:8080/oauth2/authorization/google`;
  }

}
