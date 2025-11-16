import { Component } from '@angular/core';
import { MatDialog } from "@angular/material/dialog";
import { NewTaskDialogComponent } from "../new-task-dialog/new-task-dialog.component";
import { AppCommonModule } from "../../app-common.module";

@Component({
  selector: 'app-new-task-dialog',
  standalone: true,
  imports: [AppCommonModule],
  templateUrl: './new-task-button.component.html',
  styleUrl: './new-task-button.component.css'
})
export class NewTaskButtonComponent {

  constructor(public dialog: MatDialog) {}

  openDialog() {
    const dialogRef = this.dialog.open(NewTaskDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
}
