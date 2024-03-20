import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const authService = inject(AuthenticationService);
    const router = inject(Router);
    console.log('in auth guard')
    if (authService.isAuthenticated()) {
      return true;
    }
    return false;
};
