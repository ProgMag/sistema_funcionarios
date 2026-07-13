public class Estagiario extends Funcionario{

    public Estagiario(String nome, double salario) {
        super(nome, salario);
    }

    @Override
    public double calcularImposto(double salario) {
        return 0;
    }

    @Override
    public double calcularSalarioFinal() {
        return calcularSalarioLiquido(getSalarioBase());
    }
}
