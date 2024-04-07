import { Component, DestroyRef, OnInit } from '@angular/core';
import { AppCommonModule } from "../../app-common.module";
import { TaskService } from "../../service/task.service";
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { switchMap } from "rxjs";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { TaskModel, TaskStatus } from "../../model/task.model";
import { SnackMessageService } from "../../service/snack-message.service";

@Component({
  selector: 'app-task-view',
  standalone: true,
  imports: [AppCommonModule],
  templateUrl: './task-view.component.html',
  styleUrl: './task-view.component.css',
})
export class TaskViewComponent implements OnInit {

  protected readonly TaskStatus = TaskStatus;

  loading = false;
  task: TaskModel;

  constructor(private taskService: TaskService,
              private route: ActivatedRoute,
              private snackMessageService: SnackMessageService,
              private router: Router,
              private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap((params: ParamMap) => {
            this.loading = true;
            const taskId = Number(params.get('taskId'));
            return this.taskService.findById(taskId);
          }
        ),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: (task) => {
          console.log('task', task);
          this.task = task;
          this.loading = false;
        },
        error: (error) => {
          this.loading = false;
          this.router.navigate(['/access-denied']);
        }
      });
  }

  verifyTask() {
    this.taskService.verifyTask(this.task.id)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (response) => {
          this.snackMessageService.success('Verified successfully');
        },
        error: (error) => {
          this.snackMessageService.success(error.error.message);
        }
      });
  }
}
