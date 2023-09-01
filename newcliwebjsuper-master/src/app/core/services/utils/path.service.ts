import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment'
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class PathService {
  public environment = environment;

  constructor(private authService: AuthService) {
  }
  public getUrl(aditionalPath: string): string {
    // el replace debe ser igual al del back cuando se crea el directorio
    return this.environment.baseURLFiles + this.authService.getUserLogged().idApp.replace(/[^a-zA-Z0-9]/g, '') + aditionalPath;
  }
}
