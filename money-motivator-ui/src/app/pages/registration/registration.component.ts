import { Component, DestroyRef } from '@angular/core';
import { AppCommonModule } from "../../app-common.module";
import { AuthService } from "../../service/auth.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { SnackMessageService } from "../../service/snack-message.service";

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [
    AppCommonModule
  ],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css',
})
export class RegistrationComponent {

  registrationForm: FormGroup;

  constructor(private fb: FormBuilder,
              private snackMessageService: SnackMessageService,
              private authService: AuthService,
              private destroyRef: DestroyRef) {
    this.registrationForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.registrationForm.valid) {
      this.authService.signUp(this.registrationForm.value)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
        next: (response) => {
          this.snackMessageService.success('Registration successful');
        },
        error: (error) => {
          this.snackMessageService.error('Registration failed');
        }
      });
    }
  }
}
