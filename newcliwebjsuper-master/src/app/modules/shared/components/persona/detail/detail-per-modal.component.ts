import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { pluck } from 'rxjs/operators';
import { PersonaHTTPService } from '../../../../../core/services/http/persona-http.service';

@Component({
  selector: 'detail-per',
  templateUrl: './detail-per.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailPersonaComponent implements OnInit, AfterViewInit {

  @Input()
  public idEntity: number;
  persona$: Observable<any>;

  constructor(
    private productosHTTPService: PersonaHTTPService
  ) {
  }

  ngOnInit() {
    this.persona$ = this.productosHTTPService.getPersona(this.idEntity).pipe(pluck('data'));
  }

  ngAfterViewInit(): void {
  }

}

