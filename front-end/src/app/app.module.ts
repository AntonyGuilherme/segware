import { BrowserModule } from '@angular/platform-browser';
import { LOCALE_ID, NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NbThemeModule, NbLayoutModule, NbCardModule, NbIconModule, NbButtonModule, NbInputModule, NbFormFieldModule, NbListModule, NbTooltipModule } from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { LoginComponent } from './pages/user/login/login.component';
import { AutenticacaoGuardGuard } from './pages/user/guards/autenticacao-guard.guard';
import { AutenticacaoServiceService } from './pages/user/services/autenticacao-service.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import {TokenInterceptorInterceptor} from './token-interceptor.interceptor';
import { MenuComponent } from './pages/menu/menu/menu.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { HomeComponent } from './pages/home/home.component';
import { PostesUsuarioComponent } from './pages/postes-usuario/postes-usuario.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MenuComponent,
    HomeComponent,
    PostesUsuarioComponent
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
    NbInputModule,
    NbFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    SweetAlert2Module.forRoot(),
    NbListModule,
    NbTooltipModule
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
