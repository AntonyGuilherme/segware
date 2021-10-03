import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NbToastrService } from '@nebular/theme';
import { AutenticacaoService} from '../services/autenticacao-service.service';

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
    private autenticacao: AutenticacaoService,
    private servicoDeMensagem : NbToastrService
  ) { }

  ngOnInit(): void {

    
  }
  
  public realizarLoginNaAplicacao(): Promise<boolean> {
    return this.route.navigate(['/register']);
  }
  
  public async realizarLogin(){
    
    if(this.formularioLogin.invalid) return this.servicoDeMensagem
    .warning('Os valores informados não são válidos.');;

   return this.autenticacao
    .login(this.formularioLogin.getRawValue())
    .then((statusLogin) => {

      if(statusLogin){
        return this.route.navigate(['/home']);
      } 
        
      this.servicoDeMensagem.danger('Usuário não encontrado!');

    })
    .catch(error => {
      console.log(error)
    });
    
  }


}
