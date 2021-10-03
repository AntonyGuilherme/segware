import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Perfil } from '../models/users/perfil.model';
import { Paginacao } from '../models/paginacao.model';

@Injectable({
  providedIn: 'root'
})
export class PerfilService {
  
  constructor(private http : HttpClient) { }

  async buscarPerfis(){

    return this.http.get<Paginacao<Perfil>>('/api/perfis',{
      params : { 
        "page" : String(0) , 
        "size" : String(10) ,
        "sort" : "nome,asc"
      }
    })
    .toPromise()
    .then((paginacao) => paginacao.content);
    
  }
  
}
