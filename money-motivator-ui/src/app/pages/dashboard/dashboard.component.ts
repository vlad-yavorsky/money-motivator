import { Component, DestroyRef, OnInit } from '@angular/core';
import { TaskStatisticsModel } from "../../model/statistics.model";
import { StatisticsService } from "../../service/statistics.service";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { AppCommonModule } from "../../app-common.module";
import Highcharts from 'highcharts';
import { TaskService } from "../../service/task.service";
import { TaskModel } from "../../model/task.model";
import { TaskListComponent } from "../../components/task-list/task-list.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [AppCommonModule, TaskListComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {

  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options;
  statistics: TaskStatisticsModel[];
  categories: string[];

  tasksToDo: TaskModel[];
  tasksToVerify: TaskModel[];

  constructor(private statisticsService: StatisticsService,
              private taskService: TaskService,
              private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.statisticsService.getStatistics()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(statistics => {
        console.log(statistics);
        this.statistics = statistics;

        this.categories = statistics.map(stat => `${stat.year}-${stat.month}`);
        console.log('categories', this.categories);
        console.log('data', [this.statistics.map(stat => stat.count)]);
        this.updateChartOptions();
      })

    this.taskService.findAllAssignedToCurrentUser()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(tasks => this.tasksToDo = tasks)

    this.taskService.findAllOwnedByCurrentUser()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(tasks => this.tasksToVerify = tasks)
  }

  private updateChartOptions() {
    this.chartOptions = {
      chart: {
        type: 'column'
      },
      title: {
        text: ''
      },
      xAxis: {
        categories: this.categories
      },
      yAxis: {
        min: 0,
        title: {
          text: 'Tasks done'
        },
        stackLabels: {
          enabled: true
        }
      },
      legend: {
        align: 'left',
        x: 70,
        verticalAlign: 'top',
        y: 40,
        floating: true,
        backgroundColor: 'white',
        borderColor: '#CCC',
        borderWidth: 1,
        shadow: false
      },
      tooltip: {
        headerFormat: '<b>{point.x}</b><br/>',
        pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
      },
      plotOptions: {
        column: {
          stacking: 'normal',
          dataLabels: {
            enabled: true
          }
        }
      },
      series: [{
        name: 'Done',
        type: 'column',
        data: this.statistics.map(stat => stat.count)
      }]
    };
  }
}
