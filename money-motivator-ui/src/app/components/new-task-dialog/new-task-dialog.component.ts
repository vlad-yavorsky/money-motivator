import { Component } from '@angular/core';
import { MatDialog } from "@angular/material/dialog";
import { DialogContentExampleDialog } from "./dialog-content-example-dialog";
import { AppCommonModule } from "../../app-common.module";

@Component({
  selector: 'app-new-task-dialog',
  standalone: true,
  imports: [AppCommonModule],
  templateUrl: './new-task-dialog.component.html',
  styleUrl: './new-task-dialog.component.css'
})
export class NewTaskDialogComponent {

  constructor(public dialog: MatDialog) {}

  openDialog() {
    const dialogRef = this.dialog.open(DialogContentExampleDialog);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
}
