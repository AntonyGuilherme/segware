import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NbToastrService } from '@nebular/theme';
import { PosteService } from 'src/app/data-services/poste.service';
import { Poste } from 'src/app/models/poste/poste.model';
@Component({
  selector: 'app-postes-usuario',
  templateUrl: './postes-usuario.component.html',
  styleUrls: ['./postes-usuario.component.css']
})
export class PostesUsuarioComponent implements OnInit {

  postes : Poste[] = [];
  paginaParaAcessar = 0;
  loading = false;
  ultimaPaginaAcessada : boolean = false;

  formularioPoste : FormGroup = new FormGroup({
    texto : new FormControl(null,[
      Validators.required,
      Validators.minLength(4),
      Validators.maxLength(250)
    ])
  });

  constructor(
    private posteService : PosteService,
    private mensagensService : NbToastrService
  ) { }
  
  async ngOnInit() {
      this.carregarProximosPostes();
  }

  async carregarProximosPostes() {
    if (this.loading || this.ultimaPaginaAcessada) { return; }

    this.loading = true;

    return this.posteService.buscarPorPostesDoUsuario(this.paginaParaAcessar)
      .then(paginacao => {

        this.ultimaPaginaAcessada = paginacao.last;
        this.postes.push(...paginacao.content);
        this.loading = false;
        this.paginaParaAcessar++;

      });
  }

  async salvarPoste(){

    if(this.formularioPoste.invalid){
      return this.mensagensService.warning(
         "Verifique a quantidade de caracteres!");
    }

    return this.posteService
    .salvarPoste(this.formularioPoste.getRawValue())
    .then((poste) => {
      this.postes.push(poste);
      this.formularioPoste.get("texto").setValue(null);
    })
    .then(() => {
      this.mensagensService.success("Poste salvo com suceso!");
    })
    .catch((error) => {
      this.mensagensService.danger("Verifique sua conexão!");
    });

  }

}