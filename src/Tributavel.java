public interface Tributavel {

    double calcularImposto(double salario);

    default double calcularSalarioLiquido(double salario) {
       double imposto = calcularImposto(salario);
       return salario - imposto;
    }
}
