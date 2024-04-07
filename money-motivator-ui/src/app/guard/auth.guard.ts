import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from "../service/auth.service";
import { map } from "rxjs";

export function authenticationGuard(): CanActivateFn {
  return () => {
    const authService: AuthService = inject(AuthService);
    const router: Router = inject(Router);

    console.log('AuthGuard 1')
    return authService.isAuthenticated.pipe(
      map(isAuthenticated => {
        console.log('AuthGuard 2 isAuthenticated', isAuthenticated);
        if (!isAuthenticated) {
          console.log('navigate to /login')
          router.navigate(['/login']);
          return false;
        }
        return true;
      }));
  };
}

