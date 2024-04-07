import { Component } from '@angular/core';
import { AppCommonModule } from "../../app-common.module";

@Component({
  selector: 'app-access-denied',
  standalone: true,
  imports: [AppCommonModule],
  templateUrl: './access-denied.component.html',
  styleUrl: './access-denied.component.css'
})
export class AccessDeniedComponent {

}
