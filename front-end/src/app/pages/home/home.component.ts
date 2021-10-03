import { Component, OnInit } from '@angular/core';
import { NbToastrService } from '@nebular/theme';
import { Poste } from 'src/app/models/poste/poste.model';
import { Perfis } from 'src/app/models/users/perfil.enum';
import { AutenticacaoService } from '../user/services/autenticacao-service.service';
import { PosteService } from './../../data-services/poste.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  postes: Poste[] = [];
  paginaParaAcessar = 0;
  loading = false;
  ultimaPaginaAcessada: boolean = false;

  constructor(
    private posteService: PosteService,
    private mensagensService: NbToastrService,
    private autenticacaoService: AutenticacaoService
  ) { }

  async ngOnInit() {
    this.carregarProximosPostes();
  }

  carregarProximosPostes() {
    if (this.loading || this.ultimaPaginaAcessada) { return; }

    this.loading = true;

    this.posteService.buscarPorPostesMaisAvaliados(this.paginaParaAcessar)
      .then(paginacao => {

        this.ultimaPaginaAcessada = paginacao.last;
        this.postes.push(...paginacao.content);
        this.loading = false;
        this.paginaParaAcessar++;

      });
  }


  async aprovarPoste(poste: Poste) {
    return this.posteService
      .aprovarPoste(poste.id)
      .then((posteAtualizado) => {

        const index = this.postes.findIndex(poste => poste.id == posteAtualizado.id);
        this.postes[index].numeroDeAprovacoes = posteAtualizado.numeroDeAprovacoes;
        this.postes.sort((poste1, poste2) => poste2.numeroDeAprovacoes - poste1.numeroDeAprovacoes);

      })
      .catch(error => {

        if (error.status == 404) {

          this.mensagensService.danger(
            "Você não pode fazer um UpVote em seu próprio poste!"
          );

        }

      });


  }

  async deletarPoste(poste: Poste) {
    return this.posteService.deletarPoste(poste.id)
      .then(() => {
        this.postes = this.postes.filter(_poste => _poste.id != poste.id);
      })
      .then(() => {
        return this.mensagensService.success('Poste excluído com êxito!');
      })
      .catch(() => {

        return this.mensagensService.danger('O poste não pode ser excluído!');

      })
  }


  permitirExcluir(): boolean {
    return this.autenticacaoService
      .buscarPorPermissao(Perfis.ADMIN);
  }


}
