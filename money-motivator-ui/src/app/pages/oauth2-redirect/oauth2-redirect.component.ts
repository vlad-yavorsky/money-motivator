import { Component, DestroyRef, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-oauth2-redirect',
  standalone: true,
  imports: [],
  templateUrl: './oauth2-redirect.component.html',
  styleUrl: './oauth2-redirect.component.css'
})
export class Oauth2RedirectComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private destroyRef: DestroyRef
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['token'];

      if (token) {
        this.authService.checkAuthenticationAndFetchProfile()
          .pipe(takeUntilDestroyed(this.destroyRef))
          .subscribe({
            next: (user) => {
              if (user) {
                console.log('Google OAuth login successful', user);
                this.router.navigate(['/your-work']);
              } else {
                console.error('Authentication failed');
                this.router.navigate(['/login']);
              }
            },
            error: (error) => {
              console.error('Error during OAuth2 redirect', error);
              this.router.navigate(['/login']);
            }
          });
      } else {
        console.error('No token found in redirect');
        this.router.navigate(['/login']);
      }
    });
  }
}
