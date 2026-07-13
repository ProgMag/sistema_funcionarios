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

    @Override
    void exibirFicha() {
        super.exibirFicha();
        System.out.printf("""
                Imposto: %.2f
                Salário final: %.2f
                """, calcularImposto(getSalarioBase()), calcularSalarioFinal());
    }
}
