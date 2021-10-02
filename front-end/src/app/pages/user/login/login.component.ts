import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticacaoServiceService } from '../services/autenticacao-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private route : Router,
    private autenticacao : AutenticacaoServiceService
  ) { }

  ngOnInit(): void {

    this.autenticacao.login({nomeDeUsuario : 'admin' , senha : '123456'})

  }

  public realizarLoginNaAplicacao(): Promise<boolean> {
    return this.route.navigate(['/register']);
   }

}
