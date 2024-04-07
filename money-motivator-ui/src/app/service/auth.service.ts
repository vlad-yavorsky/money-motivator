import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, map, Observable } from "rxjs";
import { SERVER_API_URL } from "../app.constants";
import { SignInModel, SignUpModel, TokenModel } from "../model/token.model";
import { UserModel } from "../model/user.model";
import { Role } from "../model/role";
import { UserAuthStateModel } from "../model/user-auth-state.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private resourceUrl = SERVER_API_URL + 'api/auth';

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  private userProfileSubject = new BehaviorSubject<UserModel | undefined>(undefined);

  constructor(private http: HttpClient) {
  }

  signIn(signIn: SignInModel): Observable<void> {
    return this.http.post<void>(`${this.resourceUrl}/sign-in`, signIn);
  }

  token(signIn: SignInModel): Observable<TokenModel> {
    return this.http.post<TokenModel>(`${this.resourceUrl}/token`, signIn);
  }

  signUp(signUp: SignUpModel): Observable<TokenModel> {
    return this.http.post<TokenModel>(`${this.resourceUrl}/sign-up`, signUp);
  }

  signOut(): Observable<void> {
    return this.http.post<void>(`${this.resourceUrl}/sign-out`, {})
      .pipe(
        map(() => {
          console.log('------ CLEAN STATE ------');
          this.isAuthenticatedSubject.next(false);
          this.userProfileSubject.next(undefined);
        }));
  }

  isGranted(role: Role): boolean {
    const user = this.userProfileSubject.getValue();
    return user?.roles.includes(role) ?? false;
  }

  /* New */

  checkAuthenticationAndFetchProfile(): Observable<UserModel | undefined> {
    return this.http.get<UserAuthStateModel>(`${this.resourceUrl}/profile`)
      .pipe(
        map(response => {
          console.log('------ INIT STATE ------');
          console.log('set isAuthenticatedSubject to =', response.authenticated);
          this.isAuthenticatedSubject.next(response.authenticated);
          if (response.authenticated && response.user) {
            console.log('set userProfileSubject to =', response.user);
            this.userProfileSubject.next(response.user);
            return response.user;
          } else {
            console.log('set userProfileSubject to =', undefined);
            this.userProfileSubject.next(undefined);
            return undefined;
          }
        }));
  }

  get isAuthenticated(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }

  get userProfile(): Observable<UserModel | undefined> {
    return this.userProfileSubject.asObservable();
  }

}
