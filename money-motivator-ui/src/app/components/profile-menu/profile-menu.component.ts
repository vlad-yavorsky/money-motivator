import { Component, DestroyRef, Input } from '@angular/core';
import { AppCommonModule } from "../../app-common.module";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { AuthService } from "../../service/auth.service";
import { Router } from "@angular/router";
import { SnackMessageService } from "../../service/snack-message.service";
import { UserModel } from "../../model/user.model";

@Component({
  selector: 'app-profile-menu',
  standalone: true,
  imports: [AppCommonModule],
  templateUrl: './profile-menu.component.html',
  styleUrl: './profile-menu.component.css'
})
export class ProfileMenuComponent {

  @Input() user?: UserModel;

  constructor(private authService: AuthService,
              private router: Router,
              private snackMessageService: SnackMessageService,
              private destroyRef: DestroyRef) {
  }

  logout() {
    this.authService.signOut()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (response) => {
          this.router.navigate(['/login'])
        },
        error: (error) => {
          this.snackMessageService.error('Sign out failed');
        }
      });
  }
}
