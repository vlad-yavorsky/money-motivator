import { Component, DestroyRef, OnInit } from '@angular/core';
import { AppCommonModule } from "../../app-common.module";
import { UserModel } from "../../model/user.model";
import { AuthService } from "../../service/auth.service";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [AppCommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent implements OnInit {

  user?: UserModel;

  constructor(private authService: AuthService,
              private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.authService.userProfile
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(user => {
        console.log('PROFILE PAGE USER', user);
        this.user = user;
      });
  }
}
