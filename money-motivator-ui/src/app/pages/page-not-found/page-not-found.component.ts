import { Component } from '@angular/core';
import { AppCommonModule } from "../../app-common.module";

@Component({
  selector: 'app-page-not-found',
  standalone: true,
  imports: [AppCommonModule],
  templateUrl: './page-not-found.component.html',
  styleUrl: './page-not-found.component.css'
})
export class PageNotFoundComponent {

}
