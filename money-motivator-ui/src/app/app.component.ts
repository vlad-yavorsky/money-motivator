import { Component, DestroyRef, OnInit } from '@angular/core';
import { AppCommonModule } from "./app-common.module";
import { RouterLinkActive, RouterOutlet } from "@angular/router";
import { UserModel } from "./model/user.model";
import { AuthService } from "./service/auth.service";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { ProfileMenuComponent } from "./components/profile-menu/profile-menu.component";
import { NewTaskDialogComponent } from "./components/new-task-dialog/new-task-dialog.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    AppCommonModule,
    RouterOutlet,
    RouterLinkActive,
    ProfileMenuComponent,
    NewTaskDialogComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title = 'money-motivator-ui';
  user?: UserModel;

  constructor(private authService: AuthService,
              private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.authService.userProfile
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(user => {
        this.user = user;
      });
  }
}
