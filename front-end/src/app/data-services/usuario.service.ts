import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../models/../models/users/usuario.model';
import { Paginacao } from '../models/paginacao.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  
  constructor(private http : HttpClient) { }

  async buscarUsuarios(pagina : number = 0 , quantidade : number = 4){

    return this.http.get<Paginacao<Usuario>>('/api/usuarios',{
      params : { 
        "page" : String(pagina) , 
        "size" : String(quantidade) ,
        "sort" : "nomeDeUsuario,desc"
      }
    })
    .toPromise();
    
  }

  
  async salvarUsuario(usuario: Usuario) {
    return this.http.post<Usuario>(`/api/usuarios`,usuario)
    .toPromise();
  }

  async deletarUsuario(nomeDeUsuario : string){

    return this.http.delete<void>(`api/usuarios/${nomeDeUsuario}`).toPromise();

  }
  
}
