import java.util.List;

public class Programa {

public static void main(String[] args) {
      GerenciadorDeArquivo gerenciadorDeArquivo = new GerenciadorDeArquivo();
        List<Aluno> alunos = gerenciadorDeArquivo.getAluno();

        for (Aluno aluno : alunos) {
            System.out.println(aluno.toString());
        }

        gerenciadorDeArquivo.gravarArquivo(alunos);
    }
}





