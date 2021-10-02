import { BrowserModule } from '@angular/platform-browser';
import { LOCALE_ID, NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NbThemeModule, NbLayoutModule, NbCardModule, NbIconModule, NbButtonModule, NbInputModule } from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { LoginComponent } from './pages/user/login/login.component';
import { AutenticacaoGuardGuard } from './pages/user/guards/autenticacao-guard.guard';
import { AutenticacaoServiceService } from './pages/user/services/autenticacao-service.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import {TokenInterceptorInterceptor} from './token-interceptor.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NbThemeModule.forRoot({ name: 'dark' }),
    NbLayoutModule,
    NbEvaIconsModule,
    NbCardModule,
    NbIconModule,
    HttpClientModule,
    NbButtonModule,
    NbInputModule
  ],
  providers: [
    
    AutenticacaoGuardGuard,
    AutenticacaoServiceService,
    {
      provide : HTTP_INTERCEPTORS,
      useClass: TokenInterceptorInterceptor,
      multi : true
    },

    { provide: LOCALE_ID, useValue: "pt" },

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
