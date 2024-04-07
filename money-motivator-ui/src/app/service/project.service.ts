import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { SERVER_API_URL } from "../app.constants";
import { ProjectModel } from "../model/project.model";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private resourceUrl = SERVER_API_URL + 'api/projects';

  constructor(private http: HttpClient) {
  }

  findCurrentUserProjects(): Observable<ProjectModel[]> {
    return this.http.get<ProjectModel[]>(this.resourceUrl);
  }

  findById(id: number): Observable<ProjectModel> {
    return this.http.get<ProjectModel>(`${this.resourceUrl}/${id}`);
  }

}
