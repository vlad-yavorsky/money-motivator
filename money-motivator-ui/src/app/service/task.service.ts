import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { SERVER_API_URL } from "../app.constants";
import { TaskModel } from "../model/task.model";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private resourceUrl = SERVER_API_URL + 'api/tasks';

  constructor(private http: HttpClient) {
  }

  findById(id: number): Observable<TaskModel> {
    return this.http.get<TaskModel>(`${this.resourceUrl}/${id}`);
  }

  findAllByProjectId(projectId: number): Observable<TaskModel[]> {
    return this.http.get<TaskModel[]>(`${this.resourceUrl}/project/${projectId}`);
  }

  findAllAssignedToCurrentUser(): Observable<TaskModel[]> {
    return this.http.get<TaskModel[]>(`${this.resourceUrl}/to-do`);
  }

  findAllOwnedByCurrentUser() {
    return this.http.get<TaskModel[]>(`${this.resourceUrl}/to-verify`);
  }

  createTask(task: TaskModel): Observable<TaskModel> {
    return this.http.post<TaskModel>(this.resourceUrl, task);
  }

  verifyTask(id: number): Observable<TaskModel> {
    return this.http.put<TaskModel>(`${this.resourceUrl}/${id}/verify`, {});
  }

}
