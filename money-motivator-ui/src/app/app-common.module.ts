import { NgModule } from "@angular/core";
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatSelectModule } from "@angular/material/select";
import { MatTableModule } from "@angular/material/table";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatInputModule } from "@angular/material/input";
import { MatDialogModule } from "@angular/material/dialog";
import { MatCommonModule } from "@angular/material/core";
import { MatSortModule } from "@angular/material/sort";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatIconModule } from "@angular/material/icon";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatCardModule } from "@angular/material/card";
import { MatGridListModule } from "@angular/material/grid-list";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatListModule } from "@angular/material/list";
import { MatMenuModule } from "@angular/material/menu";
import { MatBadgeModule } from "@angular/material/badge";
import { ReactiveFormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { HighchartsChartModule } from "highcharts-angular";
import { RouterLink } from "@angular/router";
import { MatTooltip } from "@angular/material/tooltip";

@NgModule({
  imports: [
    CommonModule,
    RouterLink,
    MatSlideToggleModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatTableModule,
    MatSnackBarModule,
    MatInputModule,
    MatDialogModule,
    MatCommonModule,
    MatSortModule,
    MatToolbarModule,
    MatPaginatorModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatGridListModule,
    MatExpansionModule,
    MatListModule,
    MatMenuModule,
    MatBadgeModule,
    ReactiveFormsModule,
    HighchartsChartModule,
    MatTooltip
  ],
  exports: [
    CommonModule,
    RouterLink,
    MatSlideToggleModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatTableModule,
    MatSnackBarModule,
    MatInputModule,
    MatDialogModule,
    MatCommonModule,
    MatSortModule,
    MatToolbarModule,
    MatPaginatorModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatGridListModule,
    MatExpansionModule,
    MatListModule,
    MatMenuModule,
    MatBadgeModule,
    ReactiveFormsModule,
    HighchartsChartModule,
    MatTooltip
  ]
})
export class AppCommonModule {
}
