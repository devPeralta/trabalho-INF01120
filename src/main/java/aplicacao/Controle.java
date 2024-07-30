package aplicacao;

import aplicacao.pecas.Peca;
import aplicacao.pecas.Pos;

import static aplicacao.pecas.Cor.BRANCO;
import static aplicacao.pecas.Cor.PRETO;

public class Controle {
    private static Peca pecaSelecionada = null;
    private static boolean prontoMov = false;

    //TODO: nao testado
    private static void movePeca(Pos posicaoDestino){
        // Apaga posição original.
        Jogo.apagaPosJogo(pecaSelecionada.getPosicao());

        // Atualiza posição de destino.
        pecaSelecionada.setPosicao(posicaoDestino);
        Jogo.inserePecaJogo(pecaSelecionada);
        // Reset da selecao de peca.
        prontoMov = false;
        pecaSelecionada = null;

        Main.desenhaTabuleiro(Jogo.getJogo());
        // Atualiza movimento no tabuleiro.
        Main.atualizaTabuleiro();
    }

    public static void registraClique(Pos posicao){
        Peca pecaClicada = null;
        boolean casaOcupada = Jogo.getJogo(posicao) != null;
        if (casaOcupada)
            pecaClicada = Jogo.getJogo(posicao);

        // Segundo clique, se já tiver feito um primeiro clique válido.
        if (Controle.prontoMov) {
            boolean posDiferentes = !posicao.equals(pecaSelecionada.getPosicao());
            if(posDiferentes) {
                // Testa captura.
                if (casaOcupada && pecaClicada.getCor() == PRETO) {
                    movePeca(posicao);
                }
                // Movimento para casa vazia.
                if (!casaOcupada) {
                    movePeca(posicao);
                }
            }
        }
        // Primeiro Clique.
        else {
            // Primeiro clique deve ser de uma peça branca.
            if (casaOcupada && pecaClicada.getCor() == BRANCO) {
                Controle.pecaSelecionada = pecaClicada;
                prontoMov = true;
            }
        }
    }
}