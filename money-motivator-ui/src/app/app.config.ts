import { APP_INITIALIZER, ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient } from "@angular/common/http";
import { AuthService } from "./service/auth.service";

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideAnimationsAsync(), provideHttpClient(),
    {
      provide: APP_INITIALIZER,
      useFactory: (service: AuthService) => () => service.checkAuthenticationAndFetchProfile(),
      deps: [AuthService],
      multi: true
    }
    ]
};
