import { Component, DestroyRef, OnInit } from '@angular/core';
import { ProjectService } from "../../service/project.service";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { AppCommonModule } from "../../app-common.module";
import { ProjectModel } from "../../model/project.model";
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { forkJoin, switchMap } from "rxjs";
import { TaskService } from "../../service/task.service";
import { TaskModel } from "../../model/task.model";
import { TaskListComponent } from "../../components/task-list/task-list.component";

@Component({
  selector: 'app-project-view',
  standalone: true,
  imports: [AppCommonModule, TaskListComponent],
  templateUrl: './project-view.component.html',
  styleUrl: './project-view.component.css',
})
export class ProjectViewComponent implements OnInit {

  loading = false;
  project: ProjectModel;
  tasks: TaskModel[];

  constructor(private projectService: ProjectService,
              private taskService: TaskService,
              private route: ActivatedRoute,
              private router: Router,
              private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap((params: ParamMap) => {
            this.loading = true;
            const projectId = Number(params.get('projectId'));
            let project$ = this.projectService.findById(projectId);
            let tasks$ = this.taskService.findAllByProjectId(projectId);
            return forkJoin([project$, tasks$]);
          }
        ),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: ([project, tasks]) => {
          console.log(project);
          this.project = project;
          this.tasks = tasks;
          this.loading = false;
        },
        error: (error) => {
          this.loading = false;
          this.router.navigate(['/access-denied']);
        }
      });
  }
}
