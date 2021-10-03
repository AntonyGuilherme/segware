import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AutenticacaoService } from './pages/user/services/autenticacao-service.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(
    private autenticacaoService: AutenticacaoService
  ) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {


    if (this.autenticacaoService.getToken()) {
      request = this.adicionarTokenARequisicao(request, this.autenticacaoService.getToken());
    }

    return next.handle(request);
  }


  adicionarTokenARequisicao(request: HttpRequest<unknown>, token: string): HttpRequest<unknown> {
    return request.clone({
      setHeaders: {
        'Authorization': `Bearer ${token}`
      }
    });
  }



}
