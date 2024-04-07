import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { SERVER_API_URL } from "../app.constants";
import { TaskStatisticsModel } from "../model/statistics.model";

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  private resourceUrl = SERVER_API_URL + 'api/statistics';

  constructor(private http: HttpClient) {
  }

  getStatistics(): Observable<TaskStatisticsModel[]> {
    return this.http.get<TaskStatisticsModel[]>(this.resourceUrl);
  }

}
