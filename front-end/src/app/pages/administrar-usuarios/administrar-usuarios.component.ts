import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UsuarioService } from 'src/app/data-services/usuario.service';
import { PerfilService } from 'src/app/data-services/perfil.service';
import { Usuario } from 'src/app/models/users/usuario.model';
import { Perfil } from 'src/app/models/users/perfil.model';
import { AutenticacaoService } from '../user/services/autenticacao-service.service';
import { Perfis } from 'src/app/models/users/perfil.enum';
import { NbToastrService } from '@nebular/theme';

@Component({
  selector: 'app-administrar-usuarios',
  templateUrl: './administrar-usuarios.component.html',
  styleUrls: ['./administrar-usuarios.component.css']
})
export class AdministrarUsuariosComponent implements OnInit {

  usuarios : Usuario[] = [];
  perfis : Perfil[] = [];
  paginaParaAcessar = 0;
  loading = false;
  ultimaPaginaAcessada : boolean = false;

  formularioUsuario : FormGroup = new FormGroup({
    
    nomeDeUsuario : new FormControl(null,[
      Validators.required,
      Validators.minLength(4),
      Validators.maxLength(32)
    ]),

    senha : new FormControl(null,[
      Validators.required,
      Validators.minLength(4),
      Validators.maxLength(32)
    ])
    ,
    idPerfil : new FormControl(null,[ Validators.required ])

  });

  constructor(
    private usuarioService : UsuarioService,
    private mensagensService : NbToastrService,
    private perfilService : PerfilService,
    private autenticacaoService : AutenticacaoService
  ) { }
  
  async ngOnInit() {

      return Promise.all([
        this.carregarProximosPostes(),
        this.buscarPerfis()
      ]);
  }

  async carregarProximosPostes() {
    if (this.loading || this.ultimaPaginaAcessada) { return; }

    this.loading = true;

    return this.usuarioService.buscarUsuarios(this.paginaParaAcessar)
      .then(paginacao => {

        this.ultimaPaginaAcessada = paginacao.last;
        this.usuarios.push(...paginacao.content);
        this.loading = false;
        this.paginaParaAcessar++;

      });
  }

  async salvarUsuario(){

    if(this.formularioUsuario.invalid){
      return this.mensagensService.warning(
         "Verifique a quantidade de caracteres!");
    }

    return this.usuarioService
    .salvarUsuario(this.formularioUsuario.getRawValue())
    .then((poste) => {
      this.usuarios.push(poste);
      this.limparFormulario();
    })
    .then(() => {
      this.mensagensService.success("Usuário salvo com suceso!");
    })
    .catch((error) => {
      this.mensagensService.danger("Verifique sua conexão!");
    });

  }

  limparFormulario() : void{
    this.formularioUsuario.get("nomeDeUsuario").setValue(null);
    this.formularioUsuario.get("senha").setValue(null);
    this.formularioUsuario.get("idPerfil").setValue(null);
  }


  async buscarPerfis(){
    return this.perfilService.buscarPerfis()
    .then(perfis => this.perfis = perfis);
  }


  async deletarUsuario(usuario: Usuario) {
    return this.usuarioService.deletarUsuario(usuario.nomeDeUsuario)
      .then(() => {
        this.usuarios = this.usuarios.filter(_usuario => _usuario.nomeDeUsuario != usuario.nomeDeUsuario);
      })
      .then(() => {
        return this.mensagensService.success('Usuário excluído com êxito!');
      })
      .catch(() => {

        return this.mensagensService.danger('O usuário não pode ser excluído!');

      })
  }


  permitirExcluir(usuario : Usuario): boolean {
    return this.autenticacaoService
      .buscarPorPermissao(Perfis.ADMIN) && this.verificarPerfilDoUsuario(usuario);
  }

  verificarPerfilDoUsuario(usuario : Usuario) : boolean{
      return !!usuario.perfis.find(perfil => perfil.nome != Perfis.ADMIN);
  }


}