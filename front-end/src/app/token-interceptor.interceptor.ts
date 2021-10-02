import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AutenticacaoServiceService } from './pages/user/services/autenticacao-service.service';

@Injectable()
export class TokenInterceptorInterceptor implements HttpInterceptor {

  constructor(
    private autenticacaoService : AutenticacaoServiceService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {


    if(this.autenticacaoService.getToken()){
      request = this.adicionarTokenARequisicao(request,this.autenticacaoService.getToken());
    }

    console.log('TOKEN INTERCEPTOR')


    return next.handle(request);
  }


  adicionarTokenARequisicao(request: HttpRequest<unknown>, token: string): HttpRequest<unknown> {
    return request.clone({
      setHeaders : {
        'Authorization' : `Bearer ${token}`
      }
    });
  }



}
