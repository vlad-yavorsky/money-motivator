import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { UserModel } from "../model/user.model";
import { SERVER_API_URL } from "../app.constants";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private resourceUrl = SERVER_API_URL + 'api/users';

  constructor(private http: HttpClient) {
  }

  private findUserById(id: number): Observable<UserModel> {
    return this.http.get<UserModel>(`${this.resourceUrl}/${id}`);
  }

}
