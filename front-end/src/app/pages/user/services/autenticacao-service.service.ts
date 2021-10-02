import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AutenticacaoServiceService {

  private readonly url = 'api';
  private readonly JWT_TOKEN = 'JWT_TOKEN';

  private nomeDeUsuarioLogado : string = null;

  constructor(private http: HttpClient) { }

  public usuarioEstaLogado() : boolean {
    return false;
  }


  public async login(usuario : { nomeDeUsuario : string , senha : string }) : Promise<any>{

    return this.http.post(`${this.url}/auth`,usuario)
    .toPromise()
    .then((token) => this.guardarDadosDoUsuario(usuario.nomeDeUsuario,token))
    .then(() => true )
    .catch(() => false);

  }


  private guardarDadosDoUsuario(nomeDeUsuario : string, token: any) : void{

    this.nomeDeUsuarioLogado = nomeDeUsuario;
    this.guardarTokens(token);

  }


  private guardarTokens(token: any) : void {
    localStorage.setItem(this.JWT_TOKEN,token);
  }


  public logout(){
    localStorage.removeItem(this.JWT_TOKEN);
  }


  public getToken(){
    return localStorage.getItem(this.JWT_TOKEN);
  }





}
