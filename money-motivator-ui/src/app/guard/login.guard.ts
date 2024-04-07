import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from "../service/auth.service";
import { map } from "rxjs";

export function loginGuard(): CanActivateFn {
  return () => {
    const authService: AuthService = inject(AuthService);

    console.log('LoginGuard 1')
    return authService.isAuthenticated.pipe(
      map(isAuthenticated => {
        console.log('LoginGuard 2 isAuthenticated', isAuthenticated);
        return !isAuthenticated;
      })
    );
  };
}

