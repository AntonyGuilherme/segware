import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Poste } from '../models/poste/poste.model';
import { Paginacao } from '../models/paginacao.model';

@Injectable({
  providedIn: 'root'
})
export class PosteService {
  
  constructor(private http : HttpClient) { }

  async buscarPorPostesMaisAvaliados(pagina : number = 0 , quantidade : number = 4){

    return this.http.get<Paginacao<Poste>>('/api/poste',{
      params : { 
        "page" : String(pagina) , 
        "size" : String(quantidade) ,
        "sort" : "numeroDeAprovacoes,desc"
      }
    })
    .toPromise();
    
  }

  async buscarPorPostesDoUsuario(pagina : number = 0 , quantidade : number = 4){

    return this.http.get<Paginacao<Poste>>('/api/poste/usuario',{
      params : { 
        "page" : String(pagina) , 
        "size" : String(quantidade) ,
        "sort" : "numeroDeAprovacoes,desc"
      }
    })
    .toPromise();

  }

  async aprovarPoste(id : number){

    return this.http.patch<Poste>(`/api/poste/${id}/aprovar`,{})
    .toPromise();

  }
  
  async salvarPoste(novoPoste: { texto : string }) {
    return this.http.post<Poste>(`/api/poste`,novoPoste)
    .toPromise();
  }

  async deletarPoste(id : number){

    return this.http.delete<void>(`api/poste/${id}`).toPromise();

  }
  
}
