import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Perfis } from 'src/app/models/users/perfil.enum';
import { Usuario } from 'src/app/models/users/usuario.model';
import { AutenticacaoService } from '../../user/services/autenticacao-service.service';

@Component({
  selector: 'menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(
    private autenticacao: AutenticacaoService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }


  estaLogado(): boolean {
    return this.autenticacao.usuarioEstaLogado();
  }


  async logout() {

    this.autenticacao.logout();

    return this.router.navigate(['/login']);

  }

  permitirAcessarOsUsuarios(): boolean {
    return this.autenticacao
      .buscarPorPermissao(Perfis.ADMIN);
  }



}
