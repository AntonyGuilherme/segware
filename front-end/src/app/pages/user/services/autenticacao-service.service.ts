import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Perfil } from 'src/app/models/users/perfil.model';
import { Usuario } from 'src/app/models/users/usuario.model';
import { TokenUsuario } from '../../../models/users/token.model';

@Injectable({
  providedIn: 'root'
})
export class AutenticacaoServiceService {

  private readonly url = 'api';
  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private readonly NOME_DE_USUARIO = 'NOME_DE_USUARIO';
  private readonly PERFIS = 'PERFIS';

  constructor(private http: HttpClient) { }

  public usuarioEstaLogado() : boolean {
    return !!this.getToken();
  }


  public async login(usuario : Usuario) : Promise<boolean>{

    return this.http.post<TokenUsuario>(`${this.url}/auth`,usuario)
    .toPromise()
    .then((token) => this.guardarDadosDoUsuario(token))
    .then(() => true )
    .catch(() => false);

  }


  private guardarDadosDoUsuario(token: TokenUsuario) : void{

    this.guardarToken(token.token);
    this.guardarNomeDeUsuario(token.nomeDeUsuario);
    this.guardarPerfis(token.perfis);

  }


  private guardarToken(token: string) : void {
    localStorage.setItem(this.JWT_TOKEN,token);
  }

  private guardarNomeDeUsuario(nomeDeUsuario : string){
    localStorage.setItem(this.NOME_DE_USUARIO,nomeDeUsuario);
  }

  private guardarPerfis(perfis : Perfil[]){
    localStorage.setItem(this.PERFIS,JSON.stringify(perfis));
  }

  public getPerfisDoUsuarioLogado() : Perfil[] {
    return JSON.parse(localStorage.getItem(this.PERFIS));
  }

  public getNomeDoUsuarioLogado() : string {
    return JSON.parse(localStorage.getItem(this.NOME_DE_USUARIO));
  }
  public getToken() : string{
    return localStorage.getItem(this.JWT_TOKEN);
  }

  public logout(){
    localStorage.clear();
  }






}
