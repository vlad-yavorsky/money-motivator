import { Component, DestroyRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { TaskService } from "../../service/task.service";
import { SnackMessageService } from "../../service/snack-message.service";
import { AppCommonModule } from "../../app-common.module";

@Component({
  selector: 'dialog-content-example-dialog',
  standalone: true,
  imports: [AppCommonModule],
  templateUrl: './dialog-content-example-dialog.html',
  styleUrl: './dialog-content-example-dialog.css'
})
export class DialogContentExampleDialog {

  taskForm: FormGroup;

  constructor(private fb: FormBuilder,
              private taskService: TaskService,
              private snackMessageService: SnackMessageService,
              private destroyRef: DestroyRef) {
    this.taskForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      price: ['', [Validators.required, Validators.min(0)]],
      status: ['', [Validators.required]],
      assigneeEmail: ['', [Validators.required, Validators.email]],
      projectId: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.taskForm.valid) {
      this.taskService.createTask(this.taskForm.value)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
          next: (response) => {
            this.snackMessageService.success('Task created');
          },
          error: (error) => {
            this.snackMessageService.error('Task creation failed');
          }
        });
    }
  }
}
