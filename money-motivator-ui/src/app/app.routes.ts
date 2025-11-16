import { Routes } from '@angular/router';
import { LoginComponent } from "./pages/login/login.component";
import { loginGuard } from "./guard/login.guard";
import { ProjectListComponent } from "./pages/project-list/project-list.component";
import { ProfileComponent } from "./pages/profile/profile.component";
import { DashboardComponent } from "./pages/dashboard/dashboard.component";
import { ProjectViewComponent } from "./pages/project-view/project-view.component";
import { TaskViewComponent } from "./pages/task-view/task-view.component";
import { RegistrationComponent } from "./pages/registration/registration.component";
import { authenticationGuard } from "./guard/auth.guard";
import { PageNotFoundComponent } from "./pages/page-not-found/page-not-found.component";
import { AccessDeniedComponent } from "./pages/access-denied/access-denied.component";
import { Oauth2RedirectComponent } from "./pages/oauth2-redirect/oauth2-redirect.component";

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'your-work',
    pathMatch: 'full'
  },
  {
    path: 'your-work',
    component: DashboardComponent,
    canActivate: [authenticationGuard()]
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [loginGuard()]
  },
  {
    path: 'registration',
    component: RegistrationComponent,
    canActivate: [loginGuard()]
  },
  {
    path: 'oauth2/redirect',
    component: Oauth2RedirectComponent
  },
  {
    path: 'projects',
    component: ProjectListComponent,
    canActivate: [authenticationGuard()]
  },
  {
    path: 'projects/:projectId',
    component: ProjectViewComponent,
    canActivate: [authenticationGuard()]
  },
  {
    path: 'projects/:projectId/tasks/:taskId',
    component: TaskViewComponent,
    canActivate: [authenticationGuard()]
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [authenticationGuard()]
  },
  {
    path: 'access-denied',
    component: AccessDeniedComponent,
    canActivate: []
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];
