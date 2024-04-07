import { Component, Input } from '@angular/core';
import { AppCommonModule } from "../../app-common.module";
import { TaskModel } from "../../model/task.model";

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [AppCommonModule],
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent {

  @Input() tasks: TaskModel[];

}
