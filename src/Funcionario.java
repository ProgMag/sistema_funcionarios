public abstract class Funcionario implements Tributavel {

    private String nome;
    private double salarioBase;

    public Funcionario(String nome, double salario) {
        this.nome = nome;
        this.salarioBase = salario;
    }

    public String getNome() {
        return nome;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    void exibirFicha(){
        System.out.printf("""
                Nome: %s
                Salário: %.2f
                """, nome, salarioBase);
    }

    abstract double calcularSalarioFinal();
}
