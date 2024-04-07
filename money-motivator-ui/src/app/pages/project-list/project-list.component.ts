import { Component, DestroyRef, OnInit } from '@angular/core';
import { ProjectService } from "../../service/project.service";
import { ProjectModel } from "../../model/project.model";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { AppCommonModule } from "../../app-common.module";

@Component({
  selector: 'app-project',
  standalone: true,
  imports: [AppCommonModule],
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css',
})
export class ProjectListComponent implements OnInit {

  projects: ProjectModel[];

  constructor(private projectService: ProjectService,
              private destroyRef: DestroyRef) {
  }

  ngOnInit(): void {
    this.projectService.findCurrentUserProjects()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(value => this.projects = value);
  }
}
