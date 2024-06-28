
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorDeArquivo {

    private String header = "";
    private String nomeArquivo = "C:\\_ws\\Avaliação A1\\alunos.csv";

    public List<Aluno> getAluno() {
        List<Aluno> listaDeAlunos = new ArrayList<>();

        try {
            File arquivoLeitura = new File(nomeArquivo);
            Scanner leitor = new Scanner(arquivoLeitura);

     
            if (leitor.hasNextLine()) {
                header = leitor.nextLine();
            }

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                String[] dados = linha.split(";");

                String matricula = dados[0].trim();
                String nome = dados[1].trim();

                String notaString = dados[2].replace(',', '.').trim();
                Double nota = Double.parseDouble(notaString);

                Aluno aluno = new Aluno(matricula, nome, nota);
                listaDeAlunos.add(aluno);
            }

            leitor.close(); 
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter nota para Double: " + e.getMessage());
        }

        return listaDeAlunos;
    }

    public void gravarArquivo(List<Aluno> listaDeAlunos) {
        if (listaDeAlunos == null || listaDeAlunos.isEmpty()) {
            System.out.println("Não há alunos para gerar o resumo.");
            return;
        }

        int quantidadeAlunos = listaDeAlunos.size();
        int aprovados = 0;
        int reprovados = 0;
        Double menorNota = Double.MAX_VALUE;
        Double maiorNota = Double.MIN_VALUE;
        Double somaNotas = 0.0;

        // Calcula as estatísticas
        for (Aluno aluno : listaDeAlunos) {
            if (aluno.getNota() >= 6.0) {
                aprovados++;
            } else {
                reprovados++;
            }

            // Atualiza a menor e maior nota
            if (aluno.getNota() < menorNota) {
                menorNota = aluno.getNota();
            }
            if (aluno.getNota() > maiorNota) {
                maiorNota = aluno.getNota();
            }

            // Soma das notas para calcular a média
            somaNotas += aluno.getNota();
        }

        Double mediaGeral = somaNotas / quantidadeAlunos;

        // Grava o resumo no arquivo "resumo.txt"
        try (FileWriter writer = new FileWriter("resumo.txt")) {
            writer.write("Resumo da Turma:\n");
            writer.write("Quantidade de Alunos: " + quantidadeAlunos + "\n");
            writer.write("Aprovados: " + aprovados + "\n");
            writer.write("Reprovados: " + reprovados + "\n");
            writer.write("Menor Nota: " + menorNota + "\n");
            writer.write("Maior Nota: " + maiorNota + "\n");
            writer.write("Média Geral: " + mediaGeral + "\n");

            System.out.println("Resumo gravado com sucesso no arquivo 'resumo.txt'.");
        } catch (IOException e) {
            System.out.println("Erro ao gravar o arquivo resumo.txt: " + e.getMessage());
        }
    }

}
