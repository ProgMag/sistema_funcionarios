import java.util.ArrayList;

public class Main {
    static void main() {

        Gerente gerente = new Gerente("Andresa", 5000);

        Gerente gerente1 = new Gerente("Ricardo", 6560);

        Estagiario estagiario = new Estagiario("Gui", 1000);

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(gerente);
        funcionarios.add(gerente1);
        funcionarios.add(estagiario);

        for (Funcionario funcionario : funcionarios) {
            System.out.print("\n");
            funcionario.exibirFicha();

            if (funcionario instanceof Bonificavel b) {
                double bonus = b.calcularBonus(funcionario.getSalarioBase());
                System.out.println(funcionario.getNome() + " recebeu uma bonificação de R$" + bonus);
            } else {
                System.out.println(funcionario.getNome() + " não recebeu uma bonificação");
            }
        }
    }
}
