import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AutenticacaoServiceService } from '../services/autenticacao-service.service';
import { MensagensService } from './../../../utils/mensagens/mensagens.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  formularioLogin : FormGroup = new FormGroup({
    
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

  });


  constructor(
    private route: Router,
    private autenticacao: AutenticacaoServiceService,
    private servicoDeMensagem : MensagensService
  ) { }

  ngOnInit(): void {

    
  }
  
  public realizarLoginNaAplicacao(): Promise<boolean> {
    return this.route.navigate(['/register']);
  }
  
  public async realizarLogin(){
    
    if(this.formularioLogin.invalid) return this.servicoDeMensagem
    .mensagemAlerta('Atenção!','Os valores informados não são válidos.');;

   return this.autenticacao
    .login(this.formularioLogin.getRawValue())
    .then((statusLogin) => {

      if(statusLogin){
        return this.route.navigate(['/home']);
      } 
        
      this.servicoDeMensagem.mensagemErro('Erro No Login!','Error');

    })
    .catch(error => {
      console.log(error)
    });
    
  }


}
