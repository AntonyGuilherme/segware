import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class MensagensService {

  private Toast = Swal.mixin({
    toast: true,
    position: "top-end",
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.addEventListener("mouseenter", Swal.stopTimer);
      toast.addEventListener("mouseleave", Swal.resumeTimer);
    },
  });

  constructor() { }


  public mensagemSucesso(title : string, mensagem : string){
    return this.Toast.fire(title,mensagem,'success');
  }

  public mensagemAlerta(titulo : string, mensagem : string){
    return this.Toast.fire(titulo,mensagem,'warning');
  }

  public mensagemErro(titulo : string, mensagem : string){
    return this.Toast.fire(titulo,mensagem,'error');
  }

  public mensagemInformativa(titulo : string, mensagem : string){
    return this.Toast.fire(titulo,mensagem,'info');
  }



}
