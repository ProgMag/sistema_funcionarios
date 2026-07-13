public class Gerente extends Funcionario implements Bonificavel {

    public Gerente(String nome, double salario) {
        super(nome, salario);
    }

    @Override
    public double calcularImposto(double salario) {
        return salario * 0.15;
    }

    @Override
    public double calcularBonus(double salario) {
        return salario * 0.20;
    }


    @Override
    public double calcularSalarioFinal() {
        double salarioLiquido = calcularSalarioLiquido(getSalarioBase());
        double bonus = calcularBonus(getSalarioBase());
        return bonus + salarioLiquido;
    }

    @Override
    public void exibirFicha() {
        super.exibirFicha();
        System.out.printf("""
                Imposto: %.2f
                Salário final: %.2f
                """, calcularImposto(getSalarioBase()),calcularSalarioFinal());
    }
}
