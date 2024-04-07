import { Injectable } from '@angular/core';
import { MatSnackBar } from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class SnackMessageService {

  SNACKBAR_MILLIS = 500000;

  constructor(private snackBar: MatSnackBar) {
  }

  error(message: string) {
    this.snackBar.open(message, '', {
      duration: this.SNACKBAR_MILLIS
    });
  }

  success(message: string) {
    this.snackBar.open(message, '', {
      duration: this.SNACKBAR_MILLIS
    });
  }

}
